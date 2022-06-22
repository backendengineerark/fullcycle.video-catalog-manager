package app.projetaria.videocatalogmanager.application.category.update;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import app.projetaria.videocatalogmanager.domain.exception.DomainException;
import app.projetaria.videocatalogmanager.domain.validation.handler.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTests {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    void cleanUp() {
        reset(gateway);
    }

    @Test
    @DisplayName("Should update a category")
    public void givenAValidUpdateCommand_whenCallUpdateCategory_shouldReturnCategoryId() {
        final Category actualCategory = Category.create("Film", "Cat", true);

        final CategoryId expectedId = actualCategory.getId();
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedStatus);

        when(gateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(actualCategory.clone()));

        when(gateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final UpdateCategoryOutput output = useCase.execute(command).get();

        assertThat(output, is(notNullValue()));
        assertThat(output.id(), is(notNullValue()));

        verify(gateway, times(1)).findById(eq(expectedId));

        verify(gateway, times(1)).update(argThat(updatedCategory -> {
            return Objects.equals(expectedName, updatedCategory.getName()) &&
                    Objects.equals(expectedDescription, updatedCategory.getDescription()) &&
                    Objects.equals(expectedStatus, updatedCategory.getIsActive()) &&
                    Objects.nonNull(updatedCategory.getId()) &&
                    Objects.nonNull(updatedCategory.getCreatedAt()) &&
                    updatedCategory.getUpdatedAt().isAfter(actualCategory.getUpdatedAt()) &&
                    Objects.isNull(updatedCategory.getDeletedAt());
        }));
    }

    @Test
    @DisplayName("Should throw DomainException when try update a category with nullable name")
    public void givenACommandWithNullableName_whenCallUpdateCategory_shouldThrowDomainException() {
        final Category actualCategory = Category.create("Film", "Cat", true);

        final CategoryId expectedId = actualCategory.getId();
        final String expectedName = null;
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedStatus);

        when(gateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(actualCategory.clone()));

        final Notification notification = useCase.execute(command).getLeft();

        assertThat(notification.getErrors().size(), is(expectedErrorCount));
        assertThat(notification.firstError().message(), is(expectedErrorMessage));
        verify(gateway, times(0)).update(any());
    }

    @Test
    @DisplayName("Should throw DomainException when try update a category with empty name")
    public void givenACommandWithEmptyName_whenCallCreateCategory_shouldThrowDomainException() {
        final Category actualCategory = Category.create("Film", "Cat", true);

        final CategoryId expectedId = actualCategory.getId();
        final String expectedName = "";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedStatus);

        when(gateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(actualCategory.clone()));

        final Notification notification = useCase.execute(command).getLeft();

        assertThat(notification.getErrors().size(), is(expectedErrorCount));
        assertThat(notification.firstError().message(), is(expectedErrorMessage));
        verify(gateway, times(0)).create(any());
    }

    @Test
    @DisplayName("Should update a category to inactive")
    public void givenAValidInactiveCommand_whenCallUpdateCategory_shouldReturnCategoryId() {
        final Category actualCategory = Category.create("Film", "Cat", true);

        final CategoryId expectedId = actualCategory.getId();
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = false;

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedStatus);

        when(gateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(actualCategory.clone()));

        when(gateway.update(any()))
                .thenAnswer(returnsFirstArg());

        assertThat(actualCategory.getIsActive(), is(true));
        assertThat(actualCategory.getDeletedAt(), is(nullValue()));

        final UpdateCategoryOutput output = useCase.execute(command).get();

        assertThat(output, is(notNullValue()));
        assertThat(output.id(), is(notNullValue()));

        verify(gateway, times(1)).findById(eq(expectedId));

        verify(gateway, times(1)).update(argThat(updatedCategory -> {
            return Objects.equals(expectedName, updatedCategory.getName()) &&
                    Objects.equals(expectedDescription, updatedCategory.getDescription()) &&
                    Objects.equals(expectedStatus, updatedCategory.getIsActive()) &&
                    Objects.nonNull(updatedCategory.getId()) &&
                    Objects.nonNull(updatedCategory.getCreatedAt()) &&
                    updatedCategory.getUpdatedAt().isAfter(actualCategory.getUpdatedAt()) &&
                    Objects.nonNull(updatedCategory.getDeletedAt());
        }));
    }

    @Test
    @DisplayName("Should throw DomainException when Gateway throws random exception")
    public void givenAValidUpdateCommand_whenGatewayThrowsRandomException_shouldReturnAnException() {
        final Category actualCategory = Category.create("Film", "Cat", true);

        final CategoryId expectedId = actualCategory.getId();
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;
        final Integer expectedErrorCount = 1;

        final String expectedErrorMessage = "Gateway error";

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedStatus);

        when(gateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(actualCategory.clone()));

        when(gateway.update(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final Notification notification = useCase.execute(command).getLeft();

        assertThat(notification.getErrors().size(), is(expectedErrorCount));
        assertThat(notification.firstError().message(), is(expectedErrorMessage));

        verify(gateway, times(1)).update(argThat(aCategory -> {
            return Objects.equals(expectedName, aCategory.getName()) &&
                    Objects.equals(expectedDescription, aCategory.getDescription()) &&
                    Objects.equals(expectedStatus, aCategory.getIsActive()) &&
                    Objects.nonNull(aCategory.getId()) &&
                    Objects.nonNull(aCategory.getCreatedAt()) &&
                    Objects.nonNull(aCategory.getUpdatedAt()) &&
                    Objects.isNull(aCategory.getDeletedAt());
        }));
    }

    @Test
    @DisplayName("Should throw NotFoundException when try update a category with invalid ID")
    public void givenACommandWithInvalidID_whenCallUpdateCategory_shouldReturnCategoryId() {
        final String expectedId = "123";
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;
        final String expectedErrorMessage = "Category with ID 123 was not found";
        final Integer errorCount = 1;

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(expectedId, expectedName, expectedDescription, expectedStatus);

        when(gateway.findById(eq(CategoryId.from(expectedId))))
                .thenReturn(Optional.empty());

        final DomainException exception =
                assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(gateway, times(1)).findById(eq(CategoryId.from(expectedId)));
        verify(gateway, times(0)).update(any());

        assertThat(exception.getErrors().size(), is(errorCount));
        assertThat(exception.getMessage(), is(expectedErrorMessage));

    }
}
