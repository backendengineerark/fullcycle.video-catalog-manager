package app.projetaria.videocatalogmanager.domain.exception;

public class DomainException extends RuntimeException {

    public DomainException() {
        super();
    }

    public DomainException(final String message) {
        super(message);
    }
}
