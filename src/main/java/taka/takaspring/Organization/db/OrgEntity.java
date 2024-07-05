package taka.takaspring.Organization.db;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 시 컴파일 에러 발생시킴
@Entity
public class OrgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String orgName;

    private UserEntity orgAdmin;

    @OneToMany(mappedBy = "org", cascade = CascadeType.ALL)
    private List<UserOrgEntity> userOrgList = new ArrayList<>();

// 특정 단체가 가지는 대여 물품의 목록은 이미 RentalItemEntity에 외래키로 지정이 되어있다.
// 즉 쿼리의 시작은 RentalItemEntity이기 때문에 굳이 대여물품 리스트를 양방향 매핑 할 필요는 없음.
// 근데 또 바로 조회가 필요할 때는 그냥 리스트를 넣어놓는게 좋을수도 있음
// 사실 단방향으로 모든걸 해도 문제는 없는데 필요에 의해 양방향이 필요할 때도 있음

//    @OneToMany(mappedBy = "rentalItemEntity", cascade = CascadeType.ALL)
//    private List<RentalItemEntity> rentalItemsList = new ArrayList<>();


    @Builder
    public OrgEntity(Long id, String orgName, UserEntity orgAdmin, List<UserOrgEntity> userOrgList){
        this.id = id;
        this.orgName = orgName;
        this.orgAdmin = orgAdmin;
        this.userOrgList = userOrgList;
    }

}
