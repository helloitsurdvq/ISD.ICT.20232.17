package com.example.aims.view;

import com.example.aims.controller.PlaceOrderController;
import com.example.aims.entity.Cart;
import com.example.aims.entity.CartMedia;
import com.example.aims.entity.Media;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class CartScreenController implements Initializable {
    @FXML
    GridPane gridPane;

    @FXML
    Text subtotalText;

//    @FXML
//    Text amountText;

    @FXML
    Button placeOrderBtn;


    @FXML
    CheckBox choiceAllCheckbox;

    private Cart cart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceAllCheckbox.setSelected(true);
        gridPane.setHgap(10);
        gridPane.setVgap(40);

    }

    public void initView(Cart cart) {
        this.cart = cart;
        List<CartMedia> cartMedia = cart.getListCartMedia();
        for(int i = 1; i <= cartMedia.size(); i++) {

            Spinner<Integer> spinner = new Spinner<>();
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, cartMedia.get(i-1).getMedia().getQuantity(), cartMedia.get(i-1).getQuantity());
            spinner.setValueFactory(valueFactory);



            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(true);

            Image image = new Image(getClass().getResourceAsStream("images.jpeg"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(57);
            imageView.setFitWidth(50);
            gridPane.add(checkBox,0, i);
            gridPane.add(new HBox(15, imageView, new Text(cartMedia.get(i-1).getMedia().getTitle())),1, i);
            gridPane.add(new Text(String.valueOf(cartMedia.get(i-1).getMedia().getPrice())),2, i);
            gridPane.add(spinner,3, i);
            gridPane.add(new Text(String.valueOf(cartMedia.get(i-1).getPrice())),4, i);
            gridPane.add(new Button("delete"),5, i);
        }

        subtotalText.setText(String.valueOf(cart.getAmount()));

//        amountText.setText(String.valueOf(cart.getAmount()));
    }

    @FXML
    protected void requestToPlaceOrder(ActionEvent event) throws IOException {


        PlaceOrderController placeOrderController = new PlaceOrderController();
        Scene currentScene = ((Node) event.getSource()).getScene();

        placeOrderController.placeOrder(cart, currentScene);


    }


}
