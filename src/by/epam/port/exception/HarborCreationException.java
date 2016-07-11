package by.epam.port.exception;

public class HarborCreationException extends Exception {
    public HarborCreationException() {
    }

    public HarborCreationException(String message) {
        super(message);
    }

    public HarborCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public HarborCreationException(Throwable cause) {
        super(cause);
    }
}
