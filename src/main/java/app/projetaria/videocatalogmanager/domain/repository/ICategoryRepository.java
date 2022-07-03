package app.projetaria.videocatalogmanager.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import app.projetaria.videocatalogmanager.domain.category.Category;

public interface ICategoryRepository {

    Category create(final Category category);
    List<Category> findAll();
    Optional<Category> findById(final UUID id);
    void update(final Category category);
    void remove(final UUID id);
}
