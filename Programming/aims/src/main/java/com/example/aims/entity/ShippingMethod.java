package com.example.aims.entity;

public class ShippingMethod{
    private int id;
    private String name;
    private String description;

    public ShippingMethod(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
