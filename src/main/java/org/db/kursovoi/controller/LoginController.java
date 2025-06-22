package org.db.kursovoi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.*;

public class LoginController {

    private final Stage     stage;
    private final LoginView view;

    public LoginController(Stage s, LoginView v) {
        stage = s;
        view  = v;

        view.getLoginButton()   .setOnAction(new LoginHandler());
        view.getToRegisterButton().setOnAction(new SwitchToReg());   // ← исправленный вызов
    }

    /* ---------- handlers ---------- */
    private final class LoginHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {

            String u = view.getUsernameField().getText().trim();
            String p = view.getPasswordField().getText().trim();

            User user = Users.get().auth(u, p);
            if (user == null) {
                warn("Ошибка", "Неверный логин или пароль");
                view.getPasswordField().clear();
                return;
            }

            UserView main = new UserView();
            stage.setScene(new Scene(main.getRoot()));
            stage.setTitle("Каталог туров");

            main.getLogoutButton().setOnAction(new BackToLogin());

            if ("ADMIN".equals(user.getRole())) {
                main.getAdminButton().setVisible(true);
                main.getAdminButton().setOnAction(new OpenAdmin()); // ← вызов без второго аргумента
            }

            String pref = Preferences.INSTANCE
                    .getPreferredCountry(user.getClientId());

            new UserController(stage, main, user, pref);
        }
    }

    private final class SwitchToReg implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            RegistrationView rv = new RegistrationView();
            new RegistrationController(stage, rv);
            stage.setScene(new Scene(rv.getRoot()));
            stage.setTitle("Регистрация");
        }
    }

    private final class BackToLogin implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            LoginView lv = new LoginView();
            new LoginController(stage, lv);
            stage.setScene(new Scene(lv.getRoot()));
            stage.setTitle("Вход");
        }
    }

    private final class OpenAdmin implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            AdminView av = new AdminView();
            new AdminController(stage, av);    // конструктор с одним аргументом
        }
    }

    private void warn(String h, String m) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(h);
        a.setContentText(m);
        a.initOwner(stage);
        a.showAndWait();
    }
}
