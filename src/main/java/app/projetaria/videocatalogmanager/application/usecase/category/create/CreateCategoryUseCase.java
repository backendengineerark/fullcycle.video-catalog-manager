package app.projetaria.videocatalogmanager.application.usecase.category.create;

import org.springframework.stereotype.Component;

import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;
import app.projetaria.videocatalogmanager.domain.entity.Category;
import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CreateCategoryUseCase implements ICreateCategoryUseCase {

    private ICategoryRepository repository;

    @Override
    public CategoryOutputData execute(final CreateCategoryInputData data) {
        Category category = this.repository.create(this.toDomain(data));
        return this.toOutput(category);
    }

    private Category toDomain(final CreateCategoryInputData data) {
        Category category = new Category(data.getName(), data.getDescription());
        if (Boolean.FALSE.equals(data.getIsActive())) {
            category.deactivate();
        }
        return category;
    }
    
    private CategoryOutputData toOutput(final Category category) {
        return CategoryOutputData.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .isActive(category.getIsActive())
            .build();
    }

}
