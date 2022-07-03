package app.projetaria.videocatalogmanager.infrastructure.category;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import app.projetaria.videocatalogmanager.domain.category.CategorySearchQuery;
import app.projetaria.videocatalogmanager.domain.pagination.Pagination;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryEntity;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryRepository;
import app.projetaria.videocatalogmanager.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static app.projetaria.videocatalogmanager.infrastructure.utils.SpecificationUtils.like;

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
    public void deleteById(final CategoryId anId) {
        final String id = anId.getValue();
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        }
    }

    @Override
    public Optional<Category> findById(final CategoryId anId) {
        return this.repository.findById(anId.getValue())
                .map(CategoryEntity::toAggregate);
    }

    @Override
    public Category update(final Category aCategory) {
        return this.save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery aQuery) {
        PageRequest page = PageRequest.of(
            aQuery.page(),
            aQuery.perPage(),
            Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        Specification<CategoryEntity> specifications = Optional.ofNullable(aQuery.terms())
                .filter(term -> !term.isBlank())
                .map(term -> SpecificationUtils
                        .<CategoryEntity>like("name", term)
                        .or(like("description", term))
                ).orElse(null);

        Page<CategoryEntity> result =
                this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
            result.getNumber(),
            result.getSize(),
            result.getTotalElements(),
            result.map(CategoryEntity::toAggregate).toList()
        );
    }

    private Category save(final Category aCategory) {
        return this.repository.save(CategoryEntity.from(aCategory))
            .toAggregate();
    }
}
