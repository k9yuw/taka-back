package taka.takaspring.Member.db;

import jakarta.persistence.*;
import lombok.*;
import taka.takaspring.Member.db.enums.RoleType;
import taka.takaspring.common.BaseEntity;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 시 컴파일 에러 발생시킴
@Entity
public class UserEntity extends BaseEntity {

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

    @Builder
    public UserEntity(String email, String password, String name, String phoneNumber, String profileImageUrl, RoleType role){
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

}