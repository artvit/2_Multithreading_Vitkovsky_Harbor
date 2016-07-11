package by.epam.port.exception;

public class ShipCapacityException extends Exception {
    public ShipCapacityException() {
    }

    public ShipCapacityException(String message) {
        super(message);
    }

    public ShipCapacityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShipCapacityException(Throwable cause) {
        super(cause);
    }
}
