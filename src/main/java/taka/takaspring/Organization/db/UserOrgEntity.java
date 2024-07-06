package taka.takaspring.Organization.db;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import taka.takaspring.Member.db.UserEntity;

@Entity
@Getter
@Builder
public class UserOrgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_org_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private OrgEntity org;
}