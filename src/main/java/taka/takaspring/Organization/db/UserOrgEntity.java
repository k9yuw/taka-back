package taka.takaspring.Organization.db;

import jakarta.persistence.*;
import taka.takaspring.Member.db.UserEntity;

@Entity
public class UserOrgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private OrgEntity org;
}
