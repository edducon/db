// src/main/java/org/db/kursovoi/model/Hotels.java
package org.db.kursovoi.model;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

/**
 * Singleton-модель "Hotels".
 * Методы insert/update/delete шлют notifyObservers(), плюс рисует своё окно.
 */
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


    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.fillText("Список отелей", 10, 20);
        int y = 40;
        try (ResultSet rs = selectAll()) {
            while (rs.next()) {
                String line = rs.getInt("hotel_id") + ": "
                        + rs.getString("country_name") + ", "
                        + rs.getInt("class") + "★, "
                        + rs.getString("hotel_name");
                gc.fillText(line, 10, y);
                y += 20;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
