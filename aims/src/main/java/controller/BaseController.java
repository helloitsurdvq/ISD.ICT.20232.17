package controller;

import model.media.Media;
import model.cart.Cart;
import model.cart.CartMedia;

import java.util.List;

public class BaseController {
    public CartMedia checkMediaInCart(Media media) {
        return Cart.getCart().checkMediaInCart(media);
    }
    public List getListCartMedia() {
        return Cart.getCart().getListMedia();
    }
}