package com.example.aims.view;

import com.example.aims.controller.PlaceOrderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ShippingScreenController implements Initializable {

    @FXML
    TextField nameText;

    @FXML
    TextField phoneText;

    @FXML
    TextField addressText;

    @FXML
    TextField shippingInstructionsText;

    @FXML
    ChoiceBox provinceChoiceBox;


    @FXML
    Button submitBtn;

    @FXML
    Button backBtn;

    private PlaceOrderController placeOrderController;


    public void setPlaceOrderController(PlaceOrderController placeOrderController) {
        this.placeOrderController = placeOrderController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        provinceChoiceBox.setValue("Hà Nội");
    }

    @FXML
    protected void requestInvoice(ActionEvent event) throws IOException {


        HashMap<String, String> info = new HashMap<>();
        info.put("name", nameText.getText());
        info.put("phone", phoneText.getText());
        info.put("province", provinceChoiceBox.getValue().toString());
        info.put("address", addressText.getText());
        info.put("instruction", addressText.getText());
        if(placeOrderController.processDeliveryInfo(info)) {
            placeOrderController.requestInvoice();
        }
    }


    @FXML
    protected void backToCartScreen(ActionEvent event) throws IOException {
        placeOrderController.backToCartScreen();
    }



}
