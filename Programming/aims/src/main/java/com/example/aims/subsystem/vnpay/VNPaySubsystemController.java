package com.example.aims.subsystem.vnpay;

import com.example.aims.entity.Invoice;
import com.example.aims.entity.PaymentTransaction;
import com.example.aims.view.ResultScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class VNPaySubsystemController {
    private PaymentTransaction paymentTransaction;
    private Invoice invoice;
    private Scene currentScene;
    public void payOrder(Invoice invoice, String contents, Scene currentScene) {
        this.invoice = invoice;
        this.currentScene = currentScene;
        try {
            String paymentUrl = Request.buildPayOrderQueryUrl(invoice.getTotalAmount(), "VND", contents, "vn");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aims/view/PaymentScreen.fxml"));
            Parent root = loader.load();
            VNPayBoundary controller = loader.getController();
            controller.setVnPaySubsystemController(this);
            controller.doPaymentTransaction(paymentUrl);
            this.currentScene.setRoot(root);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void getPaymentTransaction(String response) {
        paymentTransaction = Response.getPaymentTransaction(response);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/aims/view/ResultScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ResultScreenController controller = loader.getController();
        controller.setPaymentTransaction(paymentTransaction);
        controller.initView();
        currentScene.setRoot(root);
    }
}