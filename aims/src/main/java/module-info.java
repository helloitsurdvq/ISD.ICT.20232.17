//module com.aims {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//
//    opens com.aims to javafx.fxml;
//    exports com.aims;
//}

module main {
    requires javafx.controls;
    requires javafx.fxml;

    exports controller;
    exports app;

    opens app to javafx.fxml;
    opens controller to javafx.fxml;
}