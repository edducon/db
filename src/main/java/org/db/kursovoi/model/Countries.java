package org.db.kursovoi.model;

import java.sql.*;
import java.util.*;

public final class Countries extends Observable {

    private static Countries INSTANCE;
    private Countries() { loadAll(); }
    public static synchronized Countries get() {
        if (INSTANCE == null) INSTANCE = new Countries();
        return INSTANCE;
    }

    private final List<Country> cache = new ArrayList<Country>();
    public List<Country> getAll() { return Collections.unmodifiableList(cache); }

    public void refresh() {
        cache.clear(); loadAll();
        setChanged(); notifyObservers();
    }

    public void insert(String name, String climate) {
        String sql = "INSERT INTO countries(country_name, climate_features) VALUES (?,?)";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, climate);
            ps.executeUpdate();
            cache.add(new Country(name, climate));
            setChanged(); notifyObservers();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void update(String oldName, String newName, String newClimate) {
        String sql = "UPDATE countries SET country_name=?, climate_features=? WHERE country_name=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setString(2, newClimate);
            ps.setString(3, oldName);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        refresh();
    }

    public void delete(String name) {
        String sql = "DELETE FROM countries WHERE country_name=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }

        for (Iterator<Country> it = cache.iterator(); it.hasNext();) {
            if (it.next().getName().equals(name)) { it.remove(); break; }
        }
        setChanged(); notifyObservers();
    }

    private void loadAll() {
        String sql = "SELECT country_name, climate_features FROM countries";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cache.add(new Country(
                        rs.getString("country_name"),
                        rs.getString("climate_features")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
