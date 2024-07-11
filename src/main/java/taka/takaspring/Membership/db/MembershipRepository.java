package taka.takaspring.Membership.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taka.takaspring.Organization.db.OrgEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {
    List<MembershipEntity> findByOrgId(Long orgId);
    Optional<MembershipEntity> findByOrgIdAndUserId(Long orgId, Long userId);
    List<MembershipEntity> findByOrgAndStatus(Long orgId, MembershipEntity.MembershipStatus status);
}