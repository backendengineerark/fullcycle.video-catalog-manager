package app.projetaria.videocatalogmanager.domain.category;

import app.projetaria.videocatalogmanager.domain.Identifier;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class CategoryId extends Identifier {

    private final String value;

    private CategoryId(final String value) {
        Objects.requireNonNull(value, "ID value must not be null");
        this.value = value;
    }

    public static CategoryId unique() {
        return CategoryId.from(UUID.randomUUID());
    }

    public static CategoryId from(final String anId) {
        return new CategoryId(anId);
    }

    public static CategoryId from(final UUID anId) {
        return new CategoryId(anId.toString().toLowerCase());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryId that = (CategoryId) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
