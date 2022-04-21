package app.projetaria.videocatalogmanager.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryTests {
    
    @Test
    @DisplayName("Should create a category")
    public void shouldCreateCategory() {
        final String name = "Comedy";
        final String description = "Comedy category";

        final Category entity = new Category(name, description);

        assertThat(entity.getName(), is(name));
        assertThat(entity.getDescription(), is(description));
        assertThat(entity.getIsActive(), is(Boolean.TRUE));
    }

    @Test
    @DisplayName("Should deactivate a category")
    public void shouldDeactivateCategory() {
        final String name = "Comedy";
        final String description = "Comedy category";

        final Category entity = new Category(name, description);
        entity.deactivate();

        assertThat(entity.getName(), is(name));
        assertThat(entity.getDescription(), is(description));
        assertThat(entity.getIsActive(), is(Boolean.FALSE));
    }

    @Test
    @DisplayName("Should activate a category from inactive")
    public void shouldActivateCategoryFromDeactivate() {
        final String name = "Comedy";
        final String description = "Comedy category";

        final Category entity = new Category(name, description);
        entity.deactivate();
        entity.activate();

        assertThat(entity.getName(), is(name));
        assertThat(entity.getDescription(), is(description));
        assertThat(entity.getIsActive(), is(Boolean.TRUE));
    }
}
