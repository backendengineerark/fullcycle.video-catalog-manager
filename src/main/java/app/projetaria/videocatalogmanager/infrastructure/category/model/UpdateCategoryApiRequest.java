package app.projetaria.videocatalogmanager.infrastructure.category.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateCategoryApiRequest(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("is_active") Boolean active
) {
}
