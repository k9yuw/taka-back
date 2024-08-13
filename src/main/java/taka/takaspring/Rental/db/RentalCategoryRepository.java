package taka.takaspring.Rental.db;

import org.springframework.data.jpa.repository.JpaRepository;
import taka.takaspring.Organization.db.OrgEntity;

import java.util.List;

public interface RentalCategoryRepository extends JpaRepository<RentalCategoryEntity, Long> {
    List<RentalCategoryEntity> findByOrganization(OrgEntity organization);
}
