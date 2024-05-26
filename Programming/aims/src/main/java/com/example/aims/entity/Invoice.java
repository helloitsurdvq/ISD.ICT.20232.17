package com.example.aims.entity;

public class Invoice {

    private int totalAmount;

    private int subAmount;

    private Order order;
    private int vat;

    private PaymentTransaction paymentTransaction;


    public Invoice() {};

    public Order getOrder() {
        return order;
    }


    public int getVat() {
        return vat;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public Invoice(int totalAmount, Order order, int vat, PaymentTransaction paymentTransaction) {
        this.totalAmount = totalAmount;
        this.order = order;
        this.vat = vat;
        this.paymentTransaction = paymentTransaction;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public void setSubAmount(int subAmount) {
        this.subAmount = subAmount;
    }

    public int getSubAmount() {
        return subAmount;
    }

    public static Invoice createInvoice(Order order) {
        Invoice invoice = new Invoice();


        invoice.setOrder(order);

        invoice.setSubAmount(order.getAmount());

        invoice.setVat((int) Math.round(order.getAmount()*0.1));

        invoice.setTotalAmount(order.getAmount() + invoice.getVat() + order.getShippingFee());

        return invoice;

    }
}
