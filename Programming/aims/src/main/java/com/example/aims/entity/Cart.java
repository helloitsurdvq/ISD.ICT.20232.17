package com.example.aims.entity;

import java.util.List;

public class Cart {
    private List<CartMedia> listCartMedia;
    public Cart() {
    }
    public int getAmount() {
        int totalAmount = 0;

        for (CartMedia item : listCartMedia) {
            totalAmount += item.getPrice();
        }

        return totalAmount;
    }

    public List<CartMedia> getListCartMedia() {
        return listCartMedia;
    }

    public void setListCartMedia(List<CartMedia> listCartMedia) {
        this.listCartMedia = listCartMedia;
    }

    public Cart(List<CartMedia> listCartMedia) {
        this.listCartMedia = listCartMedia;
    }

    public boolean checkAvailabilityOfProduct() {
        return true;
    }

    public static Cart createNewCart() {
        Media media0 = new Media(0, "Book1", "book", 40000, 20000, 10, "../assets/images.jpeg");
        Media media1 = new Media(0, "Book2", "book", 40000, 20000, 20, "../assets/images.jpeg");

        CartMedia cartMedia1 = new CartMedia(media0, 2, 40000 );
        CartMedia cartMedia2 = new CartMedia(media1, 1, 20000 );

        Cart cart = new Cart();
        cart.setListCartMedia(List.of(cartMedia1, cartMedia2));
        return cart;
    }
}
