package model.order;

import database.db;
import utils.*;
import model.media.Media;
import model.Shipment;
import services.home.HomeService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
public class Order {
    private static Logger LOGGER = Format.getLogger(HomeService.class.getName());
    private int shippingFees;
    private List<OrderMedia> lstOrderMedia;
    private Shipment shipment;
    private String name;
    private String province;
    private String instruction;
    private String status;

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    private String address;

    public List<OrderMedia> getLstOrderMedia() {
        return lstOrderMedia;
    }

    public void setLstOrderMedia(List<OrderMedia> lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String phone;
    private Integer id;

    public Order() {
        this.lstOrderMedia = new ArrayList<OrderMedia>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void createOrderEntity(){
        try {
            Statement stm = db.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String query = "INSERT INTO 'Order' (name, province, address, phone, shipping_fee) " +
                "VALUES ( ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = db.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, province);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, shippingFees);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);

                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

            String sqlinsertShipment = "INSERT INTO Shipment (shipType, deliveryInstruction, dateTime, deliverySub, orderId) " +
                    "VALUES ( ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement2 = db.getConnection().prepareStatement(sqlinsertShipment)) {

                LOGGER.info(sqlinsertShipment);
                preparedStatement2.setInt(1, shipment.getShipType());
                preparedStatement2.setString(2, shipment.getDeliveryInstruction());
                preparedStatement2.setString(3, shipment.getDeliveryTime());
                preparedStatement2.setString(4, shipment.getShipmentDetail());
                preparedStatement2.setInt(5, id);
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> getAllOrder() throws SQLException {
        Statement stm = db.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from 'Order' inner join Shipment on 'Order'.id = Shipment.orderId");
        ArrayList listOrder = new ArrayList<>();
        while (res.next()) {
            Order order = new Order();
            order.setId(res.getInt(1));
            order.setName(res.getString("name"));
            order.setPhone(res.getString("phone"));
            order.setAddress(res.getString("address"));
            order.setProvince(res.getString("province"));
            order.setShippingFees(res.getInt("shipping_fee"));
            order.setStatus(res.getString("status"));

            // Create a Shipment object and set its properties
            Shipment shipment = new Shipment(
                    res.getInt("shipType"),
                    res.getString("deliveryInstruction"),
                    res.getString("deliverySub"),
                    res.getString("dateTime")
            );
            order.setShipment(shipment);
            listOrder.add(order);
        }
        return listOrder;
    }

    public void updateOrderStatus(int id, String status) {
        try (PreparedStatement preparedStatement = db.getConnection().prepareStatement(
                "update 'Order' set status = ? WHERE id = ?")) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating order status failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order getOrderById(int id) {
        String query = "SELECT * FROM 'Order' INNER JOIN Shipment ON 'Order'.id = Shipment.orderId WHERE 'Order'.id = ?";
        try (PreparedStatement preparedStatement = db.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                Order order = new Order();
                order.setId(res.getInt(1));
                order.setName(res.getString("name"));
                order.setPhone(res.getString("phone"));
                order.setAddress(res.getString("address"));
                order.setProvince(res.getString("province"));
                order.setShippingFees(res.getInt("shipping_fee"));
                order.setStatus(res.getString("status"));

                // Create a list to store OrderMedia objects
                List<OrderMedia> listOrderMedia = new ArrayList<>();

                String orderMediaQuery = "SELECT mediaID, title, type, imageUrl, OrderMedia.price AS orderMediaPrice, OrderMedia.quantity AS orderMediaQuantity, Media.quantity AS mediaQuantity FROM OrderMedia INNER JOIN Media ON OrderMedia.mediaID = Media.id WHERE orderId = ? ORDER BY mediaID";
                try (PreparedStatement orderMediaStatement = db.getConnection().prepareStatement(orderMediaQuery)) {
                    orderMediaStatement.setInt(1, id);
                    ResultSet orderMediaRes = orderMediaStatement.executeQuery();

                    while (orderMediaRes.next()) {
                        Media media = new Media();
                        media.setId(orderMediaRes.getInt("mediaID"));
                        media.setTitle(orderMediaRes.getString("title"));
                        media.setType(orderMediaRes.getString("type"));
                        media.setQuantity(orderMediaRes.getInt("mediaQuantity"));
                        media.setMediaURL(orderMediaRes.getString("imageUrl"));
                        OrderMedia orderMedia = new OrderMedia();
                        orderMedia.setMedia(media);
                        orderMedia.setPrice(orderMediaRes.getInt("orderMediaPrice"));
                        orderMedia.setQuantity(orderMediaRes.getInt("orderMediaQuantity"));
                        listOrderMedia.add(orderMedia);
                    }
                }

                order.setlstOrderMedia(listOrderMedia);
                Shipment shipment = new Shipment(
                        res.getInt("shipType"),
                        res.getString("deliveryInstruction"),
                        res.getString("deliverySub"),
                        res.getString("dateTime")
                );
                order.setShipment(shipment);
                return order;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addOrderMedia(OrderMedia om) {
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om) {
        this.lstOrderMedia.remove(om);
    }

    public List<OrderMedia> getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public int getAmount() {
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) (amount + (Configs.PERCENT_VAT / 100) * amount);
    }
}