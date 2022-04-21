package app.projetaria.videocatalogmanager.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.projetaria.videocatalogmanager.domain.entity.Category;
import app.projetaria.videocatalogmanager.domain.exception.DomainException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryTests {
    
    @Test
    @DisplayName("Should create a category")
    public void shouldCreateCategory() {
        final UUID id = UUID.randomUUID();
        final String name = "Comedy";
        final String description = "Comedy category";

        final Category entity = new Category(id, name, description);

        assertThat(entity.getId(), is(id));
        assertThat(entity.getName(), is(name));
        assertThat(entity.getDescription(), is(description));
        assertThat(entity.getIsActive(), is(Boolean.TRUE));
    }

    @Test
    @DisplayName("Should deactivate a category")
    public void shouldDeactivateCategory() {
        final String name = "Comedy";
        final String description = "Comedy category";

        final Category entity = new Category(name, description);
        entity.deactivate();

        assertThat(entity.getName(), is(name));
        assertThat(entity.getDescription(), is(description));
        assertThat(entity.getIsActive(), is(Boolean.FALSE));
    }

    @Test
    @DisplayName("Should activate a category from inactive")
    public void shouldActivateCategoryFromDeactivate() {
        final String name = "Comedy";
        final String description = "Comedy category";

        final Category entity = new Category(name, description);
        entity.deactivate();
        entity.activate();

        assertThat(entity.getName(), is(name));
        assertThat(entity.getDescription(), is(description));
        assertThat(entity.getIsActive(), is(Boolean.TRUE));
    }

    @Test
    @DisplayName("Should throw DomainException when try crate a category with name nullable")
    public void shouldThrowExceptionWhenTryCreateCategoryWithNameNullable() {
        final String exceptionMessage = "Name cannot be null";
        final String name = null;
        final String description = "Wa category";

        DomainException exception = assertThrows(
            DomainException.class, 
            () -> new Category(name, description), 
            "Expect instantiate Category do throw, but it didn't");
        
        assertThat(exception.getMessage(), is(exceptionMessage));
    }

    @Test
    @DisplayName("Should throw DomainException when try crate a category with name less than 3 characters")
    public void shouldThrowExceptionWhenTryCreateCategoryWithNameLessThan3Characters() {
        final String exceptionMessage = "Name must be at last 3 characters";
        final String name = "Wa";
        final String description = "Wa category";

        DomainException exception = assertThrows(
            DomainException.class, 
            () -> new Category(name, description), 
            "Expect instantiate Category do throw, but it didn't");
        
        assertThat(exception.getMessage(), is(exceptionMessage));
    }
}
