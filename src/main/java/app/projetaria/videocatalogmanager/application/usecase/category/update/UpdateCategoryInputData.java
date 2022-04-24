package app.projetaria.videocatalogmanager.application.usecase.category.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryInputData {
    
    private String name;
    private String description;
    private Boolean isActive;
}
