package taka.takaspring.Member.db;

import jakarta.persistence.*;
import lombok.*;
import taka.takaspring.Member.db.enums.RoleType;
import taka.takaspring.Membership.db.MembershipEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 시 컴파일 에러 발생시킴
@Entity
@Table(name = "user_entity")
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
    private String major;

    @Column(nullable = false)
    private String studentNum;

    @Column(nullable = false)
    private String phoneNumber;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MembershipEntity> userOrgList = new ArrayList<>();


    @Builder
    public UserEntity(Long id, String email, String password, String name, String major, String studentNum, String phoneNumber, String profileImageUrl, RoleType role, List<MembershipEntity> userOrgList){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.major = major;
        this.studentNum = studentNum;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.userOrgList = userOrgList;
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