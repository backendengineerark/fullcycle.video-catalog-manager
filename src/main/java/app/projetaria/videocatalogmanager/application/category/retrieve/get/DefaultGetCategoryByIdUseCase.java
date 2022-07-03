package app.projetaria.videocatalogmanager.application.category.retrieve.get;

import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import app.projetaria.videocatalogmanager.domain.exception.DomainException;
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

    private Supplier<DomainException> notNull(final CategoryId anId) {
        return () -> DomainException.with(
                new Error("Category with ID %s was not found".formatted(anId.getValue())));
    }
}
