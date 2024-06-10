package exception;

public class ProcessingException extends PaymentException{
    public ProcessingException() {
        super("VNPay is busy!");
    }
}
