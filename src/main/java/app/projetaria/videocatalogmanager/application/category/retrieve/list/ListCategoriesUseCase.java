package app.projetaria.videocatalogmanager.application.category.retrieve.list;

import app.projetaria.videocatalogmanager.application.UseCase;
import app.projetaria.videocatalogmanager.domain.category.CategorySearchQuery;
import app.projetaria.videocatalogmanager.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase
        extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
