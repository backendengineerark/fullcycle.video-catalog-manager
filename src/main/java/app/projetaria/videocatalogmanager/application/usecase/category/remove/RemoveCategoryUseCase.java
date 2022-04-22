package app.projetaria.videocatalogmanager.application.usecase.category.remove;

import java.util.UUID;

import org.springframework.stereotype.Component;

import app.projetaria.videocatalogmanager.application.exception.NotFoundException;
import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RemoveCategoryUseCase implements IRemoveCategoryUseCase {

    private ICategoryRepository repository;

    @Override
    public void execute(UUID id) {
        this.repository.findById(id)
            .ifPresentOrElse(
                category -> repository.remove(id), 
                () -> { 
                    throw new NotFoundException(String.format("Category %s not found to remove", id)); 
                });
    }
    
}
