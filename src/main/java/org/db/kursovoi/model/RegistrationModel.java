// src/main/java/org/db/kursovoi/model/RegistrationModel.java
package org.db.kursovoi.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель для экрана регистрации.
 */
public class RegistrationModel {

    /**
     * Регистрирует нового пользователя.
     * @return созданный User или null при ошибке.
     */
    public User register(String username,
                         String password,
                         String lastName,
                         String firstName,
                         String patronymic,
                         String address,
                         String phone,
                         String country)
    {
        if (username.isEmpty()||password.isEmpty()||
                lastName.isEmpty()||firstName.isEmpty()||
                address.isEmpty()||phone.isEmpty()||country==null) {
            return null;
        }
        if (Users.get().exists(username.trim())) {
            return null;
        }

        // 1) создаём клиента
        int cid = Clients.get()
                .insert(lastName.trim(), firstName.trim(),
                        patronymic.trim(), address.trim(), phone.trim());
        if (cid < 0) return null;

        // 2) сохраняем предпочитаемую страну
        Preferences.INSTANCE.setPreferredCountry(cid, country);

        // 3) создаём пользователя
        Users.get().insert(username.trim(), password.trim(), cid, "USER");

        // 4) возвращаем только что созданного
        return Users.get().auth(username.trim(), password.trim());
    }

    /**
     * Загружает список всех стран из БД для ComboBox.
     */
    public List<String> loadCountries() {
        List<String> list = new ArrayList<>();
        try (ResultSet rs = Countries.get().selectAll()) {
            while (rs.next()) {
                String c = rs.getString("country_name").trim();
                if (!list.contains(c)) list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
