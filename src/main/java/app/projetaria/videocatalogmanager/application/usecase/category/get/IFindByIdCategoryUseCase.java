package app.projetaria.videocatalogmanager.application.usecase.category.get;

import java.util.UUID;

import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;

public interface IFindByIdCategoryUseCase {

    CategoryOutputData execute(UUID id);
}
