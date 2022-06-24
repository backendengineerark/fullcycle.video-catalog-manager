package app.projetaria.videocatalogmanager.application.category.retrieve.list;

import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategorySearchQuery;
import app.projetaria.videocatalogmanager.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway gateway;

    public DefaultListCategoriesUseCase(CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final CategorySearchQuery aQuery) {
        return this.gateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}
