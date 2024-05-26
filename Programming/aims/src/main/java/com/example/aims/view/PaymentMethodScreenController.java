package com.example.aims.view;

import com.example.aims.controller.PlaceOrderController;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentMethodScreenController implements Initializable {
    @FXML
    RadioButton checkBox;

    @FXML
    Button submitBtn;


    private PlaceOrderController placeOrderController;

    public void setPlaceOrderController(PlaceOrderController placeOrderController) {
        this.placeOrderController = placeOrderController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitBtn.setDisable(true);
        ChangeListener<Boolean> changeListener = (observable, oldValue, newValue) -> {
            if (checkBox.isSelected()) {
                submitBtn.setDisable(false);
            } else {
                submitBtn.setDisable(true);
            }
        };

        // Đăng ký ChangeListener với selectedProperty của CheckBox
        checkBox.selectedProperty().addListener(changeListener);
    }

    @FXML
    protected void Submit(ActionEvent event) throws IOException {
        placeOrderController.requestPayment();
    }
}
