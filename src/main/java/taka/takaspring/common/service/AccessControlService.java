package taka.takaspring.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;

@Service
public class AccessControlService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public AccessControlService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public boolean isOrgAdmin(Authentication authentication, Long orgId) {
        String email = authentication.getName();
        MembershipEntity membership = membershipRepository.findByUser_EmailAndOrg_Id(email, orgId);
        return membership != null && membership.isAdmin();
    }
}