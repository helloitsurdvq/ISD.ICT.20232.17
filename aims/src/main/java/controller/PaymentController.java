package controller;

import exception.*;
import model.cart.Cart;
import subsystem.VNPaySubsystem;
import subsystem.VNPayInterface;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Map;

/**
 * This {@code PaymentController} class control the flow of the payment process
 * in our AIMS Software.
 */
public class PaymentController extends BaseController {
    /**
     * Represent the Interbank subsystem
     */
    private VNPayInterface VNPayService;
    public Map<String, String> makePayment(Map<String, String> res, int orderId) {
        Map<String, String> result = new Hashtable<String, String>();

        try {
            this.VNPayService = new VNPaySubsystem();
            var trans = VNPayService.makePaymentTransaction(res);
            trans.save(orderId);
            result.put("RESULT", "PAYMENT SUCCESSFUL!");
            result.put("MESSAGE", "You have succesffully paid the order!");
        } catch (PaymentException | UnrecognizedException | SQLException ex) {
            result.put("MESSAGE", ex.getMessage());
            result.put("RESULT", "PAYMENT FAILED!");

        } catch (ParseException ex) {
            result.put("MESSAGE", ex.getMessage());
            result.put("RESULT", "PAYMENT FAILED!");
        }
        return result;
    }

    /**
     * Generate VNPay payment URL
     */
    public String getUrlPay(int amount, String content){
        VNPayService = new VNPaySubsystem();
        var url = VNPayService.generatePayUrl(amount, content);
        return url;
    }

    public void emptyCart() {
        Cart.getCart().emptyCart();
    }
}
