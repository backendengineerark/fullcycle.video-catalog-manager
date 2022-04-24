package app.projetaria.videocatalogmanager.api.category;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;
import app.projetaria.videocatalogmanager.application.usecase.category.create.CreateCategoryInputData;
import app.projetaria.videocatalogmanager.application.usecase.category.create.ICreateCategoryUseCase;
import app.projetaria.videocatalogmanager.application.usecase.category.findall.IFindAllCategoryUseCase;
import app.projetaria.videocatalogmanager.application.usecase.category.get.IFindByIdCategoryUseCase;
import app.projetaria.videocatalogmanager.application.usecase.category.remove.IRemoveCategoryUseCase;
import app.projetaria.videocatalogmanager.application.usecase.category.update.IUpdateCategoryUseCase;
import app.projetaria.videocatalogmanager.application.usecase.category.update.UpdateCategoryInputData;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CategoryEndpoint implements ICategoryEndpoint {

    private ICreateCategoryUseCase createUseCase;
    private IFindAllCategoryUseCase findAllUseCase;
    private IFindByIdCategoryUseCase findByIdUseCase;
    private IUpdateCategoryUseCase updateUseCase;
    private IRemoveCategoryUseCase removeUseCase;

    @Override
    public CategoryOutputData create(CreateCategoryInputData data) {
        return this.createUseCase.execute(data);
    }

    @Override
    public List<CategoryOutputData> findAll() {
        return this.findAllUseCase.execute();
    }

    @Override
    public CategoryOutputData findById(UUID id) {
        return this.findByIdUseCase.execute(id);
    }

    @Override
    public void update(UUID id, UpdateCategoryInputData data) {
        this.updateUseCase.execute(id, data);
    }

    @Override
    public void remove(UUID id) {
        this.removeUseCase.execute(id);
    }
}
