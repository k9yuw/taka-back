package taka.takaspring.Organization.db;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Membership.db.MembershipEntity;

import java.util.List;

@Repository
public interface OrgRepository extends JpaRepository<OrgEntity, Long> {
    Page<OrgEntity> findAll(Pageable pageable);
}
