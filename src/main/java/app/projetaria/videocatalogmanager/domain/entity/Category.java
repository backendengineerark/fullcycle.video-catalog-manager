package app.projetaria.videocatalogmanager.domain.entity;

import java.util.Optional;
import java.util.UUID;

import app.projetaria.videocatalogmanager.domain.exception.DomainException;
import lombok.Getter;

@Getter
public class Category {
    
    private UUID id;
    private String name;
    private String description;
    private Boolean isActive;

    public Category(final UUID id, final String name, final String description) {
        this.id = id;
        this.changeName(name);
        this.changeDescription(description);
        this.activate();
    }

    public Category(final String name, final String description) {
        this.changeName(name);
        this.changeDescription(description);
        this.activate();
        this.id = UUID.randomUUID();
    }
    
    public void changeName(final String name) {
        Optional.ofNullable(name)
            .map(inputName -> {
                if (inputName.length() < 3) {
                    throw new DomainException("Name must be at last 3 characters");
                }
                this.name = inputName;
                return this.name;
            })
            .orElseThrow(() -> {
                throw new DomainException("Name cannot be null");
            });
    }

    public void changeDescription(final String description) {
        this.description = description;
    }

    public void activate() {
        this.isActive = Boolean.TRUE;
    }

    public void deactivate() {
        this.isActive = Boolean.FALSE;
    }
    
}
