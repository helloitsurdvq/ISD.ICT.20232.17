module com.example.aims {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.web;

    opens com.example.aims.view to javafx.fxml;
    exports com.example.aims.view;
    exports com.example.aims.subsystem.vnpay;
    opens com.example.aims.subsystem.vnpay to javafx.fxml;

    opens com.example.aims.controller;
    opens com.example.aims.entity;

}