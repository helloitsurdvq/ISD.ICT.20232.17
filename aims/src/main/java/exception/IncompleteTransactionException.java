package exception;

public class IncompleteTransactionException extends PaymentException {
    public IncompleteTransactionException() {
        super("Incomplete Transaction.");
    }
}
