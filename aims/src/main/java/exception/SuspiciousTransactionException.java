package exception;

public class SuspiciousTransactionException extends PaymentException{
    public SuspiciousTransactionException(){
        super("Suspicious Transaction Identified!");
    }
}
