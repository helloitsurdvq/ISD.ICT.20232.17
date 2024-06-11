package controller;

import model.media.Media;
import model.cart.Cart;
import model.cart.CartMedia;

import java.util.List;

/**
 * This class is the base controller.
 */
public class BaseController {
    /**
     * The method checks whether the Media in Cart, if it were in, we will return
     * the CartMedia else return null.
     */
    public CartMedia checkMediaInCart(Media media) {
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * This method gets the list of items in cart.
     */
    public List getListCartMedia() {
        return Cart.getCart().getListMedia();
    }
}