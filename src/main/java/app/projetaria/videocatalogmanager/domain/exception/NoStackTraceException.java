package app.projetaria.videocatalogmanager.domain.exception;

public class NoStackTraceException extends RuntimeException {

    NoStackTraceException(final String message) {
        this(message, null);
    }

    NoStackTraceException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}
