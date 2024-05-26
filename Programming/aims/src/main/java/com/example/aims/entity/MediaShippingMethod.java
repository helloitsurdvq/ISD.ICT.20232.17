package com.example.aims.entity;

public class MediaShippingMethod {
    private Media media;
    private ShippingMethod shippingMethod;

    public MediaShippingMethod(Media media, ShippingMethod shippingMethod) {
        this.media = media;
        this.shippingMethod = shippingMethod;
    }

    public String getNameMethod() {
        return shippingMethod.getName();
    }
}
