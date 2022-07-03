package app.projetaria.videocatalogmanager.application.category.create;

import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.validation.handler.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTests {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    void cleanUp() {
        reset(gateway);
    }

    @Test
    @DisplayName("Should create a category")
    public void givenAValidCreateCommand_whenCallCreateCategory_shouldReturnCategoryId() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedStatus);

        when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final CreateCategoryOutput output = useCase.execute(command).get();

        assertThat(output, is(notNullValue()));
        assertThat(output.id(), is(notNullValue()));

        verify(gateway, times(1)).create(argThat(aCategory -> {
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
    @DisplayName("Should throw DomainException when try create a category with nullable name")
    public void givenACommandWithNullableName_whenCallCreateCategory_shouldThrowDomainException() {
        final String expectedName = null;
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedStatus);

        final Notification notification = useCase.execute(command).getLeft();

        assertThat(notification.getErrors().size(), is(expectedErrorCount));
        assertThat(notification.firstError().message(), is(expectedErrorMessage));
        verify(gateway, times(0)).create(any());
    }

    @Test
    @DisplayName("Should throw DomainException when try create a category with empty name")
    public void givenACommandWithEmptyName_whenCallCreateCategory_shouldThrowDomainException() {
        final String expectedName = "";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedStatus);

        final Notification notification = useCase.execute(command).getLeft();

        assertThat(notification.getErrors().size(), is(expectedErrorCount));
        assertThat(notification.firstError().message(), is(expectedErrorMessage));
        verify(gateway, times(0)).create(any());
    }

    @Test
    @DisplayName("Should create a inactive category")
    public void givenACommandWithInactiveStatus_whenCallCreateCategory_shouldReturnCategoryId() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = false;

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedStatus);

        when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final CreateCategoryOutput output = useCase.execute(command).get();

        assertThat(output, is(notNullValue()));
        assertThat(output.id(), is(notNullValue()));

        verify(gateway, times(1)).create(argThat(aCategory -> {
            return Objects.equals(expectedName, aCategory.getName()) &&
                    Objects.equals(expectedDescription, aCategory.getDescription()) &&
                    Objects.equals(expectedStatus, aCategory.getIsActive()) &&
                    Objects.nonNull(aCategory.getId()) &&
                    Objects.nonNull(aCategory.getCreatedAt()) &&
                    Objects.nonNull(aCategory.getUpdatedAt()) &&
                    Objects.nonNull(aCategory.getDeletedAt());
        }));
    }

    @Test
    @DisplayName("Should throw DomainException when Gateway throws random exception")
    public void givenAValidCreateCommand_whenGatewayThrowsRandomException_shouldReturnAnException() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;
        final Integer expectedErrorCount = 1;

        final String expectedErrorMessage = "Gateway error";

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedStatus);

        when(gateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final Notification notification = useCase.execute(command).getLeft();

        assertThat(notification.getErrors().size(), is(expectedErrorCount));
        assertThat(notification.firstError().message(), is(expectedErrorMessage));

        verify(gateway, times(1)).create(argThat(aCategory -> {
            return Objects.equals(expectedName, aCategory.getName()) &&
                    Objects.equals(expectedDescription, aCategory.getDescription()) &&
                    Objects.equals(expectedStatus, aCategory.getIsActive()) &&
                    Objects.nonNull(aCategory.getId()) &&
                    Objects.nonNull(aCategory.getCreatedAt()) &&
                    Objects.nonNull(aCategory.getUpdatedAt()) &&
                    Objects.isNull(aCategory.getDeletedAt());
        }));
    }
}
