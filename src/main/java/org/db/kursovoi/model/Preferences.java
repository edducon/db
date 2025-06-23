// src/main/java/org/db/kursovoi/model/Preferences.java
package org.db.kursovoi.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class Preferences {
    private Preferences() {}
    public static final Preferences INSTANCE = new Preferences();

    private static final String DEL     = "DELETE FROM client_countries WHERE client_id=?";
    private static final String INS     = "INSERT INTO client_countries(client_id,country_name) VALUES(?,?)";
    private static final String SEL_ONE = "SELECT country_name FROM client_countries WHERE client_id=? LIMIT 1";
    private static final String SEL_ALL = "SELECT client_id,country_name FROM client_countries";

    public String getPreferredCountry(int clientId) {
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(SEL_ONE)) {
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void setPreferredCountry(int clientId, String country) {
        try (Connection cn = Database.get();
             PreparedStatement d = cn.prepareStatement(DEL)) {
            d.setInt(1, clientId);
            d.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        if (country != null) {
            try (Connection cn = Database.get();
                 PreparedStatement i = cn.prepareStatement(INS)) {
                i.setInt(1, clientId);
                i.setString(2, country);
                i.executeUpdate();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void delete(int clientId) {
        try (Connection cn = Database.get();
             PreparedStatement d = cn.prepareStatement(DEL)) {
            d.setInt(1, clientId);
            d.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<ClientCountry> selectAll() {
        var list = new ArrayList<ClientCountry>();
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(SEL_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ClientCountry(
                        rs.getInt("client_id"),
                        rs.getString("country_name")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** DTO для таблицы client_countries */
    public static record ClientCountry(int clientId, String countryName) {}
}
