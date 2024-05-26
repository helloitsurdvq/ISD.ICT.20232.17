package com.example.aims.subsystem.vnpay;

import com.example.aims.entity.Invoice;
import com.example.aims.entity.Order;
import com.example.aims.entity.PaymentTransaction;
import com.example.aims.subsystem.PaymentException;
import com.example.aims.subsystem.PaymentInterface;
import com.example.aims.subsystem.vnpay.VNPaySubsystemController;
import javafx.scene.Scene;

public class VNPaySubsystem implements PaymentInterface {
    private VNPaySubsystemController vnPaySubsystemController = new VNPaySubsystemController();
    @Override
    public PaymentTransaction payOrder(Invoice invoice, String contents, Scene currentScene) throws PaymentException {
        vnPaySubsystemController.payOrder(invoice, contents, currentScene);
        return null;
    }
}