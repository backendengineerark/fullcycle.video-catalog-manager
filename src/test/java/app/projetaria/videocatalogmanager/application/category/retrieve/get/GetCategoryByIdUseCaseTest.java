package app.projetaria.videocatalogmanager.application.category.retrieve.get;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import app.projetaria.videocatalogmanager.domain.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    @DisplayName("Should return a category by id")
    public void givenAValidCommand_whenCallGetCategory_shouldBeReturnCategory() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Category category = Category.create(expectedName, expectedDescription, expectedStatus);

        final CategoryId expectedId = category.getId();

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(category));

        final CategoryOutput categoryFounded = useCase.execute(expectedId.getValue());

        assertThat(categoryFounded.id(), is(expectedId));
        assertThat(categoryFounded.name(), is(expectedName));
        assertThat(categoryFounded.description(), is(expectedDescription));
        assertThat(categoryFounded.isActive(), is(expectedStatus));
        assertThat(categoryFounded.createdAt(), is(notNullValue()));
        assertThat(categoryFounded.updateAt(), is(notNullValue()));
        assertThat(categoryFounded.deletedAt(), is(nullValue()));
    }

    @Test
    @DisplayName("Should throw NotFoundException when try get a category with invalid ID")
    public void givenACommandWithInvalidID_whenCallGetCategory_shouldReturnNotFoundException() {
        final CategoryId expectedId = CategoryId.from("123");
        final String expectedErrorMessage = "Category with ID 123 was not found";

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());

        final DomainException exception = assertThrows(DomainException.class,
                () -> useCase.execute(expectedId.getValue()));

        assertThat(exception.getMessage(), is(expectedErrorMessage));
    }

    @Test
    @DisplayName("Should throw DomainException when Gateway throws random exception")
    public void givenAValidGetCommand_whenGatewayThrowsRandomException_shouldReturnAnException() {
        final CategoryId expectedId = CategoryId.from("123");
        final String expectedErrorMessage = "Gateway error";

        when(categoryGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue()));

        assertThat(exception.getMessage(), is(expectedErrorMessage));
    }
}
