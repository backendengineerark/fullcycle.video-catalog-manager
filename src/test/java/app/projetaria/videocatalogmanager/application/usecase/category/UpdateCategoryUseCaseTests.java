//package app.projetaria.videocatalogmanager.application.usecase.category;
//
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import app.projetaria.videocatalogmanager.application.exception.NotFoundException;
//import app.projetaria.videocatalogmanager.application.usecase.category.update.UpdateCategoryInputData;
//import app.projetaria.videocatalogmanager.application.usecase.category.update.UpdateCategoryUseCase;
//import app.projetaria.videocatalogmanager.domain.category.Category;
//import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;
//
//@ExtendWith(SpringExtension.class)
//public class UpdateCategoryUseCaseTests {
//
//    @InjectMocks
//    private UpdateCategoryUseCase useCase;
//
//    @Mock
//    private ICategoryRepository repository;
//
//    @BeforeEach
//    private void setup() {
//        useCase = new UpdateCategoryUseCase(repository);
//    }
//
//    @Test
//    @DisplayName("Should update a category by id")
//    public void shouldUpdateCategorybyId() {
//        final UUID id = UUID.randomUUID();
//        final String newName = "Action Fun";
//        final String newDescription = "Action Fun Category";
//        final Boolean newIsActive = Boolean.FALSE;
//
//        final UpdateCategoryInputData data =
//            new UpdateCategoryInputData(newName, newDescription, newIsActive);
//
//        final Category category = new Category(id, "Action", "Action Category");
//
//        when(repository.findById(any(UUID.class)))
//            .thenReturn(Optional.of(category));
//
//        doNothing().when(repository).update(any(Category.class));
//
//        assertDoesNotThrow(() -> useCase.execute(id, data));
//
//        verify(repository, times(1)).findById(any(UUID.class));
//        verify(repository, times(1)).update(any(Category.class));
//
//        assertThat(category.getId(), is(notNullValue()));
//        assertThat(category.getName(), is(data.getName()));
//        assertThat(category.getDescription(), is(data.getDescription()));
//        assertThat(category.getIsActive(), is(data.getIsActive()));
//    }
//
//    @Test
//    @DisplayName("Should throw DomainException when try remove a nonexistent category")
//    public void shouldThrowDomainExceptionWhenTryRemoveNonexistentCategory() {
//        final UUID id = UUID.randomUUID();
//        final String newName = "Action Fun";
//        final String newDescription = "Action Fun Category";
//        final Boolean newIsActive = Boolean.FALSE;
//
//        final UpdateCategoryInputData data =
//            new UpdateCategoryInputData(newName, newDescription, newIsActive);
//
//        final String exceptionMessage =
//            String.format("Category %s not found to update", id);
//
//        when(repository.findById(any(UUID.class)))
//            .thenReturn(Optional.empty());
//
//        NotFoundException exception = assertThrows(
//            NotFoundException.class,
//            () -> useCase.execute(id, data),
//            "Expect update category by id do throw, but it didn't"
//        );
//
//        verify(repository, times(1)).findById(any(UUID.class));
//        verify(repository, times(0)).remove(any(UUID.class));
//
//        assertThat(exception.getMessage(), is(exceptionMessage));
//    }
//}
