package com.example.aims.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Order {
    private int shippingFee;
    private DeliveryAddress deliveryAddress;
    private List<OrderMedia> lstOrderMedia = new ArrayList<>();

    public void setLstOrderMedia(List<OrderMedia> lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public Order(int shippingFee, DeliveryAddress deliveryAddress) {
        this.shippingFee = shippingFee;
        this.deliveryAddress = deliveryAddress;
    }

    public Order() {

    }

    public int getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public static Order createOrder(Cart cart) {
        Order order = new Order();
        for(int i = 0; i < cart.getListCartMedia().size(); i++) {
            CartMedia cartMedia = cart.getListCartMedia().get(i);
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), cartMedia.getPrice(), cartMedia.getQuantity(), order);
            order.addOrderMedia(orderMedia);
        }
        return order;
    }

    public void addOrderMedia(OrderMedia orderMedia){
        this.lstOrderMedia.add(orderMedia);
    }

    public void calculateShippingFee() {
        Random rand = new Random();
        int fees = (int) ((rand.nextFloat()) * this.getAmount());
        this.shippingFee = fees;
    }

    public int getAmount() {
        int amount = 0;
        for(OrderMedia orderMedia : lstOrderMedia) {
            amount += orderMedia.getPrice();
        }
        return amount;
    }

    public List<OrderMedia> getLstOrderMedia() {
        return lstOrderMedia;
    }

    public boolean checkRushOrderAddress() {
        System.out.println(this.deliveryAddress.getProvince());
        if (this.deliveryAddress.getProvince().equals("Hà Nội")) {
            return true;
        }
        return false;
    }
}