package com.example.aims.entity;

import java.time.LocalDateTime;

public class RushOrder {
    private int id;
    private ShippingInfo shippingInfo;
    private ShippingMethod shippingMethod;
    private String instruction;
    private LocalDateTime deliveryTime;
}