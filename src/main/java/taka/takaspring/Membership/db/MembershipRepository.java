package taka.takaspring.Membership.db;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taka.takaspring.Organization.db.OrgEntity;

import java.util.List;
import java.util.Optional;

@Hidden
@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {
    List<MembershipEntity> findByOrgId(@Param("orgId") Long orgId);
    Optional<MembershipEntity> findByOrgIdAndUserId(@Param("orgId") Long orgId, @Param("userId") Long userId);
    List<MembershipEntity> findByOrgIdAndStatus(@Param("orgId") Long orgId, @Param("status") MembershipEntity.MembershipStatus status);
    MembershipEntity findByUser_EmailAndOrg_Id(String email, Long orgId);
}