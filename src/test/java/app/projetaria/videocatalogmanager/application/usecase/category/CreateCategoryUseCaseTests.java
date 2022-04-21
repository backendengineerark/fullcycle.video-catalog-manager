package app.projetaria.videocatalogmanager.application.usecase.category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;
import app.projetaria.videocatalogmanager.application.usecase.category.create.CreateCategoryInputData;
import app.projetaria.videocatalogmanager.application.usecase.category.create.CreateCategoryUseCase;
import app.projetaria.videocatalogmanager.domain.entity.Category;
import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;

@ExtendWith(SpringExtension.class)
public class CreateCategoryUseCaseTests {

    @InjectMocks
    private CreateCategoryUseCase useCase;

    @Mock
    private ICategoryRepository repository;

    @BeforeEach
    public void setup() {
        useCase = new CreateCategoryUseCase(repository);
    }

    @Test
    @DisplayName("Should create a category")
    public void shouldCreateCategory() {
        final String name = "Action";
        final String description = "Action category";
        final Boolean isActive = Boolean.TRUE;

        CreateCategoryInputData categoryData = 
            new CreateCategoryInputData(name, description, isActive);

        Category categoryCreated = 
            new Category(UUID.randomUUID(), name, description);
        
        when(repository.create(any(Category.class)))
            .thenReturn(categoryCreated);
        
        CategoryOutputData categoryOutput = useCase.execute(categoryData);

        verify(repository, times(1)).create(any(Category.class));

        assertThat(categoryOutput.getId(), is(notNullValue()));
        assertThat(categoryOutput.getName(), is(categoryData.getName()));
        assertThat(categoryOutput.getDescription(), is(categoryData.getDescription()));
        assertThat(categoryOutput.getIsActive(), is(categoryData.getIsActive()));
    }
    
}
