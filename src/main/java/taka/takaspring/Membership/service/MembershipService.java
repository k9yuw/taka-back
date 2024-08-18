package taka.takaspring.Membership.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Membership.dto.MembershipDto;
import taka.takaspring.Membership.exception.EnrollmentNotFoundException;
import taka.takaspring.Membership.exception.MembershipNotFoundException;
import taka.takaspring.Organization.db.OrgRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final OrgRepository orgRepository;
    private final UserRepository userRepository;

    @Autowired
    public MembershipService(MembershipRepository userOrgRepository, OrgRepository orgRepository, UserRepository userRepository) {
        this.membershipRepository = userOrgRepository;
        this.orgRepository = orgRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Page<MembershipDto.UserByOrgResponse> getUserListByOrgId(Long organizationId, Pageable pageable) {
        Page<MembershipEntity> membershipEntities = membershipRepository.findByOrgId(organizationId, pageable);
        return membershipEntities.map(this::convertToUserByOrgResponse);
    }

    private MembershipDto.UserByOrgResponse convertToUserByOrgResponse(MembershipEntity userOrgEntity) {
        return MembershipDto.UserByOrgResponse.builder()
                .name(userOrgEntity.getUser().getName())
                .major(userOrgEntity.getUser().getMajor())
                .studentNum(userOrgEntity.getUser().getStudentNum())
                .build();
    }


    @Transactional
    public Page<MembershipDto.UserByOrgResponse> searchUsersByOrgIdAndName(Long organizationId, String name, Pageable pageable) {
        Page<MembershipEntity> membershipEntities = membershipRepository.findByOrgIdAndUser_NameContaining(organizationId, name, pageable);
        return membershipEntities.map(this::convertToUserByOrgResponse);
    }

    @Transactional
    public void deleteUserFromOrg(Long orgId, Long userId) {
        Optional<MembershipEntity> userOrgEntityOptional = membershipRepository.findByOrgIdAndUserId(orgId, userId);
        String orgName = orgRepository.findById(orgId).get().getOrgName();
        String userName = userRepository.findById(userId).get().getName();
        String message = String.format("단체명: %s, 회원명: %s", orgName, userName);

        if (userOrgEntityOptional.isPresent()) {
            membershipRepository.delete(userOrgEntityOptional.get());
        } else {
            throw new MembershipNotFoundException(message);
        }
    }


    @Transactional
    public EnrollmentDto.EnrollmentResponse approveEnrollment(EnrollmentDto.EnrollmentIntermediateRequest request){

        Long membershipId = request.getMembership().getId();
        String orgName = request.getMembership().getOrg().getOrgName();
        String userName = request.getMembership().getUser().getName();
        String message = String.format("단체명: %s, 회원명: %s", orgName, userName);

        MembershipEntity membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new EnrollmentNotFoundException(message));

        membership.setStatus(MembershipEntity.MembershipStatus.APPROVED);
        membershipRepository.save(membership);

        EnrollmentDto.EnrollmentResponse response = EnrollmentDto.EnrollmentResponse.builder().
                userName(membership.getUser().getName()).
                orgName(membership.getOrg().getOrgName()).
                build();

        return response;
    }

    @Transactional
    public EnrollmentDto.EnrollmentResponse rejectEnrollment(EnrollmentDto.EnrollmentIntermediateRequest request){

        Long membershipId = request.getMembership().getId();
        String orgName = request.getMembership().getOrg().getOrgName();
        String userName = request.getMembership().getUser().getName();
        String message = String.format("단체명: %s, 회원명: %s", orgName, userName);

        MembershipEntity membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new EnrollmentNotFoundException(message));

        membership.setStatus(MembershipEntity.MembershipStatus.REJECTED);
        membershipRepository.save(membership);

        EnrollmentDto.EnrollmentResponse response = EnrollmentDto.EnrollmentResponse.builder().
                userName(membership.getUser().getName()).
                orgName(membership.getOrg().getOrgName()).
                build();

        return response;

    }

    @Transactional
    public Page<EnrollmentDto.EnrollmentIntermediateRequest> getPendingUserList(Long organizationId, Pageable pageable) {
        Page<MembershipEntity> membershipEntities = membershipRepository.findByOrgIdAndStatus(organizationId, MembershipEntity.MembershipStatus.PENDING, pageable);
        return membershipEntities.map(this::convertToEnrollmentIntermediateRequest);
    }


    @Transactional
    private EnrollmentDto.EnrollmentIntermediateRequest convertToEnrollmentIntermediateRequest(MembershipEntity membershipEntity) {

        EnrollmentDto.EnrollmentIntermediateRequest response = EnrollmentDto.EnrollmentIntermediateRequest.builder().
                membership(membershipEntity).
                build();

        return response;
    }
}
