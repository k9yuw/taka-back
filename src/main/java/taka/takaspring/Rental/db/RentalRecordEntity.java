package taka.takaspring.Rental.db;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RentalRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private OrgEntity organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private RentalItemEntity item;

    @Column(nullable = false)
    private LocalDateTime rentalStartDate;

    @Column(nullable = false)
    private LocalDateTime rentalEndDate;

    private LocalDateTime returnDate;

    @Column(nullable = false)
    private boolean isReturned;

    public RentalRecordEntity(UserEntity user, OrgEntity organization, RentalItemEntity item, LocalDateTime rentalStartDate, LocalDateTime rentalEndDate) {
        this.user = user;
        this.organization = organization;
        this.item = item;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.isReturned = false;
    }

    public void markAsReturned() {
        this.isReturned = true;
    }
}