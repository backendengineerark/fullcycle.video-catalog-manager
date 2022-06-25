package app.projetaria.videocatalogmanager.infrastructure.category;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.infrastructure.MySQLGatewayTest;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryEntity;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}
