package com.example.aims.subsystem.vnpay;

import com.example.aims.controller.Test;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

public class VNPayBoundary implements Initializable {
    @FXML
    private WebView webView;
    private WebEngine engine;
    private VNPaySubsystemController vnPaySubsystemController;
    public void setVnPaySubsystemController(VNPaySubsystemController vnPaySubsystemController) {
        this.vnPaySubsystemController = vnPaySubsystemController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webView.getEngine();
        engine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("URL changed: " + newValue);
                // You can perform actions based on the URL change here

                if(newValue.contains("http://localhost:8080/vnpay_jsp/vnpay_return.jsp?")) {
                    vnPaySubsystemController.getPaymentTransaction(newValue);
                }
            }
            // http://localhost:8080/vnpay_jsp/vnpay_return.jsp?vnp_Amount=18000000&vnp_BankCode=NCB&vnp_BankTranNo=VNP14198263
            // &vnp_CardType=ATM&vnp_OrderInfo=Thanh+toan+don+hang%3A06236597&vnp_PayDate=20231124213746&vnp_ResponseCode=00&vnp_TmnCode=5QT3R157
            // &vnp_TransactionNo=14198263&vnp_TransactionStatus=00&vnp_TxnRef=06236597
            // &vnp_SecureHash=2d2d33d975667b584a4a8ad635c43a3410c54e439f4e332031523a929d0d082113b3da217ba20741a9b70160369adc7360464f92e3226210b2414d7e2e012f81
        });

    }

    public void doPaymentTransaction(String query) {
        engine.load(query);
    }

    private String getResponse() {
        return  null;
    }
}
