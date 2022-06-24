package app.projetaria.videocatalogmanager.application.category.retrieve.list;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategorySearchQuery;
import app.projetaria.videocatalogmanager.domain.pagination.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {

    @InjectMocks
    private DefaultListCategoriesUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(gateway);
    }

    @Test
    @DisplayName("Should return a list of categories")
    public void givenAValidQuery_whenCallListCategory_shouldBeReturnCategories() {
        List<Category> categories = List.of(
            Category.create("Comedy", "Comedy category", true),
            Category.create("Action", "Action category", true)
        );

        final Integer expectedPage = 0;
        final Integer expectedPerPage = 10;
        final String expectedTerms = "";
        final String expectedSort = "createdAt";
        final String expectedDirection = "asc";

        final CategorySearchQuery aQuery = new CategorySearchQuery(expectedPage,
                expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final Pagination<Category> expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final Integer expectedItemsCount = 2;

        final Pagination<CategoryListOutput> expectedResults =
                expectedPagination.map(CategoryListOutput::from);

        when(gateway.findAll(eq(aQuery)))
                .thenReturn(expectedPagination);

        final Pagination<CategoryListOutput> result = useCase.execute(aQuery);

        assertThat(result.items().size(), is(expectedItemsCount));
        assertThat(result, is(expectedResults));
        assertThat(expectedPage, is(result.currentPage()));
        assertThat(expectedPerPage, is(result.perPage()));
        assertThat(categories.size(), is(result.total()));
    }

    @Test
    @DisplayName("Should return a empty list of categories")
    public void givenAValidQuery_whenCallListCategory_shouldBeReturnEmptyCategories() {
        List<Category> categories = List.<Category>of();

        final Integer expectedPage = 0;
        final Integer expectedPerPage = 10;
        final String expectedTerms = "";
        final String expectedSort = "createdAt";
        final String expectedDirection = "asc";

        final CategorySearchQuery aQuery = new CategorySearchQuery(expectedPage,
                expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final Pagination<Category> expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final Integer expectedItemsCount = 0;

        final Pagination<CategoryListOutput> expectedResults =
                expectedPagination.map(CategoryListOutput::from);

        when(gateway.findAll(eq(aQuery)))
                .thenReturn(expectedPagination);

        final Pagination<CategoryListOutput> result = useCase.execute(aQuery);

        assertThat(result.items().size(), is(expectedItemsCount));
        assertThat(result, is(expectedResults));
        assertThat(expectedPage, is(result.currentPage()));
        assertThat(expectedPerPage, is(result.perPage()));
        assertThat(categories.size(), is(result.total()));
    }

    @Test
    @DisplayName("Should throw DomainException when Gateway throws random exception")
    public void givenAValidQuery_whenGatewayThrowsRandomException_shouldReturnAnException() {
        final Integer expectedPage = 0;
        final Integer expectedPerPage = 10;
        final String expectedTerms = "";
        final String expectedSort = "createdAt";
        final String expectedDirection = "asc";
        final String expectedErrorMessage = "Gateway error";

        final CategorySearchQuery aQuery = new CategorySearchQuery(expectedPage,
                expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        when(gateway.findAll(eq(aQuery)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> useCase.execute(aQuery));

        assertThat(exception.getMessage(), is(expectedErrorMessage));
    }
}
