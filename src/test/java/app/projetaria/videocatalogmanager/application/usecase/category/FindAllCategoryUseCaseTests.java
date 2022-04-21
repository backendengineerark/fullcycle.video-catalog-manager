package app.projetaria.videocatalogmanager.application.usecase.category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;
import app.projetaria.videocatalogmanager.application.usecase.category.findall.FindAllCategoryUseCase;
import app.projetaria.videocatalogmanager.domain.entity.Category;
import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;

@ExtendWith(SpringExtension.class)
public class FindAllCategoryUseCaseTests {
    
    @InjectMocks
    private FindAllCategoryUseCase useCase;

    @Mock
    private ICategoryRepository repository;

    @BeforeEach
    public void setup() {
        useCase = new FindAllCategoryUseCase(repository);
    }

    @Test
    @DisplayName("Should find all categories")
    public void shouldFindAllCategories() {
        Category category1 = new Category(UUID.randomUUID(), "Action", "Action category");
        Category category2 = new Category(UUID.randomUUID(), "Comedy", "Comedy category");
        List<Category> categories = Arrays.asList(category1, category2);

        when(repository.findAll())
            .thenReturn(categories);

        List<CategoryOutputData> categoriesData = useCase.execute();

        verify(repository, times(1)).findAll();
        
        assertThat(categoriesData, is(notNullValue()));
        assertThat(categoriesData, hasSize(2));
    }
}
