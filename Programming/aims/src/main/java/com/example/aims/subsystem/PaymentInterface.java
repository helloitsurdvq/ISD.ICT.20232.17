package com.example.aims.subsystem;

import com.example.aims.entity.Invoice;
import com.example.aims.entity.Order;
import com.example.aims.entity.PaymentTransaction;
import javafx.scene.Scene;

public interface PaymentInterface {
    public abstract PaymentTransaction payOrder(Invoice invoice, String contents, Scene currentScene)
        throws PaymentException;
}
