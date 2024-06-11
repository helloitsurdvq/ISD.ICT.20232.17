package controller;

import model.cart.Cart;

import java.sql.SQLException;

/**
 * This class controls the flow of events when users view the Cart
 */
public class ViewCartController extends BaseController {

    /**
     * This method checks the available products in Cart
     */
    public void checkAvailabilityOfProduct() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method calculates the cart subtotal
     */
    public int getCartSubtotal() {
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }
}