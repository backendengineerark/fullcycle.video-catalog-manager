package app.projetaria.videocatalogmanager.application.category.create;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;

public record CreateCategoryOutput(CategoryId id) {

    public static CreateCategoryOutput from(Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId());
    }
}
