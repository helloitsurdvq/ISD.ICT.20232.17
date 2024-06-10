package exception;

public class UnrecognizedException extends RuntimeException {
    public UnrecognizedException() {
        super("Something went wrong!");
    }
}