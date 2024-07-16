package taka.takaspring.Organization.db;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 시 컴파일 에러 발생시킴
@Entity
@Table(name = "org_entity")
public class OrgEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String orgName;

    @Column(nullable = false)
    private String department;

    private String orgDescription;


    @Builder
    public OrgEntity(Long id, String orgName, String department, String orgDescription){
        this.id = id;
        this.orgName = orgName;
        this.department = department;
        this.orgDescription = orgDescription;
    }


}
