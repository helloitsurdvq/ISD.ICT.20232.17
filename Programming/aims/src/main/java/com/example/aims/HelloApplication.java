package com.example.aims;

import com.example.aims.view.CartScreenController;
import com.example.aims.view.InvoiceScreenController;
import com.example.aims.view.MainScreenController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication {
    public static void main(String[] args) {
        MainScreenController mainScreenController = new MainScreenController();
        mainScreenController.on(args);
    }
}