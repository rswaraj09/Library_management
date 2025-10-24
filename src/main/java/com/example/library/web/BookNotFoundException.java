

public class BookNotFoundException extends RuntimeException {
    // Default constructor
    public BookNotFoundException() {
        super();
    }

    // Constructor with message
    public BookNotFoundException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public BookNotFoundException(Throwable cause) {
        super(cause);
    }
}
