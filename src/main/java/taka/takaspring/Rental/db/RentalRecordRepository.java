package taka.takaspring.Rental.db;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Hidden
@Repository
public interface RentalRecordRepository extends JpaRepository<RentalRecordEntity, Long> {
    Optional<RentalRecordEntity> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    // UserId와 isReturned가 false인 레코드를 찾는 메서드
    List<RentalRecordEntity> findByUserIdAndIsReturnedFalse(@Param("userId") Long userId);

    List<RentalRecordEntity> findByOrgId(@Param("orgId") Long orgId);
    List<RentalRecordEntity> findByOrgIdAndUserId(@Param("orgId") Long orgId, @Param("userId") Long userId);
    List<RentalRecordEntity> findByItemId(@Param("itemId") Long itemId);

}