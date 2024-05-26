package com.example.aims.entity;

public class OrderMedia {
    private Media media;
    private int price;
    private int quantity;
    private Order order;
    public int getQuantity() {
        return quantity;
    }

    public OrderMedia(Media media, int price, int quantity, Order order) {
        this.media = media;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    public OrderMedia() {
    }

    public int getPrice() {
        return price;
    }

    public Media getMedia() {
        return media;
    }
}