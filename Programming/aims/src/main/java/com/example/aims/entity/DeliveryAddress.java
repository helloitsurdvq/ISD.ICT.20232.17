package com.example.aims.entity;

public class DeliveryAddress {
    private String name;
    private String phone;
    private String province;

    private String address;

    private String instruction;

    public DeliveryAddress() {

    }


    public DeliveryAddress(String name, String phone, String province, String address, String instruction) {
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.address = address;
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
