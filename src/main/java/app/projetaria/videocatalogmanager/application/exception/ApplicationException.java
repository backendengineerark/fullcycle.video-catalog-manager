package app.projetaria.videocatalogmanager.application.exception;

public class ApplicationException extends RuntimeException {
    
    public ApplicationException() {
        super();
    }

    public ApplicationException(final String message) {
        super(message);
    }
}
