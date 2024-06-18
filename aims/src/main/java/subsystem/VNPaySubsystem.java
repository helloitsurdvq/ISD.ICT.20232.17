package subsystem;

import model.PaymentTransaction;
import subsystem.VNPay.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class VNPaySubsystem implements VNPayInterface {
    private VNPayController ctrl;
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