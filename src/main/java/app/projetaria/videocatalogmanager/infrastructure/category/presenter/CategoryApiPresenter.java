package app.projetaria.videocatalogmanager.infrastructure.category.presenter;

import app.projetaria.videocatalogmanager.application.category.retrieve.get.CategoryOutput;
import app.projetaria.videocatalogmanager.application.category.retrieve.list.CategoryListOutput;
import app.projetaria.videocatalogmanager.infrastructure.category.model.CategoryApiListResponse;
import app.projetaria.videocatalogmanager.infrastructure.category.model.CategoryApiResponse;

public interface CategoryApiPresenter {

    static CategoryApiResponse present(final CategoryOutput output) {
        return new CategoryApiResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updateAt(),
                output.deletedAt()
        );
    }

    static CategoryApiListResponse present(final CategoryListOutput output) {
        return new CategoryApiListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
