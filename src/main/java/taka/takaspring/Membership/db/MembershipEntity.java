package taka.takaspring.Membership.db;

import jakarta.persistence.*;
import lombok.*;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.common.BaseEntity;
import org.hibernate.envers.Audited;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "membership_entity")
@Audited
@Builder(toBuilder = true)
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

    @Builder
    public MembershipEntity(Long id, UserEntity user, OrgEntity org, boolean isAdmin, MembershipStatus status){
        this.id = id;
        this.user = user;
        this.org = org;
        this.isAdmin = isAdmin;
        this.status = status;
    }
}