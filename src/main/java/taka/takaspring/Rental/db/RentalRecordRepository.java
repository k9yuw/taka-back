package taka.takaspring.Rental.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRecordRepository extends JpaRepository<RentalRecordEntity, Long> {

    Optional<RentalRecordEntity> findByIdAndUserId(Long id, Long userId);
}
