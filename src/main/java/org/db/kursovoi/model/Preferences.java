// src/main/java/org/db/kursovoi/model/Preferences.java
package org.db.kursovoi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Синглтон для хранения и получения предпочтительной страны клиента.
 * Управляет таблицей client_countries.
 */
public final class Preferences {

    private Preferences() { }
    public static final Preferences INSTANCE = new Preferences();

    private static final String DEL =
            "DELETE FROM client_countries WHERE client_id=?";
    private static final String INS =
            "INSERT INTO client_countries(client_id, country_name) VALUES(?,?)";
    private static final String SEL =
            "SELECT country_name FROM client_countries WHERE client_id=? LIMIT 1";

    /**
     * Устанавливает предпочтительную страну для клиента.
     * Сначала удаляет старую запись, затем добавляет новую.
     */
    public void setPreferredCountry(int clientId, String country) {
        try (Connection cn = Database.get();
             PreparedStatement d = cn.prepareStatement(DEL)) {
            d.setInt(1, clientId);
            d.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection cn = Database.get();
             PreparedStatement i = cn.prepareStatement(INS)) {
            i.setInt(1, clientId);
            i.setString(2, country);
            i.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает текущую предпочтительную страну клиента или null, если её нет.
     */
    public String getPreferredCountry(int clientId) {
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(SEL)) {
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
