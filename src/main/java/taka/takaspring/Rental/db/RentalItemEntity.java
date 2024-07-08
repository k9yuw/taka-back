package taka.takaspring.Rental.db;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 시 컴파일 에러 발생시킴
@Entity
public class RentalItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_item_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String itemName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private OrgEntity organization;

    @Column(nullable = false)
    private boolean isAvailable;

    private String rentalPeriod;

    private String itemImageUrl;

    @Builder
    public RentalItemEntity(Long id, String itemName, OrgEntity organization, boolean isAvailable, String rentalPeriod, String itemImageUrl){
        this.id = id;
        this.itemName = itemName;
        this.organization = organization;
        this.isAvailable = isAvailable;
        this.rentalPeriod = rentalPeriod;
        this.itemImageUrl = itemImageUrl;
    }

    public RentalItemEntity updateFields(String itemName, boolean isAvailable, String rentalPeriod, String itemImageUrl) {
        return new RentalItemEntity(this.id, itemName, this.organization, isAvailable, rentalPeriod, itemImageUrl);
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}
