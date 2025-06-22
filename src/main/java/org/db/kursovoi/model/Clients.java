// src/main/java/org/db/kursovoi/model/Clients.java
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
 * Singleton-модель "Clients": CRUD + отрисовка + окно управления.
 */
public final class Clients extends Observable {

    private static Clients INSTANCE;
    private Clients() { }
    public static synchronized Clients get() {
        if (INSTANCE == null) INSTANCE = new Clients();
        return INSTANCE;
    }

    /** Возвращает всех клиентов */
    public ResultSet selectAll() throws SQLException {
        String sql = "SELECT client_id,last_name,first_name,patronymic,address,phone FROM clients";
        Connection cn = Database.get();
        PreparedStatement ps = cn.prepareStatement(
                sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY
        );
        return ps.executeQuery();
    }

    /** Находит одного клиента */
    public Client find(int id) {
        try {
            String sql = "SELECT client_id,last_name,first_name,patronymic,address,phone "
                    + "FROM clients WHERE client_id=?";
            try (Connection cn = Database.get();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Client(
                            rs.getInt("client_id"),
                            rs.getString("last_name"),
                            rs.getString("first_name"),
                            rs.getString("patronymic"),
                            rs.getString("address"),
                            rs.getString("phone")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Вставляет нового клиента и уведомляет об изменении */
    public int insert(String ln, String fn, String pt, String ad, String ph) {
        String sql = "INSERT INTO clients(last_name,first_name,patronymic,address,phone)"
                + " VALUES(?,?,?,?,?)";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, ln);
            ps.setString(2, fn);
            ps.setString(3, pt);
            ps.setString(4, ad);
            ps.setString(5, ph);
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                int id = gk.getInt(1);
                setChanged(); notifyObservers();
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /** Обновляет клиента и уведомляет об изменении */
    public void update(int id, String ln, String fn, String pt, String ad, String ph) {
        String sql = "UPDATE clients SET last_name=?,first_name=?,patronymic=?,address=?,phone=? WHERE client_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, ln);
            ps.setString(2, fn);
            ps.setString(3, pt);
            ps.setString(4, ad);
            ps.setString(5, ph);
            ps.setInt(6, id);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Удаляет клиента и уведомляет об изменении */
    public void delete(int id) {
        String sql = "DELETE FROM clients WHERE client_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ——— методы для отрисовки и показа окна ———

    /** Рисует список клиентов на переданном GraphicsContext */
    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.fillText("=== Список клиентов ===", 10, 20);
        int y = 40;
        try (ResultSet rs = selectAll()) {
            while (rs.next()) {
                String line = rs.getInt("client_id") + ": "
                        + rs.getString("last_name") + " "
                        + rs.getString("first_name") + " "
                        + (rs.getString("patronymic") == null ? "" : rs.getString("patronymic"))
                        + " | " + rs.getString("phone");
                gc.fillText(line, 10, y);
                y += 20;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Открывает окно управления клиентами и подписывается
     * на изменения для автоматической перерисовки списка.
     */
    public void showWindow(Stage owner) {
        Canvas canvas = new Canvas(600, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(javafx.scene.text.Font.font(14));

        draw(gc);
        this.addObserver((o, arg) -> draw(gc));

        Stage win = new Stage();
        win.initOwner(owner);
        win.setTitle("Управление клиентами");
        win.setScene(new Scene(new StackPane(canvas)));
        win.show();
    }
}
