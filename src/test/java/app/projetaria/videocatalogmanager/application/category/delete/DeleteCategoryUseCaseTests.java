package app.projetaria.videocatalogmanager.application.category.delete;

import app.projetaria.videocatalogmanager.application.category.create.DefaultCreateCategoryUseCase;
import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTests {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    void cleanUp() {
        reset(gateway);
    }

    @Test
    @DisplayName("Should delete a category")
    public void givenAValidDeleteCommand_whenCallDeleteCategory_shouldBeOK() {
        final Category category = Category.create("Comedy", "Comedy category", true);
        final CategoryId expectedId = category.getId();

        doNothing().when(gateway).deleteById(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        verify(gateway, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Should delete a category even if invalid ID")
    public void givenACommandWithInvalidID_whenCallDeleteCategory_shouldBeOK() {
        final CategoryId expectedId = CategoryId.from("123");

        doNothing().when(gateway).deleteById(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        verify(gateway, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Should throw DomainException when Gateway throws random exception")
    public void givenAValidDeleteCommand_whenGatewayThrowsRandomException_shouldReturnAnException() {
        final Category category = Category.create("Comedy", "Comedy category", true);
        final CategoryId expectedId = category.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(gateway).deleteById(eq(expectedId));

        assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));
        verify(gateway, times(1)).deleteById(any());
    }
}
