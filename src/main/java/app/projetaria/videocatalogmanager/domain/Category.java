package app.projetaria.videocatalogmanager.domain;

import java.util.Optional;
import java.util.UUID;

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

    public Category(String name, String description) {
        this.changeName(name);
        this.changeDescription(description);
        this.activate();
    }
    
    public void changeName(final String name) {
        Optional.ofNullable(name)
            .map(inputName -> {
                if (inputName.length() < 3) {
                    throw new RuntimeException("Name must be at last 3 characteres");
                }
                this.name = inputName;
                return this.name;
            })
            .orElseThrow(() -> {
                throw new RuntimeException("Name cannot be null");
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
