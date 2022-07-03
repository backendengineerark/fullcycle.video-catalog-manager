package app.projetaria.videocatalogmanager.application.category.retrieve.list;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;

import java.time.Instant;

public record CategoryListOutput(
        CategoryId id,
        String name,
        String description,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static CategoryListOutput from(final Category aCategory) {
        return new CategoryListOutput(
            aCategory.getId(),
            aCategory.getName(),
            aCategory.getDescription(),
            aCategory.getIsActive(),
            aCategory.getCreatedAt(),
            aCategory.getUpdatedAt(),
            aCategory.getDeletedAt()
        );
    }
}
