package exception;

public class FailedTransactionException extends PaymentException{
    public FailedTransactionException() {
        super("Failed Transaction.");
    }
}
