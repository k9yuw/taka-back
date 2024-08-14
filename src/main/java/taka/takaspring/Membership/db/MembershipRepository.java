package taka.takaspring.Membership.db;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taka.takaspring.Organization.db.OrgEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {

    Page<MembershipEntity> findByOrgId(@Param("orgId") Long orgId, Pageable pageable);

    Optional<MembershipEntity> findByOrgIdAndUserId(@Param("orgId") Long orgId, @Param("userId") Long userId);

    Page<MembershipEntity> findByOrgIdAndStatus(@Param("orgId") Long orgId,
                                                @Param("status") MembershipEntity.MembershipStatus status,
                                                Pageable pageable);

    MembershipEntity findByUser_EmailAndOrg_Id(String email, Long orgId);
}
