package model;

import database.db;

import java.sql.*;

public class User {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String encrypted_password;

    public User(int id, String name, String email, String address, String phone, String encrypted_password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.encrypted_password = encrypted_password;
    }

    public User() {

    }

    public User authenticate(String email, String encryptedPassword) throws SQLException {
        String sql = "SELECT * FROM User " +
                "WHERE email = '" + email + "' AND encrypted_password = '" + encryptedPassword + "'";
        Statement stm = db.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            return new User(
                    res.getInt("id"),
                    res.getString("name"),
                    res.getString("email"),
                    res.getString("address"),
                    res.getString("phone"),
                    res.getString(("encrypted_password"))
            );
        } else {
            throw new SQLException();
        }
    }

    @Override
    public String toString() {
        return "{" +
                "  username='" + name + "'" +
                ", email='" + email + "'" +
                ", address='" + address + "'" +
                ", phone='" + phone + "'" +
                "}";
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}