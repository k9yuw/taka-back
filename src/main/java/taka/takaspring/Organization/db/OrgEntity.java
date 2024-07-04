package taka.takaspring.Organization.db;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 시 컴파일 에러 발생시킴
@Entity
public class OrgEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String orgName;

    private UserEntity orgAdmin;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<UserEntity> memberList = new ArrayList<>();

    @OneToMany(mappedBy = "rentalItemEntity", cascade = CascadeType.ALL)
    private List<RentalItemEntity> rentalItemsList = new ArrayList<>();

}
