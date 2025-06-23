// src/main/java/org/db/kursovoi/model/RegistrationModel.java
package org.db.kursovoi.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RegistrationModel {

    /** Возвращает список стран как объектов Country */
    public List<Country> loadCountries() {
        List<Country> list = new ArrayList<>();
        try (ResultSet rs = Countries.get().selectAll()) {
            while (rs.next()) {
                String name = rs.getString("country_name").trim();
                String clim = rs.getString("climate_features").trim();
                list.add(new Country(name, clim));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Делегирует всю логику регистрации */
    public User register(String username,
                         String password,
                         String lastName,
                         String firstName,
                         String patronymic,
                         String address,
                         String phone,
                         Country country) {
        if (username.isEmpty() || password.isEmpty() ||
                lastName.isEmpty() || firstName.isEmpty() ||
                address.isEmpty() || phone.isEmpty() || country == null)
            return null;
        if (Users.get().exists(username.trim()))
            return null;

        int cid = Clients.get()
                .insert(lastName.trim(), firstName.trim(),
                        patronymic.trim(), address.trim(), phone.trim());
        if (cid < 0) return null;

        Preferences.INSTANCE.setPreferredCountry(cid, country.getName());
        Users.get().insert(username.trim(), password.trim(), cid, "USER");

        return Users.get().auth(username.trim(), password.trim());
    }
}
