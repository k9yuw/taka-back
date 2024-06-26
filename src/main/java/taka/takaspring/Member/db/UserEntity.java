package taka.takaspring.Member.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import taka.takaspring.Member.db.enums.RoleType;
import taka.takaspring.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 32)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private RoleType role;

}