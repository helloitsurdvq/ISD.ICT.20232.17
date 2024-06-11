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
    // common coupling
    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Format.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder
     */
    public void placeOrder() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     */
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

    /**
     * This method creates the new Invoice based on order
     */
    public Invoice createInvoice(Order order) {

        order.createOrderEntity();
        order.getlstOrderMedia().forEach(orderMedia -> {
            orderMedia.createOrderMediaEntity(orderMedia.getMedia().getId(), order.getId(), orderMedia.getPrice(), orderMedia.getQuantity());
        });
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        validateDeliveryInfo(info);
    }

    /**
     * The method validates the info
     */
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


    /**
     * This method calculates the shipping fees of order
     */
    public int calculateShippingFee(int amount) {
        Random rand = new Random();
        int fees = (int) (((rand.nextFloat() * 10) / 100) * amount);
        return fees;
    }

    /**
     * This method get product available place rush order media
     */
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


    /**
     * @param province
     * @param address
     * @return boolean
     */
    public boolean validateAddressPlaceRushOrder(String province, String address) {
        if (!validateContainLetterAndNoEmpty(address))
            return false;
        if (!province.equals("Hà Nội"))
            return false;
        return true;
    }


    /**
     * @return boolean
     */
    public boolean validateMediaPlaceRushorder() {
        if (Media.getIsSupportedPlaceRushOrder())
            return true;
        return false;
    }
}
