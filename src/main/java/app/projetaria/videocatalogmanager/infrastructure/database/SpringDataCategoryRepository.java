package app.projetaria.videocatalogmanager.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;

import app.projetaria.videocatalogmanager.infrastructure.model.CategoryModel;

import java.util.UUID;

public interface SpringDataCategoryRepository extends JpaRepository<CategoryModel, UUID> {
    
}