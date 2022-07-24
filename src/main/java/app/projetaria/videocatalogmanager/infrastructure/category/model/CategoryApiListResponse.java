package app.projetaria.videocatalogmanager.infrastructure.category.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record CategoryApiListResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("is_active") Boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("deleted_at") Instant deletedAt
    ) {
}
