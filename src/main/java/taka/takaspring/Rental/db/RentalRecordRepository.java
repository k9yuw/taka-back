package taka.takaspring.Rental.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRecordRepository extends JpaRepository<RentalRecordEntity, Long> {

    RentalRecordEntity findByIdAndUserId(Long rentalRecordId, Long userId);
}
