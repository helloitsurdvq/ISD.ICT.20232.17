package exception;

public class SendToBankException extends PaymentException {
    public SendToBankException() {
        super("VNPay requests repay transaction.");
    }
}
