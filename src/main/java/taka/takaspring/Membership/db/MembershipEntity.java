package taka.takaspring.Membership.db;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.common.BaseEntity;

@Entity
@Getter
@Builder
public class MembershipEntity extends BaseEntity {

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

    @Column(nullable = false)
    private boolean isAdmin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private MembershipStatus status;

    public enum MembershipStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    public MembershipEntity(Long id, UserEntity user, OrgEntity org, MembershipStatus status){
        this.id = id;
        this.user = user;
        this.org = org;
        this.status = status;
    }

}