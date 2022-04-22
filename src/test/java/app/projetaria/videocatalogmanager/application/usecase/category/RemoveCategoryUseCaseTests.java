package app.projetaria.videocatalogmanager.application.usecase.category;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import app.projetaria.videocatalogmanager.application.exception.NotFoundException;
import app.projetaria.videocatalogmanager.application.usecase.category.remove.RemoveCategoryUseCase;
import app.projetaria.videocatalogmanager.domain.entity.Category;
import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;

@ExtendWith(SpringExtension.class)
public class RemoveCategoryUseCaseTests {

    @InjectMocks
    private RemoveCategoryUseCase useCase;

    @Mock
    private ICategoryRepository repository;

    @BeforeEach
    public void setup() {
        useCase = new RemoveCategoryUseCase(repository);
    }
    
    @Test
    @DisplayName("Should remove a category by id")
    public void shouldRemoveCategoryById() {
        final UUID id = UUID.randomUUID();
        final String name = "Comedy";
        final String description = "Comedy category";

        final Category category = new Category(id, name, description);

        when(repository.findById(any(UUID.class)))
            .thenReturn(Optional.of(category));

        doNothing().when(repository).remove(any(UUID.class));

        assertDoesNotThrow(() -> useCase.execute(id));

        verify(repository, times(1)).findById(any(UUID.class));
        verify(repository, times(1)).remove(any(UUID.class));
    }

    @Test
    @DisplayName("Should throw DomainException when try remove a nonexistent category")
    public void shouldThrowDomainExceptionWhenTryRemoveNonexistentCategory() {
        final UUID id = UUID.randomUUID();
        final String exceptionMessage = String.format("Category %s not found to remove", id);

        when(repository.findById(any(UUID.class)))
            .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
            NotFoundException.class, 
            () -> useCase.execute(id),
            "Expect remove category by id do throw, but it didn't"
        );

        verify(repository, times(1)).findById(any(UUID.class));
        verify(repository, times(0)).remove(any(UUID.class));

        assertThat(exception.getMessage(), is(exceptionMessage));
    }
}
