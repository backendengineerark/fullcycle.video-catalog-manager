package app.projetaria.videocatalogmanager.application.category.retrieve.get;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import app.projetaria.videocatalogmanager.domain.exception.DomainException;
import app.projetaria.videocatalogmanager.domain.exception.NotFoundException;
import app.projetaria.videocatalogmanager.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway gateway;

    public DefaultGetCategoryByIdUseCase(CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public CategoryOutput execute(String anId) {
        final CategoryId id = CategoryId.from(anId);

        return this.gateway.findById(id)
            .map(CategoryOutput::from)
            .orElseThrow(notNull(id));
    }

    private Supplier<NotFoundException> notNull(final CategoryId anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }
}
