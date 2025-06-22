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
        this.stage = s; this.view = v;

        view.getLoginButton()   .setOnAction(new LoginHandler());
        view.getRegisterButton().setOnAction(new RegisterHandler());
    }

    private final class LoginHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            String u = view.getUsernameField().getText().trim();
            String p = view.getPasswordField().getText().trim();

            User user = Users.get().auth(u, p);

            UserView uv = new UserView();
            stage.setScene(new Scene(uv.getRoot()));
            stage.setTitle("Главная страница");

            uv.getLogoutButton().setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ev) { backToLogin(); }
            });

            if ("ADMIN".equals(user.getRole())) {
                uv.getAdminButton().setVisible(true);
                uv.getAdminButton().setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ev) {
                        AdminView av = new AdminView();
                        new AdminController(stage, av);
                    }
                });
            }

            String pref = Preferences.INSTANCE.getPreferredCountry(user.getClientId());
            new UserController(stage, uv, user, pref);
        }
    }

    private final class RegisterHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            RegistrationView rv = new RegistrationView();
            new RegistrationController(stage, rv);
            stage.setScene(new Scene(rv.getRoot()));
            stage.setTitle("Регистрация");
        }
    }

    private void backToLogin() {
        LoginView lv = new LoginView();
        new LoginController(stage, lv);
        stage.setScene(new Scene(lv.getRoot()));
        stage.setTitle("Приложение для бронирования туров | Вход в систему");
    }
}
