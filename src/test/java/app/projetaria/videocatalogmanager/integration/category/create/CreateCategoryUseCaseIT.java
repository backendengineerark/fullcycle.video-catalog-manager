package app.projetaria.videocatalogmanager.integration.category.create;

import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryCommand;
import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryOutput;
import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryUseCase;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.validation.handler.Notification;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryEntity;
import app.projetaria.videocatalogmanager.infrastructure.category.persistence.CategoryRepository;
import app.projetaria.videocatalogmanager.integration.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @SpyBean
    private CategoryGateway gateway;

    @BeforeEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should create a category")
    public void givenAValidCreateCommand_whenCallCreateCategory_shouldReturnCategoryId() {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        assertThat(repository.count(), is(0l));

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedStatus);

        final CreateCategoryOutput output = useCase.execute(command).get();

        assertThat(repository.count(), is(1l));
        assertThat(output, is(notNullValue()));
        assertThat(output.id(), is(notNullValue()));

        CategoryEntity categoryFounded =
                repository.findById(output.id()).get();

        assertThat(categoryFounded.getName(), is(expectedName));
        assertThat(categoryFounded.getDescription(), is(expectedDescription));
        assertThat(categoryFounded.getActive(), is(expectedStatus));
        assertThat(categoryFounded.getCreatedAt(), is(notNullValue()));
        assertThat(categoryFounded.getUpdatedAt(), is(notNullValue()));
        assertThat(categoryFounded.getDeletedAt(), is(nullValue()));
    }

    @Test
    @DisplayName("Should throw DomainException when try create a category with nullable name")
    public void givenACommandWithNullableName_whenCallCreateCategory_shouldThrowDomainException() {
        final String expectedName = null;
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        assertThat(repository.count(), is(0l));

        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedStatus);

        final Notification notification = useCase.execute(command).getLeft();

        assertThat(repository.count(), is(0l));

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

        assertThat(repository.count(), is(0l));

        final Integer expectedErrorCount = 1;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedStatus);

        final Notification notification = useCase.execute(command).getLeft();

        assertThat(repository.count(), is(0l));

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

        assertThat(repository.count(), is(0l));

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedStatus);

        final CreateCategoryOutput output = useCase.execute(command).get();

        CategoryEntity categoryFounded =
                repository.findById(output.id()).get();

        assertThat(categoryFounded.getName(), is(expectedName));
        assertThat(categoryFounded.getDescription(), is(expectedDescription));
        assertThat(categoryFounded.getActive(), is(expectedStatus));
        assertThat(categoryFounded.getCreatedAt(), is(notNullValue()));
        assertThat(categoryFounded.getUpdatedAt(), is(notNullValue()));
        assertThat(categoryFounded.getDeletedAt(), is(notNullValue()));
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

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(gateway).create(any());

        final Notification notification = useCase.execute(command).getLeft();

        assertThat(notification.getErrors().size(), is(expectedErrorCount));
        assertThat(notification.firstError().message(), is(expectedErrorMessage));
    }
}
