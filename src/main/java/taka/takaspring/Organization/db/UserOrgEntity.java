package taka.takaspring.Organization.db;

import jakarta.persistence.*;
import taka.takaspring.Member.db.UserEntity;

@Entity
public class UserOrgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_org_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private OrgEntity org;
}
