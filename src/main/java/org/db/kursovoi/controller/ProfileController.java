package org.db.kursovoi.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.ProfileView;

import java.util.Iterator;
import java.util.List;

public class ProfileController {

    private final ProfileView view;
    private final User        user;
    private final Stage       window = new Stage();
    private String            initialCountry;

    public ProfileController(Stage owner, ProfileView v, User u, String pref) {
        this.view = v; this.user = u; this.initialCountry = pref;

        List<Country> list = Countries.get().getAll();
        for (Iterator<Country> it = list.iterator(); it.hasNext();) {
            view.getCountryBox().getItems().add(it.next().getName());
        }

        loadData();

        view.getSaveButton().setOnAction(new SaveHandler());
        view.getHomeButton().setOnAction(new HomeHandler());

        window.initOwner(owner);
        window.setScene(new Scene(view.getRoot()));
        window.setTitle("Личный кабинет");
        window.show();
    }

    private void loadData() {
        Client c = Clients.get().find(user.getClientId());

        view.getUsername()  .setText(user.getUsername());
        view.getPassword()  .clear();
        view.getLastName()  .setText(c.getLastName());
        view.getFirstName() .setText(c.getFirstName());
        view.getPatronymic().setText(c.getPatronymic());
        view.getAddress()   .setText(c.getAddress());
        view.getPhone()     .setText(c.getPhone());
        view.getCountryBox().setValue(initialCountry);

        view.getOrdersList().getItems().clear();
        for (Iterator<Tour> it = Tours.get().getAll().iterator(); it.hasNext();) {
            Tour t = it.next();
            if (t.getClientId() == user.getClientId()) {
                view.getOrdersList().getItems().add(
                        "Путевка " + t.getId() + " | Отель " + t.getHotelId() +
                                " | " + t.getCost() +"руб." + " | " + t.getDepartureDate());
            }
        }
    }

    private final class SaveHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            String newUser = view.getUsername().getText().trim();
            if (!newUser.isEmpty() && !newUser.equals(user.getUsername())) {
                if (Users.get().exists(newUser)) {
                    showAlert("Ошибка", "Логин занят"); return;
                }
                Users.get().updateUsername(user.getId(), newUser);
            }
            String newPass = view.getPassword().getText().trim();
            if (!newPass.isEmpty()) {
                Users.get().updatePassword(user.getId(), newPass);
            }
            Clients.get().update(user.getClientId(),
                    view.getLastName().getText().trim(),
                    view.getFirstName().getText().trim(),
                    view.getPatronymic().getText().trim(),
                    view.getAddress().getText().trim(),
                    view.getPhone().getText().trim());

            String c = view.getCountryBox().getValue();
            if (c != null) {
                Preferences.INSTANCE.setPreferredCountry(user.getClientId(), c);
                UserController uc = UserController.getCurrent();
                uc.setPreferredCountry(c);
                Platform.runLater(new Runnable() { public void run() { uc.reload(); }});
            }

            Users.get().refresh(); Clients.get().refresh();
            showAlert("Успешно", "Данные изменены");
        }
    }

    private final class HomeHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) { window.close(); }
    }

    private void showAlert(String h, String m) {
        javafx.scene.control.Alert a =
                new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        a.setHeaderText(h); a.setContentText(m); a.initOwner(window); a.showAndWait();
    }
}
