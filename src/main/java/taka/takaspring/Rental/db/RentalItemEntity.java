package taka.takaspring.Rental.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 시 컴파일 에러 발생시킴
@Entity
public class RentalItemEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String organization;

    @Column(nullable = false)
    private boolean isAvailable;

    private LocalDateTime rentalStartDate;

    private LocalDateTime rentalEndDate;

    @ManyToOne
//    @JoinColumn(name = "member_id")
    private UserEntity rentalMember;

}
