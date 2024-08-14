package taka.takaspring.Rental.db;

import org.springframework.data.jpa.repository.JpaRepository;
import taka.takaspring.Organization.db.OrgEntity;

import java.util.List;
import java.util.Optional;

public interface RentalCategoryRepository extends JpaRepository<RentalCategoryEntity, Long> {
    List<RentalCategoryEntity> findByOrganization(OrgEntity organization);
    Optional<RentalCategoryEntity> findByIdAndOrganization(Long categoryId, OrgEntity organization);
}

