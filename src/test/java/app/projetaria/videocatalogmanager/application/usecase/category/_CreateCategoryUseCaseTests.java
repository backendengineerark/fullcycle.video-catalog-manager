//package app.projetaria.videocatalogmanager.application.usecase.category;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import app.projetaria.videocatalogmanager.application.usecase.category.create.CreateCategoryUseCase;
//
//@ExtendWith(SpringExtension.class)
//public class CreateCategoryUseCaseTests {
//
//    @InjectMocks
//    private CreateCategoryUseCase useCase;
//
//    @Mock
//    private ICategoryRepository repository;
//
//    @BeforeEach
//    public void setup() {
//        useCase = new CreateCategoryUseCase(repository);
//    }
//
//    @Test
//    @DisplayName("Should create a category")
//    public void shouldCreateCategory() {
//        final String name = "Action";
//        final String description = "Action category";
//        final Boolean isActive = Boolean.TRUE;
//
//        CreateCategoryInputData categoryData =
//            new CreateCategoryInputData(name, description, isActive);
//
//        Category categoryCreated =
//            new Category(UUID.randomUUID(), name, description);
//
//        when(repository.create(any(Category.class)))
//            .thenReturn(categoryCreated);
//
//        CategoryOutputData categoryOutput = useCase.execute(categoryData);
//
//        verify(repository, times(1)).create(any(Category.class));
//
//        assertThat(categoryOutput.getId(), is(notNullValue()));
//        assertThat(categoryOutput.getName(), is(categoryData.getName()));
//        assertThat(categoryOutput.getDescription(), is(categoryData.getDescription()));
//        assertThat(categoryOutput.getIsActive(), is(categoryData.getIsActive()));
//    }
//
//}
