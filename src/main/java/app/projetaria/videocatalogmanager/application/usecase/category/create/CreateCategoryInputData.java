package app.projetaria.videocatalogmanager.application.usecase.category.create;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCategoryInputData {
    
    private String name;
    private String description;
    private Boolean isActive;
}
