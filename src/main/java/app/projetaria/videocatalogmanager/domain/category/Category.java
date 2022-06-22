package app.projetaria.videocatalogmanager.domain.category;

import java.time.Instant;
import java.util.Objects;

import app.projetaria.videocatalogmanager.domain.AggregateRoot;
import app.projetaria.videocatalogmanager.domain.validation.ValidationHandler;
import lombok.Getter;

@Getter
public class Category extends AggregateRoot<CategoryId> implements Cloneable {

    private String name;
    private String description;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(final CategoryId anId, final String aName, final String aDescription,
                     final Boolean isActive, final  Instant aCreatedAt,
                     final Instant anUpdatedAt, final Instant aDeletedAt) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.isActive = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = anUpdatedAt;
        this.deletedAt = aDeletedAt;
    }

    public static Category create(
            final String aName, final String aDescription, final Boolean isActive) {
        Instant now = Instant.now();
        Instant deletedAt = isActive ? null : now;
        return new Category(CategoryId.unique(), aName, aDescription, isActive, now, now, deletedAt);
    }

    public Category update(
            final String aName, final String aDescription, final Boolean isActive) {
        if (isActive) {
            this.activate();
        } else {
            this.deactivate();
        }
        this.name = aName;
        this.description = aDescription;
        updatedAt = Instant.now();
        return this;
    }

    public Category activate() {
        if (Objects.isNull(this.getDeletedAt())) {
            return this;
        }
        this.isActive = Boolean.TRUE;
        this.updatedAt = Instant.now();
        this.deletedAt = null;

        return this;
    }

    public Category deactivate() {
        if (! Objects.isNull(this.getDeletedAt())) {
            return this;
        }
        this.isActive = Boolean.FALSE;
        this.updatedAt = Instant.now();
        this.deletedAt = Instant.now();

        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
