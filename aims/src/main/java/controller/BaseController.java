package controller;

import model.media.Media;
import model.cart.Cart;
import model.cart.CartMedia;

import java.util.List;

/**
 * The base controller class.
 */
public class BaseController {
    /**
     * If the Media in Cart, this will return the CartMedia else return null.
     */
    public CartMedia checkMediaInCart(Media media) {
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * Gets the item list in cart.
     */
    public List getListCartMedia() {
        return Cart.getCart().getListMedia();
    }
}