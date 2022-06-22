//package app.projetaria.videocatalogmanager.infrastructure.database;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import app.projetaria.videocatalogmanager.domain.category.Category;
//import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;
//
//@DataJpaTest(properties = {
//    "spring.jpa.hibernate.ddl-auto=create-drop",
//    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
//})
//public class MySQLCategoryRepositoryTests {
//
//    @Autowired
//    SpringDataCategoryRepository springDataCategoryRepository;
//
//    ICategoryRepository repository;
//
//    @BeforeEach
//    public void setup() {
//        repository = new MySQLCategoryRepository(springDataCategoryRepository);
//    }
//
//    @Test
//    @DisplayName("Should save and retreive a Category")
//    public void shouldSaveAndRetreiveCategory() {
//        final Category category =
//            new Category(UUID.randomUUID(), "Comedy", "Comedy category");
//
//        assertDoesNotThrow(() -> this.repository.create(category));
//
//        Optional<Category> optionalCategory =
//            assertDoesNotThrow(() -> this.repository.findById(category.getId()));
//
//        assertThat(optionalCategory.isEmpty(), equalTo(false));
//        assertThat(optionalCategory.get().getId(), equalTo(category.getId()));
//        assertThat(optionalCategory.get().getName(), equalTo(category.getName()));
//        assertThat(optionalCategory.get().getDescription(), equalTo(category.getDescription()));
//        assertThat(optionalCategory.get().getIsActive(), equalTo(category.getIsActive()));
//    }
//
//    @Test
//    @DisplayName("Should retreive all Categories")
//    public void shouldRetreiveAllCategory() {
//        final Category category1 =
//            new Category(UUID.randomUUID(), "Comedy", "Comedy category");
//
//        final Category category2 =
//            new Category(UUID.randomUUID(), "Action", "Action category");
//
//        assertDoesNotThrow(() -> this.repository.create(category1));
//        assertDoesNotThrow(() -> this.repository.create(category2));
//
//        List<Category> categories =
//            assertDoesNotThrow(() -> this.repository.findAll());
//
//        assertThat(categories.size(), equalTo(2));
//    }
//
//    @Test
//    @DisplayName("Should retreive a empty Category")
//    public void shouldRetreiveEmptyCategory() {
//        Optional<Category> categoryFounded =
//            assertDoesNotThrow(() -> this.repository.findById(UUID.randomUUID()));
//
//        assertThat(categoryFounded.isEmpty(), equalTo(true));
//    }
//
//    @Test
//    @DisplayName("Should update and retreive a Category")
//    public void shouldUpdateAndRetreiveCategory() {
//        final Category category =
//            new Category(UUID.randomUUID(), "Comedy", "Comedy category");
//
//        assertDoesNotThrow(() -> this.repository.create(category));
//
//        category.changeName("New Comedy");
//        category.changeDescription("New Comedy description");
//        category.deactivate();
//
//        assertDoesNotThrow(() -> this.repository.update(category));
//
//        Optional<Category> optionalCategory =
//            assertDoesNotThrow(() -> this.repository.findById(category.getId()));
//
//        assertThat(optionalCategory.isEmpty(), equalTo(false));
//        assertThat(optionalCategory.get().getId(), equalTo(category.getId()));
//        assertThat(optionalCategory.get().getName(), equalTo(category.getName()));
//        assertThat(optionalCategory.get().getDescription(), equalTo(category.getDescription()));
//        assertThat(optionalCategory.get().getIsActive(), equalTo(category.getIsActive()));
//    }
//
//    @Test
//    @DisplayName("Should remove and not retreive a Category")
//    public void shouldRemoveAndNotRetreiveCategory() {
//        final Category category =
//            new Category(UUID.randomUUID(), "Comedy", "Comedy category");
//
//        assertDoesNotThrow(() -> this.repository.create(category));
//        assertDoesNotThrow(() -> this.repository.remove(category.getId()));
//
//        Optional<Category> optionalCategory =
//            assertDoesNotThrow(() -> this.repository.findById(category.getId()));
//
//        assertThat(optionalCategory.isEmpty(), equalTo(true));
//    }
//}
