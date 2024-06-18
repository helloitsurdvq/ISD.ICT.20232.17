package subsystem;

import exception.PaymentException;
import exception.UnrecognizedException;
import model.PaymentTransaction;

import java.text.ParseException;
import java.util.Map;

public interface VNPayInterface {
    public abstract String generatePayUrl(int amount, String contents)
            throws PaymentException, UnrecognizedException;

    public PaymentTransaction makePaymentTransaction(Map<String, String> response) throws ParseException;
}