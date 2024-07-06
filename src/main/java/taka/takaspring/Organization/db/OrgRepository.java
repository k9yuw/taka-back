package taka.takaspring.Organization.db;

import org.springframework.data.jpa.repository.JpaRepository;
import taka.takaspring.Member.db.UserEntity;

public interface OrgRepository extends JpaRepository<OrgEntity, Long> {

}
