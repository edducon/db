package org.db.kursovoi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

public final class Clients extends Observable {

    private static Clients INSTANCE;
    private Clients() { }
    public static synchronized Clients get() {
        if (INSTANCE == null) INSTANCE = new Clients();
        return INSTANCE;
    }

    public ResultSet selectAll() throws SQLException {
        String sql = "SELECT client_id,last_name,first_name,patronymic,address,phone FROM clients";
        Connection cn = Database.get();
        PreparedStatement ps = cn.prepareStatement(
                sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY
        );
        return ps.executeQuery();
    }

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
}
