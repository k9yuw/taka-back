package taka.takaspring.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Membership.exception.UserEntityNotFoundException;

@Service
public class AccessControlService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccessControlService(MembershipRepository membershipRepository, UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
    }

    public boolean isOrgAdmin(Authentication authentication, Long orgId) {
        String email = authentication.getName();
        MembershipEntity membership = membershipRepository.findByUser_EmailAndOrg_Id(email, orgId);
        return membership != null && membership.isAdmin();
    }

    public boolean isSelf(Authentication authentication, Long userId) {

        String email = authentication.getName();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserEntityNotFoundException("User not found with id: " + userId));
        return user.getEmail().equals(email);
    }
}