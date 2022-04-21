package app.projetaria.videocatalogmanager.application.usecase.category.findall;

import java.util.List;

import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;

public interface IFindAllCategoryUseCase {

    List<CategoryOutputData> execute();
    
}
