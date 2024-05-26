package com.example.aims.view;

import com.example.aims.controller.PlaceOrderController;
import com.example.aims.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InvoiceScreenController implements Initializable {
    @FXML
    GridPane gridPane;


    @FXML
    Text nameText;

    @FXML
    Text phoneText;

    @FXML
    Text addressText;

    @FXML
    Text shippingIntructionsText;

    @FXML
    Text provinceText;

    @FXML
    Text subTotalText;

    @FXML
    Text vatText;

    @FXML
    Text shippingFeeText;

    @FXML
    Text amountText;




    @FXML
    CheckBox rushOrderCheckbox;

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
        rushOrderCheckbox.setSelected(false);
        rushOrderCheckbox.setOnAction(this::handleCheckBox);

        gridPane.setHgap(10);
        gridPane.setVgap(40);
    }

    public void initView() {
        Invoice invoice =  placeOrderController.getInvoice();

        Order order = invoice.getOrder();

        List<OrderMedia> orderMediaList = order.getLstOrderMedia();

        for(int i = 1; i <= orderMediaList.size(); i++) {
            System.out.println("order media size: " + orderMediaList.size());
            Image image = new Image(getClass().getResourceAsStream("images.jpeg"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(40);
            imageView.setFitWidth(35);

            gridPane.add(new HBox(15, imageView, new Text(orderMediaList.get(i-1).getMedia().getTitle())),0, i);
            gridPane.add(new Text(String.valueOf(orderMediaList.get(i-1).getMedia().getPrice())),1, i);
            gridPane.add(new Text(String.valueOf(orderMediaList.get(i-1).getQuantity())),2, i);
            gridPane.add(new Text(String.valueOf(orderMediaList.get(i-1).getPrice())),3, i);
        }

        DeliveryAddress deliveryAddress = order.getDeliveryAddress();

        nameText.setText(deliveryAddress.getName());
        phoneText.setText(deliveryAddress.getPhone());
        provinceText.setText(deliveryAddress.getProvince());
        addressText.setText(deliveryAddress.getAddress());
        shippingIntructionsText.setText(deliveryAddress.getInstruction());

        subTotalText.setText(String.valueOf(invoice.getSubAmount()));
        vatText.setText(String.valueOf(invoice.getVat()));
        shippingFeeText.setText(String.valueOf(invoice.getOrder().getShippingFee()));
        amountText.setText(String.valueOf(invoice.getTotalAmount()));
    }

    private void handleCheckBox(ActionEvent event) {
        // Check if the checkbox is selected
        if (rushOrderCheckbox.isSelected()) {
            try {
                placeOrderController.requestRushOrder();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }




    @FXML
    protected void Submit(ActionEvent event) throws IOException {
        placeOrderController.choicePaymentMethod();
    }
}
