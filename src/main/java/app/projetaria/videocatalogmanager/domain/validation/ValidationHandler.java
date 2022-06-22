package app.projetaria.videocatalogmanager.domain.validation;

import java.util.List;
import java.util.Objects;

public interface ValidationHandler {

    List<Error> getErrors();

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler aHandler);

    ValidationHandler validate(Validation aValidation);

    default Boolean hasErrors() {
        return ! Objects.isNull(getErrors()) && ! getErrors().isEmpty();
    }

    default Error firstError() {
        return hasErrors() ? getErrors().get(0) : null;
    }

    public interface Validation {
        void validate();
    }
}
