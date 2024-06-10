package model;
import model.order.Order;
public class Invoice {
    private Order order;
    private int amount;

    public Invoice() {

    }

    public Invoice(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void saveInvoice() {

    }
}