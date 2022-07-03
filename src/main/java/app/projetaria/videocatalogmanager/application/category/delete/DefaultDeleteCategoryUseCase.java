package app.projetaria.videocatalogmanager.application.category.delete;

import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway gateway;

    public DefaultDeleteCategoryUseCase(CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public void execute(final String anId) {
        this.gateway.deleteById(CategoryId.from(anId));
    }
}
