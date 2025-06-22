package org.db.kursovoi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.*;

import java.util.Iterator;

public class RegistrationController {

    private final Stage            stage;
    private final RegistrationView view;

    public RegistrationController(Stage s, RegistrationView v) {
        this.stage = s; this.view = v;

        view.getRegisterButton().setOnAction(new RegisterHandler());
        view.getBackButton()    .setOnAction(new BackHandler());

        for (Iterator<Country> it = Countries.get().getAll().iterator(); it.hasNext();) {
            view.getCountryBox().getItems().add(it.next().getName());
        }
    }

    private final class RegisterHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            String u  = view.getUsernameField().getText().trim();
            String pw = view.getPasswordField().getText().trim();
            String ln = view.getLastNameField().getText().trim();
            String fn = view.getFirstNameField().getText().trim();
            String pt = view.getPatronymicField().getText().trim();
            String ad = view.getAddressField().getText().trim();
            String ph = view.getPhoneField().getText().trim();
            String cn = view.getCountryBox().getValue();

            if (u.isEmpty()||pw.isEmpty()||ln.isEmpty()||fn.isEmpty()
                    ||ad.isEmpty()||ph.isEmpty()||cn==null) {
                showMsg("Ошибка", "Заполните все нужные поля"); return;
            }

            int cid = Clients.get().insert(ln, fn, pt, ad, ph);
            Preferences.INSTANCE.setPreferredCountry(cid, cn);
            Users.get().insert(u, pw, cid, "USER");

            showMsg("Успешно", "Вы зарегистрированы как " + u);
            new BackHandler().handle(null);
        }
    }

    private final class BackHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            LoginView lv = new LoginView();
            new LoginController(stage, lv);
            stage.setScene(new Scene(lv.getRoot()));
        }
    }

    private void showMsg(String h,String m){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(h); a.setContentText(m); a.initOwner(stage); a.showAndWait();
    }
}
