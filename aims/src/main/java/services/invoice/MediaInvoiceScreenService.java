package services.invoice;

import model.order.OrderMedia;
import utils.Format;
import services.FXMLScreenService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class MediaInvoiceScreenService extends FXMLScreenService {
    @FXML
    private HBox hboxMedia;
    @FXML
    private VBox imageLogoVbox;
    @FXML
    private ImageView image;
    @FXML
    private VBox description;
    @FXML
    private Label title;
    @FXML
    private Label numOfProd;

    @FXML
    private Label labelOutOfStock;

    @FXML
    private Label price;
    private OrderMedia orderMedia;

    public MediaInvoiceScreenService(String screenPath) throws IOException {
        super(screenPath);
    }

    public void setOrderMedia(OrderMedia orderMedia) throws SQLException {
        this.orderMedia = orderMedia;
        setMediaInfo();
    }

    public void setMediaInfo() throws SQLException {
        title.setText(orderMedia.getMedia().getTitle());
        price.setText(Format.getCurrencyFormat(orderMedia.getPrice()));
        numOfProd.setText(String.valueOf(orderMedia.getQuantity()));
        setImage(image, orderMedia.getMedia().getImageURL());
        image.setPreserveRatio(false);
        image.setFitHeight(90);
        image.setFitWidth(83);
    }
}