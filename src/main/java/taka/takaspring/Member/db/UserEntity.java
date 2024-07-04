package taka.takaspring.Member.db;

import jakarta.persistence.*;
import lombok.*;
import taka.takaspring.Member.db.enums.RoleType;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.UserOrgEntity;
import taka.takaspring.common.BaseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 시 컴파일 에러 발생시킴
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy="user")
    private List<UserOrgEntity> userOrgList = new ArrayList<>();


    @Builder
    public UserEntity(Long id, String email, String password, String name, String phoneNumber, String profileImageUrl, RoleType role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public void update(String password, String name, String phoneNumber, RoleType role) {
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
        if (role != null) {
            this.role = role;
        }
    }

}