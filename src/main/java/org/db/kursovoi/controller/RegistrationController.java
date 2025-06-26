package org.db.kursovoi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.kursovoi.model.Country;
import org.db.kursovoi.model.RegistrationModel;
import org.db.kursovoi.model.User;
import org.db.kursovoi.view.LoginView;
import org.db.kursovoi.view.RegistrationView;

public class RegistrationController {

    private final Stage            stage;
    private final RegistrationView view;
    private final RegistrationModel model = new RegistrationModel();

    public RegistrationController(Stage stage, RegistrationView view) {
        this.stage = stage;
        this.view  = view;

        view.getCountryBox().getItems().setAll(model.loadCountries());

        view.getRegisterButton().setOnAction(new Register());
        view.getBackButton()    .setOnAction(new Back());
    }

    private final class Register implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String u  = view.getUsernameField().getText().trim();
            String pw = view.getPasswordField().getText().trim();
            String ln = view.getLastNameField().getText().trim();
            String fn = view.getFirstNameField().getText().trim();
            String pt = view.getPatronymicField().getText().trim();
            String ad = view.getAddressField().getText().trim();
            String ph = view.getPhoneField().getText().trim();
            Country cn = view.getCountryBox().getValue();

            User user = model.register(u, pw, ln, fn, pt, ad, ph, cn);
            if (user != null) {

                new Back().handle(null);
            } else {
            }
        }
    }

    private final class Back implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            LoginView lv = new LoginView();
            new LoginController(stage, lv);
            Scene loginScene = new Scene(lv.getRoot());
            loginScene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
            stage.setScene(loginScene);
            stage.setTitle("Вход");
        }
    }
}
