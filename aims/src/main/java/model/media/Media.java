package model.media;

import database.db;
import utils.Format;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Media {
    protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value;
    protected int price;
    protected int quantity;
    protected String type;
    protected String imageURL;
    protected static boolean isSupportedPlaceRushOrder = new Random().nextBoolean();

    public Media() {
        try {
            stm = db.getConnection().createStatement();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Media(int id, String title, String category, int price, int quantity, String imageURL, String type) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
        this.type = type;
        this.value = 0;
    }

    public static boolean getIsSupportedPlaceRushOrder() {
        return Media.isSupportedPlaceRushOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Media from(ResultSet res) throws SQLException {
        title = res.getString("title");
        category = res.getString("category");
        price = res.getInt("price");
        value = res.getInt("value");
        quantity = res.getInt("quantity");
        imageURL = res.getString("imageUrl");
        type = res.getString("type");
        return this;
    }

    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Media "
                + "WHERE id = " + id + ";";
        Statement stm = db.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {
            return new Media().from(res).setId(id);
        }
        return null;
    }

    public List<Media> getAllMedia() throws SQLException {
        Statement stm = db.getConnection().createStatement();
        ResultSet res = stm.executeQuery("SELECT * from Media ORDER BY id DESC;");
        ArrayList listMedia = new ArrayList<>();
        while (res.next()) {
            Media media = new Media().from(res).setId(res.getInt("id"));
            listMedia.add(media);
        }
        return listMedia;
    }

    public void updateMediaFieldById(String tbname, int id, String field, String value) throws SQLException {
        Statement stm = db.getConnection().createStatement();
        value = "\"" + value + "\"";
        stm.executeUpdate(" update " + tbname + " set" + " "
                + field + "=" + value + " "
                + "where id=" + id + ";");
    }

    public void create() throws SQLException {
        String sql = "INSERT INTO Media (title, value, price, quantity, category, \"imageUrl\", type) "
                + "VALUES (?,?,?,?,?,?,?) "
                + ";";
        PreparedStatement stm = db.getConnection().prepareStatement(sql, new String[]{"id"});
        stm.setString(1, title);
        stm.setInt(2, value);
        stm.setInt(3, price);
        stm.setInt(4, quantity);
        stm.setString(5, category);
        stm.setString(6, imageURL);
        stm.setString(7, type);
        if (stm.executeUpdate() == 0) {
            throw new SQLException("Creating failed, no rows affected.");
        }
        ResultSet res = stm.getGeneratedKeys();
        if (res.next()) {
            id = res.getInt(1);
        }
    }

    public void save() throws SQLException {
        if (id == 0) {
            create();
        } else {
            String sql = "UPDATE Media SET "
                    + "title = ?, value = ?, price = ?, quantity = ?, category = ?, \"imageUrl\" = ?, type = ? "
                    + "WHERE id = ?;";
            PreparedStatement stm = db.getConnection().prepareStatement(sql);
            stm.setString(1, title);
            stm.setInt(2, value);
            stm.setInt(3, price);
            stm.setInt(4, quantity);
            stm.setString(5, category);
            stm.setString(6, imageURL);
            stm.setString(7, type);
            stm.setInt(8, id);
            stm.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Media WHERE id = ?;";
        PreparedStatement stm = db.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();
    }

    public int getId() {
        return this.id;
    }

    public Media setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Media setMediaURL(String url) {
        this.imageURL = url;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    public int getValue() {
        return this.value;
    }

    public Media setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", title='" + title + "'" +
                ", category='" + category + "'" +
                ", price='" + price + "'" +
                ", quantity='" + quantity + "'" +
                ", type='" + type + "'" +
                ", imageURL='" + imageURL + "'" +
                "}";
    }

    public void updateMediaQuantity(int mediaId, int newQuantity) {
        try (PreparedStatement preparedStatement = db.getConnection().prepareStatement("UPDATE Media SET quantity = ? WHERE id = ?")) {
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, mediaId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}