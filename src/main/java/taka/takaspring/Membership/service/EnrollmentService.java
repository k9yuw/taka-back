package taka.takaspring.Membership.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Membership.dto.MembershipDto;
import taka.takaspring.Membership.exception.OrgEntityNotFoundException;
import taka.takaspring.Membership.exception.UserEntityNotFoundException;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;

import java.util.List;
import java.util.stream.Collectors;

// 유저 입장에서 단체에 가입할 때 다룰 수 있는 기능들
@Service
public class EnrollmentService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final OrgRepository orgRepository;

    @Autowired
    public EnrollmentService(MembershipRepository membershipRepository, UserRepository userRepository, OrgRepository orgRepository){
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.orgRepository = orgRepository;
    }

    @Transactional
    public List<EnrollmentDto.EnrollableOrgResponse> getEnrollableOrgList(){
        List<OrgEntity> orgList = orgRepository.findAll();
        return orgList.stream()
                .map(this::convertToEnrollableOrgResponse)
                .collect(Collectors.toList());
    }

    private EnrollmentDto.EnrollableOrgResponse convertToEnrollableOrgResponse(OrgEntity orgEntity) {

        EnrollmentDto.EnrollableOrgResponse response = EnrollmentDto.EnrollableOrgResponse.builder().
                department(orgEntity.getDepartment()).
                orgName(orgEntity.getOrgName()).
                description(orgEntity.getOrgDescription()).
                build();

        return response;
    }

    @Transactional
    public EnrollmentDto.EnrollmentResponse enrollUserToOrg(Long orgId, Long userId) {

        String userMsg = String.format("요청한 회원 id: %d", userId);
        String orgMsg = String.format("요청한 단체 id: %d", orgId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserEntityNotFoundException(userMsg));
        OrgEntity org = orgRepository.findById(orgId)
                .orElseThrow(() -> new OrgEntityNotFoundException(orgMsg));

        MembershipEntity enrollment = MembershipEntity.builder()
                .user(user)
                .org(org)
                .status(MembershipEntity.MembershipStatus.PENDING)
                .build();

        membershipRepository.save(enrollment);

        EnrollmentDto.EnrollmentResponse response = EnrollmentDto.EnrollmentResponse.builder()
                .userName(user.getName())
                .orgName(org.getOrgName())
                .status(MembershipEntity.MembershipStatus.PENDING)
                .build();

        return response;
    }

}
