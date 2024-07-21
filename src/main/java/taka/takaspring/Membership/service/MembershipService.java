package taka.takaspring.Membership.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Membership.dto.MembershipDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipService(MembershipRepository userOrgRepository) {
        this.membershipRepository = userOrgRepository;
    }

    @Transactional
    public List<MembershipDto.UserByOrgResponse> getUsersByOrgId(Long organizationId) {
        List<MembershipEntity> membershipEntities = membershipRepository.findByOrgId(organizationId);
        return membershipEntities.stream()
                .map(this::convertToUserByOrgResponse)
                .collect(Collectors.toList());
    }

    private MembershipDto.UserByOrgResponse convertToUserByOrgResponse(MembershipEntity userOrgEntity) {

        MembershipDto.UserByOrgResponse response = MembershipDto.UserByOrgResponse.builder().
                name(userOrgEntity.getUser().getName()).
                major(userOrgEntity.getUser().getMajor()).
                studentNum(userOrgEntity.getUser().getStudentNum()).
                build();

        return response;
    }

    @Transactional
    public void deleteUserFromOrg(Long orgId, Long userId) {
        Optional<MembershipEntity> userOrgEntityOptional = membershipRepository.findByOrgIdAndUserId(orgId, userId);

        if (userOrgEntityOptional.isPresent()) {
            membershipRepository.delete(userOrgEntityOptional.get());
        } else {
            throw new EntityNotFoundException("단체에 해당 회원이 존재하지 않습니다.");
        }
    }


    @Transactional
    public EnrollmentDto.EnrollmentResponse approveEnrollment(EnrollmentDto.EnrollmentIntermediateRequest request){

        Long membershipId = request.getMembership().getId();
        MembershipEntity membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 요청입니다."));

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
        MembershipEntity membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 요청입니다."));

        membership.setStatus(MembershipEntity.MembershipStatus.REJECTED);
        membershipRepository.save(membership);

        EnrollmentDto.EnrollmentResponse response = EnrollmentDto.EnrollmentResponse.builder().
                userName(membership.getUser().getName()).
                orgName(membership.getOrg().getOrgName()).
                build();

        return response;

    }

    @Transactional
    public List<EnrollmentDto.EnrollmentIntermediateRequest> getPendingUsers(Long organizationId) {
        List<MembershipEntity> membershipEntities = membershipRepository.findByOrgIdAndStatus(organizationId, MembershipEntity.MembershipStatus.PENDING);
        return membershipEntities.stream()
                .map(this::convertToEnrollmentIntermediateRequest)
                .collect(Collectors.toList());
    }

    private EnrollmentDto.EnrollmentIntermediateRequest convertToEnrollmentIntermediateRequest(MembershipEntity membershipEntity) {

        EnrollmentDto.EnrollmentIntermediateRequest response = EnrollmentDto.EnrollmentIntermediateRequest.builder().
                membership(membershipEntity).
                build();

        return response;
    }
}
