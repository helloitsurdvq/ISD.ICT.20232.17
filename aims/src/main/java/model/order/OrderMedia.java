package model.order;

import database.db;
import model.media.Media;

import java.sql.*;

public class OrderMedia {
    private Media media;
    private int price;
    private int quantity;
    public OrderMedia(Media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderMedia() {

    }

    public void createOrderMediaEntity(int mediaId, int orderId, int price, int quantity) {
        String query = "INSERT INTO OrderMedia (mediaId, orderId, price, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = db.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, mediaId);
            preparedStatement.setInt(2, orderId);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, quantity);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order media failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "  media='" + media + "'" +
                ", quantity='" + quantity + "'" +
                ", price='" + price + "'" +
                "}";
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}