package app.projetaria.videocatalogmanager.domain.category;

import app.projetaria.videocatalogmanager.domain.exception.DomainException;
import app.projetaria.videocatalogmanager.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoryTests {
    
    @Test
    @DisplayName("Should create a category")
    public void givenValidParameters_whenCallNewCategory_thenReturnACategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category categoryCreated =
                Category.create(expectedName, expectedDescription, expectedStatus);

        assertDoesNotThrow(() -> categoryCreated.validate(new ThrowsValidationHandler()));

        assertThat(categoryCreated, notNullValue());
        assertThat(categoryCreated.getId(), notNullValue());
        assertThat(categoryCreated.getName(), is(expectedName));
        assertThat(categoryCreated.getDescription(), is(expectedDescription));
        assertThat(categoryCreated.getIsActive(), is(expectedStatus));
        assertThat(categoryCreated.getCreatedAt(), notNullValue());
        assertThat(categoryCreated.getUpdatedAt(), notNullValue());
        assertThat(categoryCreated.getDeletedAt(), nullValue());
    }

    @Test
    @DisplayName("Should throw DomainException when try create a category with nullable name")
    public void givenAnNullableName_whenCallNewCategoryAndValidate_thenThrownDomainException() {
        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final String expectedName = null;
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category categoryCreated = Category.create(expectedName, expectedDescription, expectedStatus);

        DomainException exception = assertThrows(
            DomainException.class,
            () -> categoryCreated.validate(new ThrowsValidationHandler()),
            "Expect create a category do throw, but it didn't");

        assertThat(exception.getErrors().size(), is(expectedErrorCount));
        assertThat(exception.getErrors().get(0).message(), is(expectedErrorMessage));
    }

    @Test
    @DisplayName("Should throw DomainException when try create a category with empty name")
    public void givenAnEmptyName_whenCallNewCategoryAndValidate_thenThrownDomainException() {
        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final String expectedName = "";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category categoryCreated = Category.create(expectedName, expectedDescription, expectedStatus);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> categoryCreated.validate(new ThrowsValidationHandler()),
                "Expect create a category do throw, but it didn't");

        assertThat(exception.getErrors().size(), is(expectedErrorCount));
        assertThat(exception.getErrors().get(0).message(), is(expectedErrorMessage));
    }

    @Test
    @DisplayName("Should throw DomainException when try create a category with name less than 3 characters")
    public void givenAnNameLessThan3Characters_whenCallNewCategoryAndValidate_thenThrownDomainException() {
        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should have between 3 and 255 characters";

        final String expectedName = "Co ";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category categoryCreated = Category.create(expectedName, expectedDescription, expectedStatus);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> categoryCreated.validate(new ThrowsValidationHandler()),
                "Expect create a category do throw, but it didn't");

        assertThat(exception.getErrors().size(), is(expectedErrorCount));
        assertThat(exception.getErrors().get(0).message(), is(expectedErrorMessage));
    }

    @Test
    @DisplayName("Should throw error when try create a category with name greater than 255 characters")
    public void givenAnNameGreaterThan255Characters_whenCallNewCategoryAndValidate_thenThrowError() {
        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should have between 3 and 255 characters";

        final String expectedName = """
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. 
                Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer 
                took a galley of type and scrambled it to make a type specimen book. It has sur
                """;
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category categoryCreated = Category.create(expectedName, expectedDescription, expectedStatus);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> categoryCreated.validate(new ThrowsValidationHandler()),
                "Expect create a category do throw, but it didn't");

        assertThat(exception.getErrors().size(), is(expectedErrorCount));
        assertThat(exception.getErrors().get(0).message(), is(expectedErrorMessage));
    }

    @Test
    @DisplayName("Should create a category with empty description")
    public void givenAnEmptyCategoryDescription_whenCallNewCategory_thenReturnACategoryWithEmptyDescription() {
        final String expectedName = "Comedy";
        final String expectedDescription = " ";
        final Boolean expectedStatus = true;

        final Category categoryCreated =
                Category.create(expectedName, expectedDescription, expectedStatus);

        assertDoesNotThrow(() -> categoryCreated.validate(new ThrowsValidationHandler()));

        assertThat(categoryCreated, notNullValue());
        assertThat(categoryCreated.getId(), notNullValue());
        assertThat(categoryCreated.getName(), is(expectedName));
        assertThat(categoryCreated.getDescription(), is(expectedDescription));
        assertThat(categoryCreated.getIsActive(), is(expectedStatus));
        assertThat(categoryCreated.getCreatedAt(), notNullValue());
        assertThat(categoryCreated.getUpdatedAt(), notNullValue());
        assertThat(categoryCreated.getDeletedAt(), nullValue());
    }

    @Test
    @DisplayName("Should create a disable category")
    public void givenADisableCategoryStatus_whenCallNewCategory_thenReturnADisableCategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = " ";
        final Boolean expectedStatus = false;

        final Category categoryCreated =
                Category.create(expectedName, expectedDescription, expectedStatus);

        assertDoesNotThrow(() -> categoryCreated.validate(new ThrowsValidationHandler()));

        assertThat(categoryCreated, notNullValue());
        assertThat(categoryCreated.getId(), notNullValue());
        assertThat(categoryCreated.getName(), is(expectedName));
        assertThat(categoryCreated.getDescription(), is(expectedDescription));
        assertThat(categoryCreated.getIsActive(), is(expectedStatus));
        assertThat(categoryCreated.getCreatedAt(), notNullValue());
        assertThat(categoryCreated.getUpdatedAt(), notNullValue());
        assertThat(categoryCreated.getDeletedAt(), notNullValue());
    }

    @Test
    @DisplayName("Should update a category")
    public void givenValidParameters_whenCallUpdateCategory_thenReturnACategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category categoryCreated =
                Category.create("Com", "Category", expectedStatus);

        assertDoesNotThrow(() -> categoryCreated.validate(new ThrowsValidationHandler()));

        final Category categoryUpdated =
                categoryCreated.update(expectedName, expectedDescription, expectedStatus);

        assertDoesNotThrow(() -> categoryUpdated.validate(new ThrowsValidationHandler()));

        assertThat(categoryUpdated, notNullValue());
        assertThat(categoryUpdated.getId(), notNullValue());
        assertThat(categoryUpdated.getName(), is(expectedName));
        assertThat(categoryUpdated.getDescription(), is(expectedDescription));
        assertThat(categoryUpdated.getIsActive(), is(expectedStatus));
        assertThat(categoryCreated.getCreatedAt(), notNullValue());
        assertThat(categoryCreated.getUpdatedAt(), notNullValue());
        assertThat(categoryCreated.getDeletedAt(), nullValue());
    }

    @Test
    @DisplayName("Should update a category")
    public void givenValidParameters_whenCallUpdateToInactiveCategory_thenReturnACategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = false;

        final Category categoryCreated =
                Category.create("Com", "Category", true);
        assertThat(categoryCreated.getIsActive(), is(true));
        assertThat(categoryCreated.getDeletedAt(), nullValue());

        assertDoesNotThrow(() -> categoryCreated.validate(new ThrowsValidationHandler()));

        final Category categoryUpdated =
                categoryCreated.update(expectedName, expectedDescription, expectedStatus);

        assertDoesNotThrow(() -> categoryUpdated.validate(new ThrowsValidationHandler()));

        assertThat(categoryUpdated, notNullValue());
        assertThat(categoryUpdated.getId(), notNullValue());
        assertThat(categoryUpdated.getName(), is(expectedName));
        assertThat(categoryUpdated.getDescription(), is(expectedDescription));
        assertThat(categoryUpdated.getIsActive(), is(expectedStatus));
        assertThat(categoryCreated.getCreatedAt(), notNullValue());
        assertThat(categoryCreated.getUpdatedAt(), notNullValue());
        assertThat(categoryCreated.getDeletedAt(), notNullValue());
    }

    @Test
    @DisplayName("Should inactivate a category")
    public void givenValidActiveCategory_whenCallDeactivateCategory_thenReturnInactivateCategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = false;

        final Category categoryCreated =
                Category.create(expectedName, expectedDescription, true);

        assertDoesNotThrow(() -> categoryCreated.validate(new ThrowsValidationHandler()));

        final Instant updatedBeforeInactivate = categoryCreated.getUpdatedAt();

        assertThat(categoryCreated.getIsActive(), is(true));
        assertThat(categoryCreated.getDeletedAt(), nullValue());

        Category categoryUpdated = categoryCreated.deactivate();

        assertDoesNotThrow(() -> categoryUpdated.validate(new ThrowsValidationHandler()));

        assertThat(categoryUpdated.getId(), is(categoryCreated.getId()));
        assertThat(categoryUpdated.getName(), is(categoryCreated.getName()));
        assertThat(categoryUpdated.getDescription(), is(categoryCreated.getDescription()));
        assertThat(categoryUpdated.getIsActive(), is(expectedStatus));
        assertThat(categoryUpdated.getCreatedAt(), notNullValue());
        assertThat(categoryUpdated.getUpdatedAt().isAfter(updatedBeforeInactivate), is(true));
        assertThat(categoryUpdated.getDeletedAt(), notNullValue());
    }

    @Test
    @DisplayName("Should create a category")
    public void givenValidInactiveCategory_whenCallActivateCategory_thenReturnActivateCategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category categoryCreated =
                Category.create(expectedName, expectedDescription, false);

        assertDoesNotThrow(() -> categoryCreated.validate(new ThrowsValidationHandler()));

        final Instant updatedBeforeActivate = categoryCreated.getUpdatedAt();

        assertThat(categoryCreated.getIsActive(), is(false));
        assertThat(categoryCreated.getDeletedAt(), notNullValue());

        Category categoryUpdated = categoryCreated.activate();

        assertDoesNotThrow(() -> categoryUpdated.validate(new ThrowsValidationHandler()));

        assertThat(categoryUpdated.getId(), is(categoryCreated.getId()));
        assertThat(categoryUpdated.getName(), is(categoryCreated.getName()));
        assertThat(categoryUpdated.getDescription(), is(categoryCreated.getDescription()));
        assertThat(categoryUpdated.getIsActive(), is(expectedStatus));
        assertThat(categoryUpdated.getCreatedAt(), notNullValue());
        assertThat(categoryUpdated.getUpdatedAt().isAfter(updatedBeforeActivate), is(true));
        assertThat(categoryUpdated.getDeletedAt(), nullValue());
    }

}
