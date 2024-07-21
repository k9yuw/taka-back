package taka.takaspring.Rental.db;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Hidden
@Repository
public interface RentalRecordRepository extends JpaRepository<RentalRecordEntity, Long> {
    Optional<RentalRecordEntity> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}