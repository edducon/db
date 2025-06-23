package org.db.kursovoi.model;

import javafx.scene.canvas.GraphicsContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

public final class Countries extends Observable {

    private static Countries INSTANCE;
    private Countries() { }
    public static synchronized Countries get() {
        if (INSTANCE == null) INSTANCE = new Countries();
        return INSTANCE;
    }

    public ResultSet selectAll() throws SQLException {
        Connection cn = Database.get();
        PreparedStatement ps = cn.prepareStatement(
                "SELECT country_name, climate_features FROM countries",
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY
        );
        return ps.executeQuery();
    }

    public ResultSet selectByName(String name) throws SQLException {
        Connection cn = Database.get();
        PreparedStatement ps = cn.prepareStatement(
                "SELECT country_name, climate_features FROM countries WHERE country_name = ?",
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY
        );
        ps.setString(1, name);
        return ps.executeQuery();
    }

    public void insert(String name, String climate) {
        String sql = "INSERT INTO countries(country_name,climate_features) VALUES(?,?)";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, climate);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String oldName, String newName, String newClimate) {
        String sql = "UPDATE countries SET country_name=?,climate_features=? WHERE country_name=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setString(2, newClimate);
            ps.setString(3, oldName);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        String sql = "DELETE FROM countries WHERE country_name=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.fillText("Список стран", 10, 20);
        int y = 40;
        try (ResultSet rs = selectAll()) {
            while (rs.next()) {
                String line = rs.getString("country_name")
                        + " — " + rs.getString("climate_features");
                gc.fillText(line, 10, y);
                y += 20;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
