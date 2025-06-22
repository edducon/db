// src/main/java/org/db/kursovoi/model/Tours.java
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
 * Singleton-модель "Tours".
 * Включает CRUD, выборку по клиенту и рисовалку.
 */
public final class Tours extends Observable {
    private static Tours INSTANCE;
    private Tours() { }
    public static synchronized Tours get() {
        if (INSTANCE == null) INSTANCE = new Tours();
        return INSTANCE;
    }

    /** Все туры */
    public ResultSet selectAll() throws SQLException {
        Connection cn = Database.get();
        PreparedStatement ps = cn.prepareStatement(
                "SELECT tour_id,client_id,hotel_id,cost,duration,departure_date,sale_date,discount_percent "
                        + "FROM tours",
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY
        );
        return ps.executeQuery();
    }

    /** Туры конкретного клиента */
    public ResultSet selectByClient(int clientId) throws SQLException {
        Connection cn = Database.get();
        PreparedStatement ps = cn.prepareStatement(
                "SELECT tour_id, hotel_id, cost, departure_date "
                        + "FROM tours WHERE client_id = ?"
        );
        ps.setInt(1, clientId);
        return ps.executeQuery();
    }

    /** Вставка нового тура и уведомление об изменении */
    public void insert(Tour t) {
        String sql = "INSERT INTO tours(client_id,hotel_id,cost,duration,departure_date,sale_date,discount_percent)"
                + " VALUES(?,?,?,?,?,?,?)";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, t.getClientId());
            ps.setInt(2, t.getHotelId());
            ps.setDouble(3, t.getCost());
            ps.setInt(4, t.getDuration());
            ps.setDate(5, java.sql.Date.valueOf(t.getDepartureDate()));
            ps.setDate(6, java.sql.Date.valueOf(t.getSaleDate()));
            ps.setInt(7, t.getDiscountPercent());
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Удаление тура и уведомление об изменении */
    public void delete(int id) {
        String sql = "DELETE FROM tours WHERE tour_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ——— рисовалка ———

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.fillText("=== Список туров ===", 10, 20);
        int y = 40;
        try (ResultSet rs = selectAll()) {
            while (rs.next()) {
                String line = rs.getInt("tour_id") + ": client "
                        + rs.getInt("client_id") + ", hotel "
                        + rs.getInt("hotel_id") + ", cost="
                        + rs.getDouble("cost");
                gc.fillText(line, 10, y);
                y += 20;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Открывает окно с Canvas и перерисовывает его при изменениях */
    public void showWindow(Stage owner) {
        Canvas canvas = new Canvas(600, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(javafx.scene.text.Font.font(14));
        draw(gc);
        addObserver((o, arg) -> draw(gc));

        Stage win = new Stage();
        win.initOwner(owner);
        win.setTitle("Управление турами");
        win.setScene(new Scene(new StackPane(canvas)));
        win.show();
    }
}
