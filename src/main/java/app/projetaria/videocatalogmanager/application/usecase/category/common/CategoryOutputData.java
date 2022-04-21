package app.projetaria.videocatalogmanager.application.usecase.category.common;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryOutputData {

    private UUID id;
    private String name;
    private String description;
    private Boolean isActive;
    
}
