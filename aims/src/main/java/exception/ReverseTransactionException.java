package exception;

public class ReverseTransactionException extends PaymentException {
    public ReverseTransactionException() {
        super("Reverse Transaction.");
    }
}
