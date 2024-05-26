package com.example.aims.entity;

public class Media {
    private int id;
    private String title;
    private String category;
    private int value;
    private int price;
    private int quantity;
    private String imageURL;
    private MediaShippingMethod mediaShippingMethod;

    public void setMediaShippingMethod(MediaShippingMethod mediaShippingMethod) {
        this.mediaShippingMethod = mediaShippingMethod;
    }

    public MediaShippingMethod getMediaShippingMethod() {
        return mediaShippingMethod;
    }

    public Media () {

    }

    public Media(int id, String title, String category, int value, int price, int quantity, String imageURL) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.value = value;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean checkRushOrderMedia() {
        return true;
    }
}