package exception;

public class RejectedTransactionException extends PaymentException{
    public RejectedTransactionException() {
        super("Transaction has been rejected.");
    }
}
