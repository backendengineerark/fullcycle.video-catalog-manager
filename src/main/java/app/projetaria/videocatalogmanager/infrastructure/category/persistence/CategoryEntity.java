package app.projetaria.videocatalogmanager.infrastructure.category.persistence;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity {

    private CategoryEntity(final String id, final String name,
                           final String description, final Boolean active,
                           final Instant createdAt, final Instant updatedAt,
                           final Instant deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    public static CategoryEntity from(final Category aCategory) {
        return new CategoryEntity(aCategory.getId().getValue(), aCategory.getName(),
            aCategory.getDescription(), aCategory.getIsActive(),
            aCategory.getCreatedAt(), aCategory.getUpdatedAt(), aCategory.getDeletedAt());
    }

    public Category toAggregate() {
        return Category.with(CategoryId.from(id), this.name, this.description,
            this.active, this.createdAt, this.updatedAt, this.deletedAt);
    }
}
