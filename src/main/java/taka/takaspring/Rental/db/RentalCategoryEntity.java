package taka.takaspring.Rental.db;

import jakarta.persistence.*;
import lombok.*;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.common.BaseEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rental_category")
public class RentalCategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String categoryName;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private OrgEntity organization;

    public void updateCategory(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }
}
