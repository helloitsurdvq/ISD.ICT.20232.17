package services.cart;

import controller.PlaceOrderController;
import controller.ViewCartController;
import model.cart.CartMedia;
import model.order.Order;
import exception.NotAvailableMediaException;
import exception.PlaceOrderException;
import services.BaseScreenService;
import services.PopUpScreenService;
import services.shipping.ShippingScreenService;
import utils.Configs;
import utils.Format;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CartScreenService extends BaseScreenService {
    @FXML
    VBox vboxCart;
    @FXML
    private ImageView aimsImage;
    @FXML
    private Label pageTitle;
    @FXML
    private Label shippingFees;
    @FXML
    private Label labelAmount;
    @FXML
    private Label labelSubtotal;
    @FXML
    private Label labelVAT;
    @FXML
    private Button btnPlaceOrder;

    public CartScreenService(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);

        Image im = loadImage("/assets/Logo.png");
        aimsImage.setImage(im);
        aimsImage.setOnMouseClicked(e -> {
            homeScreenService.show();
        });
        btnPlaceOrder.setOnMouseClicked(e -> {
            try {
                requestOrder();
            } catch (SQLException | IOException exp) {
                exp.printStackTrace();
                throw new PlaceOrderException(Arrays.toString(exp.getStackTrace()).replaceAll(", ", "\n"));
            }
        });
    }

    private Image loadImage(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            return new Image(imageUrl.toString());
        } else {
            URL defaultImageUrl = getClass().getResource("/assets/Logo.png");
            if (defaultImageUrl != null) {
                return new Image(defaultImageUrl.toString());
            } else {
                System.out.println("Image path not found");
                return null;
            }
        }
    }

    public Label getLabelAmount() {
        return labelAmount;
    }

    public Label getLabelSubtotal() {
        return labelSubtotal;
    }

    public ViewCartController getBController() {
        return (ViewCartController) super.getBController();
    }

    public void requestToViewCart(BaseScreenService prevScreen) throws SQLException {
        setPreviousScreen(prevScreen);
        setScreenTitle("Cart Screen");
        getBController().checkAvailabilityOfProduct();
        displayCartWithMediaAvailability();
        show();
    }

    public void requestOrder() throws SQLException, IOException {
        try {
            // create placeOrderController and process the order
            var placeOrderController = new PlaceOrderController();
            if (placeOrderController.getListCartMedia().size() == 0) {
                PopUpScreenService.error("Cart is empty!");
                return;
            }

            placeOrderController.placeOrder();

            // create order
            Order order = placeOrderController.createOrder();

            // display shipping form
            ShippingScreenService ShippingScreenService = new ShippingScreenService(this.stage, Configs.SHIPPING_SCREEN_PATH, order);
            ShippingScreenService.setPreviousScreen(this);
            ShippingScreenService.setHomeScreenHandler(homeScreenService);
            ShippingScreenService.setScreenTitle("Shipping Screen");
            ShippingScreenService.setBController(placeOrderController);
            ShippingScreenService.show();

        } catch (NotAvailableMediaException e) {
            displayCartWithMediaAvailability();
        }
    }

    public void updateCart() throws SQLException {
        getBController().checkAvailabilityOfProduct();
        displayCartWithMediaAvailability();
    }

    void updateCartAmount() {
        // calculate subtotal and amount
        int subtotal = getBController().getCartSubtotal();
        int vat = (int) ((Configs.PERCENT_VAT / 100) * subtotal);
        int amount = subtotal + vat;

        // update subtotal and amount of Cart
        labelSubtotal.setText(Format.getCurrencyFormat(subtotal));
        labelVAT.setText(Format.getCurrencyFormat(vat));
        labelAmount.setText(Format.getCurrencyFormat(amount));
    }

    private void displayCartWithMediaAvailability() {
        // clear all old cartMedia
        vboxCart.getChildren().clear();

        // get list media of cart after check availability
        List lstMedia = getBController().getListCartMedia();

        try {
            for (Object cm : lstMedia) {
                // display the attribute of vboxCart media
                CartMedia cartMedia = (CartMedia) cm;
                CartMediaService mediaCartScreen = new CartMediaService(Configs.CART_MEDIA_PATH, this);
                mediaCartScreen.setCartMedia(cartMedia);

                // add spinner
                vboxCart.getChildren().add(mediaCartScreen.getContent());
            }
            // calculate subtotal and amount
            updateCartAmount();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}