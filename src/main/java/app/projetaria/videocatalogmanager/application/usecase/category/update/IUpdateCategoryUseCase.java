package app.projetaria.videocatalogmanager.application.usecase.category.update;

import java.util.UUID;

public interface IUpdateCategoryUseCase {

    void execute(final UUID id, final UpdateCategoryInputData data);
    
}
