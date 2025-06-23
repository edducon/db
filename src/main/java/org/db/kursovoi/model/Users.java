package org.db.kursovoi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

public final class Users extends Observable {

    private static Users INSTANCE;
    private Users() { }
    public static synchronized Users get() {
        if (INSTANCE == null) INSTANCE = new Users();
        return INSTANCE;
    }

    public ResultSet selectAll() throws SQLException {
        Connection cn = Database.get();
        PreparedStatement ps = cn.prepareStatement(
                "SELECT user_id,username,role,client_id FROM users",
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY
        );
        return ps.executeQuery();
    }

    public boolean exists(String username) {
        String sql = "SELECT 1 FROM users WHERE username=? LIMIT 1";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User auth(String u, String p) {
        String sql = "SELECT user_id,username,role,client_id FROM users WHERE username=? AND password=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, u);
            ps.setString(2, p);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getObject("client_id")==null ? null : rs.getInt("client_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(String un, String pw, int cid, String role) {
        String sql = "INSERT INTO users(username,password,role,client_id) VALUES(?,?,?,?)";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, un);
            ps.setString(2, pw);
            ps.setString(3, role);
            ps.setInt(4, cid);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUsername(int id, String un) {
        String sql = "UPDATE users SET username=? WHERE user_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, un);
            ps.setInt(2, id);
            ps.executeUpdate();
            setChanged(); notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(int id, String pw) {
        String sql = "UPDATE users SET password=? WHERE user_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, pw);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users WHERE user_id=?";
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
