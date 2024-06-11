module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires javafx.web;
    requires org.xerial.sqlitejdbc;

    exports controller;
    exports app;
    exports services;

    opens app to javafx.fxml;
    opens controller to javafx.fxml, java.sql;
    opens services to javafx.fxml, javafx.web, java.desktop;
    opens services.home to javafx.fxml;
    opens services.invoice to javafx.fxml;
    opens services.manage to javafx.fxml;
    opens services.cart to javafx.fxml;
    opens services.manage.media to javafx.fxml;
    opens services.manage.media.detail to javafx.fxml;
    opens services.manage.media.form to javafx.fxml;
    opens services.manage.order to javafx.fxml;
    opens services.payment to javafx.fxml;
    opens services.shipping to javafx.fxml;
    opens model to javafx.base;
    opens model.order to javafx.base;
    opens model.media to javafx.base;
}