package taka.takaspring.Membership.db;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Membership.db.enums.MembershipStatus;
import taka.takaspring.Organization.db.OrgEntity;

@Entity
@Getter
@Builder
public class MembershipEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status;

}