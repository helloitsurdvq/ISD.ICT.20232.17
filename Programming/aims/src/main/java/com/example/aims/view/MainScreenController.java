package com.example.aims.view;

import com.example.aims.entity.Cart;
import com.example.aims.entity.CartMedia;
import com.example.aims.entity.Media;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;



public class MainScreenController extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("cart-view.fxml"));

            Parent root = loader.load();
            CartScreenController controller = loader.getController();

            controller.initView(Cart.createNewCart());


            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }






    public void on(String[] args) {
        launch(args);
    }
}
