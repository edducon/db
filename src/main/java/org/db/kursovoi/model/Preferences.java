package org.db.kursovoi.model;

import java.sql.*;

public final class Preferences {

    private Preferences() { }
    public static final Preferences INSTANCE = new Preferences();

    private static final String DEL = "DELETE FROM client_countries WHERE client_id=?";
    private static final String INS = "INSERT INTO client_countries(client_id, country_name) VALUES(?,?)";
    private static final String SEL = "SELECT country_name FROM client_countries WHERE client_id=? LIMIT 1";

    public void setPreferredCountry(int clientId, String country) {
        try (Connection cn = Database.get();
             PreparedStatement d = cn.prepareStatement(DEL)) {
            d.setInt(1, clientId);   // чистим старое
            d.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }

        try (Connection cn = Database.get();
             PreparedStatement i = cn.prepareStatement(INS)) {
            i.setInt(1, clientId);
            i.setString(2, country);
            i.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public String getPreferredCountry(int clientId) {
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(SEL)) {
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
