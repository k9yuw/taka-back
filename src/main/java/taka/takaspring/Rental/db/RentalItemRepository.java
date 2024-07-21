package taka.takaspring.Rental.db;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Hidden
@Repository
public interface RentalItemRepository extends JpaRepository<RentalItemEntity, Long> {
    Optional<RentalItemEntity> findByIdAndOrganizationId(@Param("id") Long id, @Param("organizationId") Long organizationId);
    List<RentalItemEntity> findByOrganizationId(@Param("organizationId") Long organizationId);
}