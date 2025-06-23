package org.db.kursovoi.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.kursovoi.model.Client;
import org.db.kursovoi.model.Clients;
import org.db.kursovoi.model.Countries;
import org.db.kursovoi.model.Preferences;
import org.db.kursovoi.model.Tours;
import org.db.kursovoi.model.User;
import org.db.kursovoi.model.Users;
import org.db.kursovoi.view.ProfileView;

import java.sql.SQLException;


public class ProfileController {
    private final Stage win   = new Stage();
    private final ProfileView view;
    private final User user;
    private final String prefCountry;

    public ProfileController(Stage owner, ProfileView v, User u, String pref) {
        view = v; user = u; prefCountry = pref;

        fillCountryBox();
        loadData();

        view.getSaveButton().setOnAction(new Save());
        view.getHomeButton().setOnAction(e -> win.close());

        win.initOwner(owner);
        win.setScene(new Scene(view.getRoot()));
        win.setTitle("Профиль");
        win.show();
    }

    private void fillCountryBox() {
        try (var rs = Countries.get().selectAll()) {
            while (rs.next())
                view.getCountryBox().getItems()
                        .add(rs.getString("country_name"));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadData() {
        Client c = Clients.get().find(user.getClientId());

        view.getUsername() .setText(user.getUsername());
        view.getPassword() .clear();
        view.getLastName() .setText(c.getLastName());
        view.getFirstName().setText(c.getFirstName());
        view.getPatronymic().setText(c.getPatronymic());
        view.getAddress()  .setText(c.getAddress());
        view.getPhone()    .setText(c.getPhone());
        view.getCountryBox().setValue(prefCountry);

        view.getOrdersList().getItems().clear();
        try (var rs = Tours.get().selectByClient(user.getClientId())) {
            while (rs.next()) {
                view.getOrdersList().getItems().add(
                        "Тур " + rs.getInt("tour_id") +
                                " | отель " + rs.getInt("hotel_id") +
                                " | "      + rs.getDouble("cost") + " руб." +
                                " | "      + rs.getDate("departure_date")
                );
            }
        } catch (SQLException e){ e.printStackTrace(); }
    }

    private class Save implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String newU = view.getUsername().getText().trim();
            if (!newU.isEmpty() && !newU.equals(user.getUsername())) {
                Users.get().updateUsername(user.getId(),newU);
            }

            String newP = view.getPassword().getText().trim();
            if (!newP.isEmpty())
                Users.get().updatePassword(user.getId(),newP);

            Clients.get().update(user.getClientId(),
                    view.getLastName() .getText().trim(),
                    view.getFirstName().getText().trim(),
                    view.getPatronymic().getText().trim(),
                    view.getAddress()  .getText().trim(),
                    view.getPhone()    .getText().trim());
            String c = view.getCountryBox().getValue();
            if (c != null) {
                Preferences.INSTANCE.setPreferredCountry(user.getClientId(), c);
                UserController uc = UserController.getCurrent();
                uc.setPreferredCountry(c);
                Platform.runLater(uc::reload);
            }
        }
    }
}
