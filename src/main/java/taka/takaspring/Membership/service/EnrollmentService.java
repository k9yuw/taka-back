package taka.takaspring.Membership.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Organization.db.OrgRepository;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final MembershipRepository userOrgRepository;
    private final UserRepository userRepository;
    private final OrgRepository orgRepository;

    public  enrollUserToOrg(Long userId, Long orgId) {

    }


}
