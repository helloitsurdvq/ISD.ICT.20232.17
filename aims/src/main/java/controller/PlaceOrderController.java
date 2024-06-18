package controller;

import model.cart.Cart;
import model.cart.CartMedia;
import model.Invoice;
import model.media.Media;
import model.order.Order;
import model.order.OrderMedia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

public class PlaceOrderController extends BaseController {
    private static Logger LOGGER = utils.Format.getLogger(PlaceOrderController.class.getName());

    public void placeOrder() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }

    public Order createOrder() throws SQLException {
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    public Invoice createInvoice(Order order) {

        order.createOrderEntity();
        order.getlstOrderMedia().forEach(orderMedia -> {
            orderMedia.createOrderMediaEntity(orderMedia.getMedia().getId(), order.getId(), orderMedia.getPrice(), orderMedia.getQuantity());
        });
        return new Invoice(order);
    }

    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        validateDeliveryInfo(info);
    }

    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {

    }

    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10)
            return false;
        if (Character.compare(phoneNumber.charAt(0), '0') != 0)
            return false;
        try {
            Long.parseUnsignedLong(phoneNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean validateContainLetterAndNoEmpty(String name) {
        // Check name is not null
        if (name == null)
            return false;
        // Check if contain letter space only
        if (name.trim().length() == 0)
            return false;
        // Check if contain only letter and space
        if (name.matches("^[a-zA-Z ]*$") == false)
            return false;
        return true;
    }

    public int calculateShippingFee(int amount) {
        Random rand = new Random();
        int fees = (int) (((rand.nextFloat() * 10) / 100) * amount);
        return fees;
    }

    public Media getProductAvailablePlaceRush(Order order) throws SQLException {
        Media media = new Media();
        for (OrderMedia pd : order.getlstOrderMedia()) {
            // CartMedia cartMedia = (CartMedia) object;
            if( validateMediaPlaceRushorder()){
                media = pd.getMedia();
            }
        }
        return media;
    }

    public boolean validateAddressPlaceRushOrder(String province, String address) {
        if (!validateContainLetterAndNoEmpty(address))
            return false;
        if (!province.equals("Hà Nội"))
            return false;
        return true;
    }

    public boolean validateMediaPlaceRushorder() {
        if (Media.getIsSupportedPlaceRushOrder())
            return true;
        return false;
    }
}