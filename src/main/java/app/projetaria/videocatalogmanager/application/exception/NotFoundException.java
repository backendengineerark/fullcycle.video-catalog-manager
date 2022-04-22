package app.projetaria.videocatalogmanager.application.exception;

public class NotFoundException extends ApplicationException {
    
    public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }
}
