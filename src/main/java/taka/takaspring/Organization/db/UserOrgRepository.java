package taka.takaspring.Organization.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrgRepository extends JpaRepository<UserOrgEntity, Long> {
    List<UserOrgEntity> findByOrgId(Long orgId);

    Optional<UserOrgEntity> findByOrgIdAndUserId(Long orgId, Long userId);
}