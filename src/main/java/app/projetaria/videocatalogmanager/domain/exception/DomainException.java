package app.projetaria.videocatalogmanager.domain.exception;

import app.projetaria.videocatalogmanager.domain.validation.Error;
import lombok.Getter;

import java.util.List;

@Getter
public class DomainException extends NoStackTraceException {

    protected List<Error> errors;

    protected DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(final Error aError) {
        return new DomainException(aError.message(), List.of(aError));
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException("", errors);
    }
}
