package com.example.aims.controller;

import com.example.aims.entity.Invoice;
import com.example.aims.entity.Media;
import com.example.aims.view.RushOrderScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.HashMap;

public class RushOrderController {
    private PlaceOrderController placeOrderController;
    private Scene currentScene;
    private Invoice invoice;
    public void requestToRushOrder(Scene currentScene, Invoice invoice, PlaceOrderController placeOrderController) throws IOException {
        this.currentScene = currentScene;
        this.invoice = invoice;
        this.placeOrderController = placeOrderController;

        if(invoice.getOrder().checkRushOrderAddress()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aims/view/RushOrderScreen.fxml"));
            Parent root = loader.load();
            RushOrderScreenController controller = loader.getController();
            controller.setRushOrderController(this);
            controller.initView();

            currentScene.setRoot(root);
        }else {
            System.out.println("not support rush order");
        }

    }

    public boolean validateMediaRushOrder(Media media) {
        if(media.getMediaShippingMethod().getNameMethod().equals("Rush Order")) {
            return true;
        }else {
            return false;
        }
    }

    public boolean checkRushOrderAddress(String province) {
        if(province == null || province.isEmpty()) {
            return false;
        }
        if (province.equals("Hà Nội")) {
            return true;
        }
        return false;
    }

    public void confirmRushOrder() {

    }

    public Boolean processRushDeliveryInfo(HashMap<String, String> rushDeliveryInfo) {
        String instructionDelivery = rushDeliveryInfo.get("InstructionDelivery");
        if(!this.validateDeliveryInfo( instructionDelivery)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Instruction không được để trống");
            alert.setContentText("Vui lòng sửa lại.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void requestChoicePaymentMethod() {
        placeOrderController.choicePaymentMethod();
    }

    public boolean validateDeliveryInfo(String instructionDelivery) {
        if (instructionDelivery == null)
            return false;
        if (instructionDelivery.trim().length() == 0)
            return false;
        return true;
    }

    public RushOrderController() {
    }

    public Invoice getInvoice() {
        return invoice;
    }
}