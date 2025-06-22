package org.db.kursovoi.model;

import java.sql.*;
import java.util.*;

public final class Clients extends java.util.Observable {

    private static Clients INSTANCE;
    private Clients() { loadAll(); }
    public static synchronized Clients get() {
        if (INSTANCE == null) INSTANCE = new Clients();
        return INSTANCE;
    }

    private final List<Client> cache = new ArrayList<Client>();
    public List<Client> getAll() { return Collections.unmodifiableList(cache); }

    public Client find(int id) {
        for (Client c : cache) if (c.getId() == id) return c;
        return null;
    }

    public void refresh() {
        cache.clear(); loadAll();
        setChanged(); notifyObservers();
    }

    public int insert(String ln,String fn,String pt,String ad,String ph) {
        String sql = "INSERT INTO clients(last_name,first_name,patronymic,address,phone) VALUES(?,?,?,?,?)";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ln); ps.setString(2, fn); ps.setString(3, pt);
            ps.setString(4, ad); ps.setString(5, ph);
            ps.executeUpdate();

            ResultSet g = ps.getGeneratedKeys();
            if (g.next()) {
                int id = g.getInt(1);
                cache.add(new Client(id, ln, fn, pt, ad, ph));
                setChanged(); notifyObservers();
                return id;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public void update(int id,String ln,String fn,String pt,String ad,String ph) {
        String sql = "UPDATE clients SET last_name=?,first_name=?,patronymic=?,address=?,phone=? WHERE client_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, ln); ps.setString(2, fn); ps.setString(3, pt);
            ps.setString(4, ad); ps.setString(5, ph); ps.setInt(6, id);
            ps.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
        refresh();
    }

    public void delete(int id) {
        String sql = "DELETE FROM clients WHERE client_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }

        for (Iterator<Client> it = cache.iterator(); it.hasNext();) {
            if (it.next().getId() == id) { it.remove(); break; }
        }
        setChanged(); notifyObservers();
    }

    private void loadAll() {
        String sql = "SELECT client_id,last_name,first_name,patronymic,address,phone FROM clients";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cache.add(new Client(
                        rs.getInt("client_id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("patronymic"),
                        rs.getString("address"),
                        rs.getString("phone")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
