package app.projetaria.videocatalogmanager.application.usecase.category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import app.projetaria.videocatalogmanager.application.exception.ApplicationException;
import app.projetaria.videocatalogmanager.application.exception.NotFoundException;
import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;
import app.projetaria.videocatalogmanager.application.usecase.category.get.FindByIdCategoryUseCase;
import app.projetaria.videocatalogmanager.domain.entity.Category;
import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;

@ExtendWith(SpringExtension.class)
public class FindByIdCategoryUseCaseTests {
    
    @InjectMocks
    private FindByIdCategoryUseCase useCase;

    @Mock
    private ICategoryRepository repository;

    @BeforeEach
    public void setup() {
        useCase = new FindByIdCategoryUseCase(repository);
    }

    @Test
    @DisplayName("Should find a category by id")
    public void shouldFindCategoryById() {
        final UUID id = UUID.randomUUID();
        final String name = "Comedy";
        final String description = "Comedy category";

        final Category category = new Category(id, name, description);

        when(repository.findById(any(UUID.class)))
            .thenReturn(Optional.of(category));

        final CategoryOutputData categoryData = useCase.execute(id);

        verify(repository, times(1)).findById(any(UUID.class));

        assertThat(categoryData, is(notNullValue()));
        assertThat(categoryData.getId(), is(category.getId()));
        assertThat(categoryData.getName(), is(category.getName()));
        assertThat(categoryData.getDescription(), is(category.getDescription()));
        assertThat(categoryData.getIsActive(), is(category.getIsActive()));
    }

    @Test
    @DisplayName("Should throw DomainException when category not exists by id")
    public void shouldThrowDomainExceptionWhenCategoryNotExistsById() {
        final UUID id = UUID.randomUUID();
        final String exceptionMessage = String.format("Category %s not found", id);
        
        when(repository.findById(any(UUID.class)))
            .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
            NotFoundException.class, 
            () -> useCase.execute(id),
            "Expect get category by id do throw, but it didn't"
        );

        verify(repository, times(1)).findById(any(UUID.class));

        assertThat(exception.getMessage(), is(exceptionMessage));
    }
}
