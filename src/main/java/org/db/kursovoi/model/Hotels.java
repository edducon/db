package org.db.kursovoi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

public final class Hotels extends Observable {
    private static Hotels INSTANCE;
    private Hotels() { }
    public static synchronized Hotels get() {
        if (INSTANCE == null) INSTANCE = new Hotels();
        return INSTANCE;
    }

    public ResultSet selectAll() throws SQLException {
        Connection cn = Database.get();
        PreparedStatement ps = cn.prepareStatement(
                "SELECT hotel_id,country_name,class,hotel_name FROM hotels",
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY
        );
        return ps.executeQuery();
    }

    public ResultSet selectByCountry(String country) throws SQLException {
        Connection cn = Database.get();
        PreparedStatement ps = cn.prepareStatement(
                "SELECT hotel_id,country_name,class,hotel_name FROM hotels WHERE country_name=?",
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY
        );
        ps.setString(1, country);
        return ps.executeQuery();
    }

    public void insert(String country, int stars, String name) {
        String sql = "INSERT INTO hotels(country_name,class,hotel_name) VALUES(?,?,?)";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, country);
            ps.setInt(2, stars);
            ps.setString(3, name);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String country, int stars, String name) {
        String sql = "UPDATE hotels SET country_name=?,class=?,hotel_name=? WHERE hotel_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, country);
            ps.setInt(2, stars);
            ps.setString(3, name);
            ps.setInt(4, id);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM hotels WHERE hotel_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
