package taka.takaspring.Rental.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taka.takaspring.Organization.db.UserOrgEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalItemRepository extends JpaRepository<RentalItemEntity, Long> {
    Optional<RentalItemEntity> findByIdAndOrganizationId(Long id, Long organizationId);
    List<RentalItemEntity> findByOrganizationId(Long organizationId);
}