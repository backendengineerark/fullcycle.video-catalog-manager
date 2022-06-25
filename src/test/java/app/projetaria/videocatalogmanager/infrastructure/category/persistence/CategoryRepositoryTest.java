package app.projetaria.videocatalogmanager.infrastructure.category.persistence;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.infrastructure.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MySQLGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should throw Exception when try create a category with nullable name")
    public void givenACategoryWithNullableName_whenCallsSave_shouldThrowException() {
        final String errorPropertyName = "name";
        final String errorMessage =
                "not-null property references a null or transient value : app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryEntity.name";
        final Category category = Category.create("Comedy", "Comedy category", true);

        final CategoryEntity entity = CategoryEntity.from(category);
        entity.setName(null);

        final DataIntegrityViolationException exception =
            assertThrows(DataIntegrityViolationException.class,
                () -> repository.save(entity));

        PropertyValueException cause =
                assertInstanceOf(PropertyValueException.class, exception.getCause());

        assertThat(cause.getPropertyName(), is(errorPropertyName));
        assertThat(cause.getMessage(), is(errorMessage));
    }

    @Test
    @DisplayName("Should throw Exception when try create a category with nullable createdAt")
    public void givenACategoryWithNullableCreatedAt_whenCallsSave_shouldThrowException() {
        final String errorPropertyName = "createdAt";
        final String errorMessage =
                "not-null property references a null or transient value : app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryEntity.createdAt";
        final Category category = Category.create("Comedy", "Comedy category", true);

        final CategoryEntity entity = CategoryEntity.from(category);
        entity.setCreatedAt(null);

        final DataIntegrityViolationException exception =
                assertThrows(DataIntegrityViolationException.class,
                        () -> repository.save(entity));

        PropertyValueException cause =
                assertInstanceOf(PropertyValueException.class, exception.getCause());

        assertThat(cause.getPropertyName(), is(errorPropertyName));
        assertThat(cause.getMessage(), is(errorMessage));
    }

    @Test
    @DisplayName("Should throw Exception when try create a category with nullable updatedAt")
    public void givenACategoryWithNullableUpdatedAt_whenCallsSave_shouldThrowException() {
        final String errorPropertyName = "updatedAt";
        final String errorMessage =
                "not-null property references a null or transient value : app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryEntity.updatedAt";
        final Category category = Category.create("Comedy", "Comedy category", true);

        final CategoryEntity entity = CategoryEntity.from(category);
        entity.setUpdatedAt(null);

        final DataIntegrityViolationException exception =
                assertThrows(DataIntegrityViolationException.class,
                        () -> repository.save(entity));

        PropertyValueException cause =
                assertInstanceOf(PropertyValueException.class, exception.getCause());

        assertThat(cause.getPropertyName(), is(errorPropertyName));
        assertThat(cause.getMessage(), is(errorMessage));
    }
}
