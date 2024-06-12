package model.cart;

import exception.NotAvailableMediaException;
import model.media.Media;

import java.sql.*;
import java.util.*;

public class Cart {
    // common coupling
    private static Cart cartInstance;
    private List<CartMedia> lstCartMedia;


    private Cart() {
        lstCartMedia = new ArrayList<>();
    }

    public static Cart getCart() {
        if (cartInstance == null) cartInstance = new Cart();
        return cartInstance;
    }

    public void removeCartMedia(CartMedia cm) {
        lstCartMedia.remove(cm);
    }

    public List getListMedia() {
        return lstCartMedia;
    }

    public void emptyCart() {
        lstCartMedia.clear();
    }

    public int getTotalMedia() {
        int total = 0;
        for (Object obj : lstCartMedia) {
            CartMedia cm = (CartMedia) obj;
            total += cm.getQuantity();
        }
        return total;
    }

    public int calSubtotal() {
        int total = 0;
        for (Object obj : lstCartMedia) {
            CartMedia cm = (CartMedia) obj;
            total += cm.getPrice() * cm.getQuantity();
        }
        return total;
    }

    public void checkAvailabilityOfProduct() throws SQLException {
        // control coupling
        boolean allAvai = true;
        for (Object object : lstCartMedia) {
            CartMedia cartMedia = (CartMedia) object;
            int requiredQuantity = cartMedia.getQuantity();
            int availQuantity = cartMedia.getMedia().getQuantity();
            if (requiredQuantity > availQuantity) allAvai = false;
        }
        if (!allAvai) throw new NotAvailableMediaException("Some media is not available");
    }

    public CartMedia checkMediaInCart(Media media) {
        for (CartMedia cartMedia : lstCartMedia) {
            if (cartMedia.getMedia().getId() == media.getId()) return cartMedia;
        }
        return null;
    }
}