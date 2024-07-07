package taka.takaspring.Rental.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalItemRepository extends JpaRepository<RentalItemEntity, Long> {
    Optional<RentalItemEntity> findByIdAndOrgId(Long id, Long organizationId);
}