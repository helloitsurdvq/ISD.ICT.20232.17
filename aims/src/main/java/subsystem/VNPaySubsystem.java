package subsystem;

import model.PaymentTransaction;
import subsystem.VNPay.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/***
 * The {@code VNPaySubsystem} class is used to communicate with the
 * VNPay to make transaction.
 */

public class VNPaySubsystem implements VNPayInterface {
    private VNPayController ctrl;
    /**
     * Initializes a newly created {@code VNPaySubsystem} object so that it
     * represents an Interbank subsystem.
     */
    public VNPaySubsystem() {
        this.ctrl = new VNPayController();
    }

    public String generatePayUrl(int amount, String contents) {
        try {
            return ctrl.generatePayOrderUrl(amount, contents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PaymentTransaction makePaymentTransaction(Map<String, String> response) throws ParseException {
        return ctrl.makePaymentTransaction(response);
    }
}