package app.projetaria.videocatalogmanager.application.category.retrieve.get;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;

import java.time.Instant;

public record CategoryOutput(CategoryId id, String name,
                             String description, Boolean isActive,
                             Instant createdAt, Instant updateAt, Instant deletedAt) {

    public static CategoryOutput from(final Category aCategory) {
        return new CategoryOutput(aCategory.getId(), aCategory.getName(),
                aCategory.getDescription(), aCategory.getIsActive(),
                aCategory.getCreatedAt(), aCategory.getUpdatedAt(), aCategory.getDeletedAt());
    }
}
