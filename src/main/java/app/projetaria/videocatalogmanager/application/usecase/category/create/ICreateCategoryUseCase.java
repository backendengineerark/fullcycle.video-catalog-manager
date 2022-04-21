package app.projetaria.videocatalogmanager.application.usecase.category.create;

import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;

public interface ICreateCategoryUseCase {
    
    CategoryOutputData execute(CreateCategoryInputData data);
}
