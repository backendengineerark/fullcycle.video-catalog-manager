package app.projetaria.videocatalogmanager.infrastructure.category;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import app.projetaria.videocatalogmanager.domain.category.CategorySearchQuery;
import app.projetaria.videocatalogmanager.domain.pagination.Pagination;
import app.projetaria.videocatalogmanager.infrastructure.MySQLGatewayTest;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryEntity;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway gateway;

    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should create a category with success")
    public void givenAValidCategory_whenCallsCreate_thenReturnANewCategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category category = Category.create(expectedName, expectedDescription, expectedStatus);

        assertThat(repository.count(), is(0l));

        final Category categoryCreated = gateway.create(category);

        assertThat(repository.count(), is(1l));

        assertThat(categoryCreated.getId(), is(category.getId()));
        assertThat(categoryCreated.getName(), is(category.getName()));
        assertThat(categoryCreated.getDescription(), is(category.getDescription()));
        assertThat(categoryCreated.getIsActive(), is(category.getIsActive()));
        assertThat(categoryCreated.getCreatedAt(), is(category.getCreatedAt()));
        assertThat(categoryCreated.getUpdatedAt(), is(category.getUpdatedAt()));
        assertThat(categoryCreated.getDeletedAt(), is(category.getDeletedAt()));

        CategoryEntity categoryEntity = repository.findById(category.getId().getValue()).get();

        assertThat(categoryEntity.getId(), is(category.getId().getValue()));
        assertThat(categoryEntity.getName(), is(category.getName()));
        assertThat(categoryEntity.getDescription(), is(category.getDescription()));
        assertThat(categoryEntity.getActive(), is(category.getIsActive()));
        assertThat(categoryEntity.getCreatedAt(), is(category.getCreatedAt()));
        assertThat(categoryEntity.getUpdatedAt(), is(category.getUpdatedAt()));
        assertThat(categoryEntity.getDeletedAt(), is(category.getDeletedAt()));
    }

    @Test
    @DisplayName("Should update a category with success")
    public void givenAValidCategory_whenCallsUpdate_thenReturnAUpdatedCategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category category = Category.create("Comedi", "C0medi Categori", expectedStatus);

        assertThat(repository.count(), is(0l));

        repository.saveAndFlush(CategoryEntity.from(category));

        assertThat(repository.count(), is(1l));

        final Category categoryToUpdate = category.clone()
                .update(expectedName, expectedDescription, expectedStatus);

        final Category categoryUpdated = gateway.update(categoryToUpdate);

        assertThat(repository.count(), is(1l));

        assertThat(categoryUpdated.getId(), is(category.getId()));
        assertThat(categoryUpdated.getName(), is(expectedName));
        assertThat(categoryUpdated.getDescription(), is(expectedDescription));
        assertThat(categoryUpdated.getIsActive(), is(expectedStatus));
        assertThat(categoryUpdated.getCreatedAt(), is(category.getCreatedAt()));
        assertThat(categoryUpdated.getUpdatedAt().isAfter(category.getUpdatedAt()), is(true));
        assertThat(categoryUpdated.getDeletedAt(), is(category.getDeletedAt()));

        CategoryEntity categoryEntity = repository.findById(category.getId().getValue()).get();

        assertThat(categoryEntity.getId(), is(categoryUpdated.getId().getValue()));
        assertThat(categoryEntity.getName(), is(expectedName));
        assertThat(categoryEntity.getDescription(), is(expectedDescription));
        assertThat(categoryEntity.getActive(), is(expectedStatus));
        assertThat(categoryEntity.getCreatedAt(), is(category.getCreatedAt()));
        assertThat(categoryEntity.getUpdatedAt().isAfter(category.getUpdatedAt()), is(true));
        assertThat(categoryEntity.getDeletedAt(), is(category.getDeletedAt()));
    }

    @Test
    @DisplayName("Should find by id a category with success")
    public void givenAValidId_whenCallsFindById_thenReturnACategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category category = Category.create(expectedName, expectedDescription, expectedStatus);

        assertThat(repository.count(), is(0l));

        repository.saveAndFlush(CategoryEntity.from(category));

        assertThat(repository.count(), is(1l));

        final Category categoryFounded = gateway.findById(category.getId()).get();

        assertThat(repository.count(), is(1l));

        assertThat(categoryFounded.getId(), is(category.getId()));
        assertThat(categoryFounded.getName(), is(expectedName));
        assertThat(categoryFounded.getDescription(), is(expectedDescription));
        assertThat(categoryFounded.getIsActive(), is(expectedStatus));
        assertThat(categoryFounded.getCreatedAt(), is(category.getCreatedAt()));
        assertThat(categoryFounded.getUpdatedAt(), is(category.getUpdatedAt()));
        assertThat(categoryFounded.getDeletedAt(), is(category.getDeletedAt()));
    }

    @Test
    @DisplayName("Should throw exception when try find a category with not stored id")
    public void givenANotStoredId_whenCallsFindById_thenReturnAnException() {
        assertThat(repository.count(), is(0l));

        final Optional<Category> categoryFounded = gateway.findById(CategoryId.from("123"));

        assertThat(repository.count(), is(0l));

        assertThat(categoryFounded.isEmpty(), is(true));
    }

    @Test
    @DisplayName("Should find all categories paginated with success")
    public void givenAValidQuery_whenCallsFindAll_thenReturnPaginatedCategories() {
        final Integer expectedPage = 0;
        final Integer expectedPerPage = 1;
        final Long expectedTotalItems = 3l;

        final Category comedy = Category.create("Comedy", "Comedy category", true);
        final Category action = Category.create("Action", "Action category", true);
        final Category horror = Category.create("Horror", "Horror category", true);

        assertThat(repository.count(), is(0l));

        repository.saveAllAndFlush(List.of(
                CategoryEntity.from(comedy),
                CategoryEntity.from(action),
                CategoryEntity.from(horror)
        ));

        assertThat(repository.count(), is(3l));

        final CategorySearchQuery query = new CategorySearchQuery(0, 1, "", "name", "asc");
        Pagination<Category> result = gateway.findAll(query);

        assertThat(result.currentPage(), is(expectedPage));
        assertThat(result.perPage(), is(expectedPerPage));
        assertThat(result.total(), is(expectedTotalItems));
        assertThat(result.items().size(), is(expectedPerPage));
        assertThat(result.items().get(0).getId(), is(action.getId()));
    }

    @Test
    @DisplayName("Should return empty list paginated of categories")
    public void givenAValidQuery_whenCallsFindAll_thenReturnEmptyPaginatedCategories() {
        final Integer expectedPage = 0;
        final Integer expectedPerPage = 1;
        final Long expectedTotalItems = 0l;

        assertThat(repository.count(), is(0l));

        final CategorySearchQuery query = new CategorySearchQuery(0, 1, "", "name", "asc");
        Pagination<Category> result = gateway.findAll(query);

        assertThat(result.currentPage(), is(expectedPage));
        assertThat(result.perPage(), is(expectedPerPage));
        assertThat(result.total(), is(expectedTotalItems));
        assertThat(result.items().size(), is(0));
    }

    @Test
    @DisplayName("Should find all categories with 3 pages")
    public void givenAValidQuery_whenCallsFindAll_thenReturnThreePagesCategories() {
        Integer expectedPage = 0;
        final Integer expectedPerPage = 1;
        final Long expectedTotalItems = 3l;

        final Category comedy = Category.create("Comedy", "Comedy category", true);
        final Category action = Category.create("Action", "Action category", true);
        final Category horror = Category.create("Horror", "Horror category", true);

        assertThat(repository.count(), is(0l));

        repository.saveAllAndFlush(List.of(
                CategoryEntity.from(comedy),
                CategoryEntity.from(action),
                CategoryEntity.from(horror)
        ));

        assertThat(repository.count(), is(3l));

        CategorySearchQuery query = new CategorySearchQuery(0, 1, "", "name", "asc");
        Pagination<Category> result = gateway.findAll(query);

        assertThat(result.currentPage(), is(expectedPage));
        assertThat(result.perPage(), is(expectedPerPage));
        assertThat(result.total(), is(expectedTotalItems));
        assertThat(result.items().size(), is(expectedPerPage));
        assertThat(result.items().get(0).getId(), is(action.getId()));

        // page 1
        expectedPage = 1;
        query = new CategorySearchQuery(1, 1, "", "name", "asc");
        result = gateway.findAll(query);

        assertThat(result.currentPage(), is(expectedPage));
        assertThat(result.perPage(), is(expectedPerPage));
        assertThat(result.total(), is(expectedTotalItems));
        assertThat(result.items().size(), is(expectedPerPage));
        assertThat(result.items().get(0).getId(), is(comedy.getId()));

        // page 2
        expectedPage = 2;
        query = new CategorySearchQuery(2, 1, "", "name", "asc");
        result = gateway.findAll(query);

        assertThat(result.currentPage(), is(expectedPage));
        assertThat(result.perPage(), is(expectedPerPage));
        assertThat(result.total(), is(expectedTotalItems));
        assertThat(result.items().size(), is(expectedPerPage));
        assertThat(result.items().get(0).getId(), is(horror.getId()));
    }

    @Test
    @DisplayName("Should find all categories when name starts with Act")
    public void givenAValidQueryWithTermNameAct_whenCallsFindAll_thenReturnPaginatedCategories() {
        final Integer expectedPage = 0;
        final Integer expectedPerPage = 1;
        final Long expectedTotalItems = 1l;

        final Category comedy = Category.create("Comedy", "Comedy category", true);
        final Category action = Category.create("Action", "Action category", true);
        final Category horror = Category.create("Horror", "Horror category", true);

        assertThat(repository.count(), is(0l));

        repository.saveAllAndFlush(List.of(
                CategoryEntity.from(comedy),
                CategoryEntity.from(action),
                CategoryEntity.from(horror)
        ));

        assertThat(repository.count(), is(3l));

        final CategorySearchQuery query = new CategorySearchQuery(0, 1, "act", "name", "asc");
        Pagination<Category> result = gateway.findAll(query);

        assertThat(result.currentPage(), is(expectedPage));
        assertThat(result.perPage(), is(expectedPerPage));
        assertThat(result.total(), is(expectedTotalItems));
        assertThat(result.items().size(), is(expectedPerPage));
        assertThat(result.items().get(0).getId(), is(action.getId()));
    }

    public void givenAValidQueryWithTermDescriptionTop_whenCallsFindAll_thenReturnPaginatedCategories() {
        final Integer expectedPage = 0;
        final Integer expectedPerPage = 1;
        final Integer expectedTotalItems = 1;

        final Category comedy = Category.create("Comedy", "Comedy category", true);
        final Category action = Category.create("Action", "Action category", true);
        final Category horror = Category.create("Horror", "Top 10 week", true);

        assertThat(repository.count(), is(0l));

        repository.saveAllAndFlush(List.of(
                CategoryEntity.from(comedy),
                CategoryEntity.from(action),
                CategoryEntity.from(horror)
        ));

        assertThat(repository.count(), is(3l));

        final CategorySearchQuery query = new CategorySearchQuery(0, 1, "top", "name", "asc");
        Pagination<Category> result = gateway.findAll(query);

        assertThat(result.currentPage(), is(expectedPage));
        assertThat(result.perPage(), is(expectedPerPage));
        assertThat(result.total(), is(expectedTotalItems));
        assertThat(result.items().size(), is(expectedPerPage));
        assertThat(result.items().get(0).getId(), is(horror.getId()));
    }

    @Test
    @DisplayName("Should delete a category with success")
    public void givenAValidId_whenCallsDelete_shouldBeOK() {
        final Category category = Category.create("Comedy", "Comedy category", true);

        assertThat(repository.count(), is(0l));

        repository.saveAndFlush(CategoryEntity.from(category));

        assertThat(repository.count(), is(1l));

        gateway.deleteById(category.getId());

        assertThat(repository.count(), is(0l));
    }

    @Test
    @DisplayName("Should throw exception when try delete a category with invalid id")
    public void givenAInvalidId_whenCallsDelete_shouldReturnAnException() {
        gateway.deleteById(CategoryId.from("INVALID"));

        assertThat(repository.count(), is(0l));
    }
}
