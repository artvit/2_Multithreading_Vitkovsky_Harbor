package by.epam.port.exception;

public class JsonParserException extends Exception {
    public JsonParserException() {
    }

    public JsonParserException(String message) {
        super(message);
    }

    public JsonParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonParserException(Throwable cause) {
        super(cause);
    }
}
