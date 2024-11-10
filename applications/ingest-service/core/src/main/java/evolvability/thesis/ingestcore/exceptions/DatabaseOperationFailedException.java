package evolvability.thesis.ingestcore.exceptions;

public class DatabaseOperationFailedException extends Exception {
    public DatabaseOperationFailedException(final String message) {
        super(message);
    }

    public DatabaseOperationFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
