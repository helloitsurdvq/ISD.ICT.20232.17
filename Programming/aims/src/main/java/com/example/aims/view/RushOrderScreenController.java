package com.example.aims.view;

import com.example.aims.controller.PlaceOrderController;
import com.example.aims.controller.RushOrderController;
import com.example.aims.entity.DeliveryAddress;
import com.example.aims.entity.Invoice;
import com.example.aims.entity.Order;
import com.example.aims.entity.OrderMedia;
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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class RushOrderScreenController implements Initializable {

    @FXML
    GridPane gridPane;

    @FXML
    Text nameText;

    @FXML
    Text phoneText;

    @FXML
    Text addressText;

    @FXML
    TextField shippingInstructionsText;

    @FXML
    Text provinceText;

    @FXML
    ComboBox<String> timeComboBox;



    @FXML
    Text subTotalText;

    @FXML
    Text vatText;

    @FXML
    Text shippingFeeText;

    @FXML
    Text amountText;


    @FXML
    Button submitBtn;

    private  RushOrderController rushOrderController;


    public void setRushOrderController(RushOrderController rushOrderController) {
        this.rushOrderController = rushOrderController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeComboBox.getItems().addAll(Arrays.asList("08:00 AM", "12:00 AM", "03:30 PM", "07:00 PM"));
        timeComboBox.getSelectionModel().selectFirst();

        gridPane.setHgap(10);
        gridPane.setVgap(40);
    }

    @FXML
    protected void Submit(ActionEvent event) throws IOException {

        HashMap<String, String> rushDeliveryInfo = new HashMap<>();
        rushDeliveryInfo.put("DeliveryTime", timeComboBox.getValue().toString());
        rushDeliveryInfo.put("InstructionDelivery", shippingInstructionsText.getText());

        if(rushOrderController.processRushDeliveryInfo(rushDeliveryInfo)) {
            rushOrderController.requestChoicePaymentMethod();
        }

    }


    public void initView() {

        Invoice invoice = rushOrderController.getInvoice();

        Order order = rushOrderController.getInvoice().getOrder();

        List<OrderMedia> orderMediaList = order.getLstOrderMedia();

        for(int i = 1; i <= orderMediaList.size(); i++) {
            Image image = new Image(getClass().getResourceAsStream("images.jpeg"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(40);
            imageView.setFitWidth(35);

            gridPane.add(new HBox(15, imageView, new Text(orderMediaList.get(i-1).getMedia().getTitle())),0, i);
            gridPane.add(new Text(String.valueOf(orderMediaList.get(i-1).getMedia().getPrice())),1, i);
            gridPane.add(new Text(String.valueOf(orderMediaList.get(i-1).getQuantity())),2, i);
            gridPane.add(new Text(String.valueOf(orderMediaList.get(i-1).getPrice())),3, i);
            gridPane.add(new CheckBox(),4, i);
        }

        DeliveryAddress deliveryAddress = order.getDeliveryAddress();

        nameText.setText(deliveryAddress.getName());
        phoneText.setText(deliveryAddress.getPhone());
        provinceText.setText(deliveryAddress.getProvince());
        addressText.setText(deliveryAddress.getAddress());

        subTotalText.setText(String.valueOf(invoice.getSubAmount()));
        vatText.setText(String.valueOf(invoice.getVat()));
        shippingFeeText.setText(String.valueOf(invoice.getOrder().getShippingFee()));
        amountText.setText(String.valueOf(invoice.getTotalAmount()));
    }
}
