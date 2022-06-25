package app.projetaria.videocatalogmanager.infrastructure.category;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import app.projetaria.videocatalogmanager.domain.category.CategorySearchQuery;
import app.projetaria.videocatalogmanager.domain.pagination.Pagination;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryEntity;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Category create(final Category aCategory) {
        return this.save(aCategory);
    }

    @Override
    public void deleteById(CategoryId anId) {

    }

    @Override
    public Optional<Category> findById(CategoryId anId) {
        return Optional.empty();
    }

    @Override
    public Category update(final Category aCategory) {
        return this.save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        return null;
    }

    private Category save(final Category aCategory) {
        return this.repository.save(CategoryEntity.from(aCategory))
            .toAggregate();
    }
}
