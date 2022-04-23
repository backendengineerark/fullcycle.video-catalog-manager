package app.projetaria.videocatalogmanager.infrastructure.database.mysql;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import app.projetaria.videocatalogmanager.domain.entity.Category;
import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;
import app.projetaria.videocatalogmanager.infrastructure.database.SpringDataCategoryRepository;
import app.projetaria.videocatalogmanager.infrastructure.model.CategoryModel;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class MySQLCategoryRepository implements ICategoryRepository {

    private SpringDataCategoryRepository repository;

    @Override
    public Category create(Category category) {
        this.repository.save(new CategoryModel().fromCategory(category));
        return category;
    }

    @Override
    public List<Category> findAll() {
        return this.repository.findAll().parallelStream()
            .map(model -> model.toCategory()).collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return this.repository.findById(id)
            .map(model -> model.toCategory());
    }

    @Override
    public void update(Category category) {
        this.create(category);
    }

    @Override
    public void remove(UUID id) {
        this.repository.deleteById(id);
    }
    
}
