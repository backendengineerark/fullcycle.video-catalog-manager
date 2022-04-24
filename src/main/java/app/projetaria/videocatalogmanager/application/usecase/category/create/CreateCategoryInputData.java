package app.projetaria.videocatalogmanager.application.usecase.category.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryInputData {
    
    private String name;
    private String description;
    private Boolean isActive;
}
