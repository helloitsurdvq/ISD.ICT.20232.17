package com.example.aims.controller;

import com.example.aims.entity.*;
import com.example.aims.subsystem.PaymentException;
import com.example.aims.subsystem.PaymentInterface;
import com.example.aims.subsystem.vnpay.VNPaySubsystem;
import com.example.aims.view.CartScreenController;
import com.example.aims.view.InvoiceScreenController;
import com.example.aims.view.PaymentMethodScreenController;
import com.example.aims.view.ShippingScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlaceOrderController {
    private Order order;
    private Invoice invoice;
    private Scene currentScene;
    private PaymentInterface paymentInterface;
    private RushOrderController rushOrderController = new RushOrderController();
    public void placeOrder(Cart cart, Scene currentScene) {
        this.order = new Order();
        if (cart.checkAvailabilityOfProduct()) {
            this.order = Order.createOrder(cart);
            try {
                this.currentScene = currentScene;
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/aims/view/ShippingScreen.fxml"));
                Parent root = loader.load();
                ShippingScreenController controller = loader.getController();
                controller.setPlaceOrderController(this);
                currentScene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }

    public void requestInvoice() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aims/view/InvoiceScreen.fxml"));
            invoice = Invoice.createInvoice(this.order);
            Parent root = loader.load();
            InvoiceScreenController controller = loader.getController();
            controller.setPlaceOrderController(this);
            controller.initView();
            this.currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    public void requestRushOrder() throws IOException {
        System.out.println(invoice);
        rushOrderController.requestToRushOrder(currentScene, invoice, this);
    }

    public void choicePaymentMethod() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aims/view/PaymentMethodScreen.fxml"));

        invoice = Invoice.createInvoice(this.order);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PaymentMethodScreenController controller = loader.getController();
        controller.setPlaceOrderController(this);
        this.currentScene.setRoot(root);
    }

    public void requestPayment() {
        paymentInterface = new VNPaySubsystem();
        try {
            paymentInterface.payOrder(invoice, "Thanh toán hóa đơn", currentScene);
        } catch (PaymentException e) {
            throw new RuntimeException(e);
        }

    }

    public void backToCartScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aims/view/cart-view.fxml"));
        Parent root = loader.load();
        CartScreenController controller = loader.getController();
        controller.initView(Cart.createNewCart());
        this.currentScene.setRoot(root);
    }

    public boolean processDeliveryInfo(HashMap<String, String> info) {
        if (validateDeliveryInfo(info)) {
            DeliveryAddress deliveryAddress = new DeliveryAddress(info.get("name"), info.get("phone"),
                    info.get("province"), info.get("address"), info.get("instruction"));
            order.setDeliveryAddress(deliveryAddress);
            order.calculateShippingFee();
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Nhập thiếu thông tin hoặc sai!");
            alert.setContentText("Vui lòng sửa lại.");
            alert.showAndWait();
        }
        return false;

    }

    public Boolean validateDeliveryInfo(HashMap<String, String> info) {
        Boolean isValid = true;
        for (Map.Entry<String, String> entry : info.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            switch (fieldName) {
                case "phone":
                    if (!validatePhoneNumber(value)) {
                        System.out.println("Invalid phone number");
                        isValid = false;
                    }
                    break;
                case "name":
                    if (!validateName(value)) {
                        System.out.println("Invalid name");
                        isValid = false;
                    }
                    break;
                case "address":
                    if (!validateAddress(value)) {
                        System.out.println("Invalid address");
                        isValid = false;
                    }
                    break;
            }
        }
        return isValid;
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 10 || phoneNumber.charAt(0) != '0') {
            return false;
        }
        try {
            Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        if (!name.matches("^[a-zA-Z ]*$")) {
            return false;
        }
        return true;
    }

    public boolean validateAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }

    public Order getOrder() {
        return order;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}