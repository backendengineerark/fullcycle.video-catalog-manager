package app.projetaria.videocatalogmanager.api.exception.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    private Integer errorCode;
    private HttpStatus statusCode;
    private String message;
    private List<ApiValidationError> subErrors = new ArrayList<>();   
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    public void addSubErrors(ApiValidationError error) {
        this.subErrors.add(error);
    }
}
