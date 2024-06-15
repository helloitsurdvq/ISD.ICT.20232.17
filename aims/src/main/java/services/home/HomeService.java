package services.home;

import model.cart.CartMedia;
import model.cart.Cart;
import model.media.Media;
import services.FXMLScreenService;
import services.PopUpScreenService;
import exception.NotAvailableMediaException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Format;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class HomeService extends FXMLScreenService {
    private static Logger LOGGER = Format.getLogger(HomeService.class.getName());
    @FXML
    protected ImageView mediaImage;
    @FXML
    protected Label mediaTitle;
    @FXML
    protected Label mediaPrice;
    @FXML
    protected Label mediaAvail;
    @FXML
    protected Spinner<Integer> spinnerChangeNumber;
    @FXML
    protected Button addToCartBtn;
    private Media media;
    private HomeScreenService home;

    public HomeService(String screenPath, Media media, HomeScreenService home) throws SQLException, IOException {
        super(screenPath);
        this.media = media;
        this.home = home;

        addToCartBtn.setOnMouseClicked(event -> {
            try {
                if (spinnerChangeNumber.getValue() > media.getQuantity()) throw new NotAvailableMediaException();
                Cart cart = Cart.getCart();
                // if media already in cart then we will increase the quantity by 1 instead of create the new cartMedia
                CartMedia mediaInCart = home.getBController().checkMediaInCart(media);
                if (mediaInCart != null) {
                    mediaInCart.setQuantity(mediaInCart.getQuantity() + 1);
                } else {
                    CartMedia cartMedia = new CartMedia(media, cart, spinnerChangeNumber.getValue(), media.getPrice());
                    cart.getListMedia().add(cartMedia);
                }

                // subtract the quantity and redisplay
                media.setQuantity(media.getQuantity() - spinnerChangeNumber.getValue());
                mediaAvail.setText(String.valueOf(media.getQuantity()));
                home.getNumMediaCartLabel().setText(String.valueOf(cart.getTotalMedia() + " media"));
                PopUpScreenService.success("The media " + media.getTitle() + " added to Cart");
            } catch (NotAvailableMediaException exp) {
                try {
                    String message = "Not enough media:\nRequired: " + spinnerChangeNumber.getValue() + "\nAvail: " + media.getQuantity();
                    LOGGER.severe(message);
                    PopUpScreenService.error(message);
                } catch (Exception e) {
                    LOGGER.severe("Cannot add media to cart: ");
                }

            } catch (Exception exp) {
                LOGGER.severe("Cannot add media to cart: ");
                exp.printStackTrace();
            }
        });
        setMediaInfo();
    }

    public Media getMedia() {
        return media;
    }

    private void setMediaInfo() throws SQLException {
        // set the cover image of media
        File file = new File(media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setFitHeight(160);
        mediaImage.setFitWidth(152);
        mediaImage.setImage(image);

        mediaTitle.setText(media.getTitle());
        mediaPrice.setText(Format.getCurrencyFormat(media.getPrice()));
        mediaAvail.setText(Integer.toString(media.getQuantity()));
        spinnerChangeNumber.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );
        setImage(mediaImage, media.getImageURL());
    }
}