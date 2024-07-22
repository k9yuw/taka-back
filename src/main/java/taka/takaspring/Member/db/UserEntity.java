package taka.takaspring.Member.db;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import taka.takaspring.Member.db.enums.RoleType;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 시 컴파일 에러 발생시킴
@Entity
@Table(name = "user_entity")
@Audited(targetAuditMode = NOT_AUDITED)
public class UserEntity extends BaseEntity {

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


    @Builder
    public UserEntity(Long id, String email, String password, String name, String major, String studentNum, String phoneNumber, String profileImageUrl, RoleType role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.major = major;
        this.studentNum = studentNum;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public void update(String password, String name, String major, String studentNum, String phoneNumber) {
        this.password = (isValid(password)) ? password : this.password;
        this.name = (isValid(name)) ? name : this.name;
        this.major = (isValid(major)) ? major : this.major;
        this.studentNum = (isValid(studentNum)) ? studentNum : this.studentNum;
        this.phoneNumber = (isValid(phoneNumber)) ? phoneNumber : this.phoneNumber;
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }


}