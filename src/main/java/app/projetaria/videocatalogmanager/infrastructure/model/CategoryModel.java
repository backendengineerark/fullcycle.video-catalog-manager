package app.projetaria.videocatalogmanager.infrastructure.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
    
import app.projetaria.videocatalogmanager.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "categories")
public class CategoryModel {
    
    @Id
    private UUID id;
    
    @Column
    private String name;
    
    @Column
    private String description;
    
    @Column
    private Boolean isActive;

    public CategoryModel fromCategory(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.isActive = category.getIsActive();

        return this;
    }

    public Category toCategory() {
        Category category = new Category(this.getId(), this.getName(), this.getDescription());
            if (Boolean.FALSE.equals(category.getIsActive())) {
                category.deactivate();
            }
            
        return category;
    }
}
