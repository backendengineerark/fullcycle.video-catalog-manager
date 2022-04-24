package app.projetaria.videocatalogmanager.api.exception.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiValidationError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
