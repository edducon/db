package org.db.kursovoi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.kursovoi.model.LoginModel;
import org.db.kursovoi.model.Preferences;
import org.db.kursovoi.model.User;
import org.db.kursovoi.view.AdminView;
import org.db.kursovoi.view.LoginView;
import org.db.kursovoi.view.RegistrationView;
import org.db.kursovoi.view.UserView;

public class LoginController {

    private final Stage     stage;
    private final LoginView view;
    private final LoginModel model = new LoginModel();

    public LoginController(Stage stage, LoginView view) {
        this.stage = stage;
        this.view  = view;

        view.getLoginButton()    .setOnAction(new LoginHandler());
        view.getToRegisterButton().setOnAction(e -> {
            RegistrationView rv = new RegistrationView();
            new RegistrationController(stage, rv);
            stage.setScene(new Scene(rv.getRoot()));
            stage.setTitle("Регистрация");
        });
    }

    private final class LoginHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String u = view.getUsernameField().getText().trim();
            String p = view.getPasswordField().getText().trim();

            User user = model.login(u, p);
            if (user == null) {
                return;
            }

            // открываем каталог
            UserView main = new UserView();
            stage.setScene(new Scene(main.getRoot()));
            stage.setTitle("Каталог туров");

            main.getLogoutButton().setOnAction(ev -> {
                LoginView lv = new LoginView();
                new LoginController(stage, lv);
                stage.setScene(new Scene(lv.getRoot()));
                stage.setTitle("Вход");
            });

            if ("ADMIN".equals(user.getRole())) {
                main.getAdminButton().setVisible(true);
                main.getAdminButton().setOnAction(ev -> {
                    AdminView av = new AdminView();
                    new AdminController(stage);
                });
            }

            String pref = Preferences.INSTANCE.getPreferredCountry(user.getClientId());
            new UserController(stage, main, user, pref);
        }
    }
}
