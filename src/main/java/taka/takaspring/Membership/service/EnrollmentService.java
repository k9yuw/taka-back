package taka.takaspring.Membership.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Membership.exception.OrgEntityNotFoundException;
import taka.takaspring.Membership.exception.UserEntityNotFoundException;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;

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
