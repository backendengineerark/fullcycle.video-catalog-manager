package app.projetaria.videocatalogmanager.application.category.update;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;

public record UpdateCategoryOutput(String id) {

    public static UpdateCategoryOutput from(String anId) {
        return new UpdateCategoryOutput(anId);
    }

    public static UpdateCategoryOutput from(Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId().getValue());
    }
}
