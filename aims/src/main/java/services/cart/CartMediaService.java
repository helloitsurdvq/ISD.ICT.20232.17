package services.cart;

import exception.ViewCartException;
import services.FXMLScreenService;
import utils.Format;
import utils.Configs;
import model.cart.Cart;
import model.cart.CartMedia;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.sql.SQLException;

public class CartMediaService extends FXMLScreenService {
    private static Logger LOGGER = Format.getLogger(CartMediaService.class.getName());
    @FXML
    protected HBox hboxMedia;
    @FXML
    protected ImageView image;
    @FXML
    protected VBox description;
    @FXML
    protected Label labelOutOfStock;
    @FXML
    protected VBox spinnerFX;
    @FXML
    protected Label title;
    @FXML
    protected Label price;
    @FXML
    protected Label currency;
    @FXML
    protected Button btnDelete;
    private CartMedia cartMedia;
    private Spinner<Integer> spinner;
    private CartScreenService cartScreen;

    public CartMediaService(String screenPath, CartScreenService cartScreen) throws IOException {
        super(screenPath);
        this.cartScreen = cartScreen;
        hboxMedia.setAlignment(Pos.CENTER);
    }

    public void setCartMedia(CartMedia cartMedia) {
        this.cartMedia = cartMedia;
        setMediaInfo();
    }

    private void setMediaInfo() {
        title.setText(cartMedia.getMedia().getTitle());
        price.setText(Format.getCurrencyFormat(cartMedia.getPrice()));
        File file = new File(cartMedia.getMedia().getImageURL());
        Image im = new Image(file.toURI().toString());
        image.setImage(im);
        image.setPreserveRatio(false);
        image.setFitHeight(110);
        image.setFitWidth(92);

        btnDelete.setFont(Configs.REGULAR_FONT);
        btnDelete.setOnMouseClicked(e -> {
            try {
                Cart.getCart().removeCartMedia(cartMedia);
                cartScreen.updateCart(); // re-display user cart
                LOGGER.info("Deleted " + cartMedia.getMedia().getTitle() + " from the cart");
            } catch (SQLException exp) {
                exp.printStackTrace();
                throw new ViewCartException();
            }
        });
        initializeSpinner();
    }

    private void initializeSpinner() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, cartMedia.getQuantity());
        spinner = new Spinner<Integer>(valueFactory);
        spinner.setOnMouseClicked(e -> {
            int numOfProd = this.spinner.getValue();
            int remainQuantity = cartMedia.getMedia().getQuantity();
            LOGGER.info("NumOfProd: " + numOfProd + " -- remainOfProd: " + remainQuantity);
            if (numOfProd > remainQuantity) {
                LOGGER.info("product " + cartMedia.getMedia().getTitle() + " only remains " + remainQuantity + " (required " + numOfProd + ")");
                labelOutOfStock.setText("Sorry, Only " + remainQuantity + " remain in stock");
                spinner.getValueFactory().setValue(remainQuantity);
                numOfProd = remainQuantity;
            }
            cartMedia.setQuantity(numOfProd);
            price.setText(Format.getCurrencyFormat(numOfProd * cartMedia.getPrice()));
            cartScreen.updateCartAmount();

        });
        spinnerFX.setAlignment(Pos.CENTER);
        spinnerFX.getChildren().add(this.spinner);
    }
}