package org.db.kursovoi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.*;

public class LoginController {

    private final LoginView view;

    public LoginController(LoginView v) {
        this.view = v;

        view.getLoginButton()   .setOnAction(new LoginHandler());
    }

    private final class LoginHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            String u = view.getUsernameField().getText().trim();
            String p = view.getPasswordField().getText().trim();

            User user = Users.get().auth(u, p);
            if (user == null) {
                view.getPasswordField().clear();
                showMsg("Ошибка", "Заполните логин и пароль верно!");
                return;
            }
        }
    }


    private void showMsg(String h,String m){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(h); a.setContentText(m); a.showAndWait();
    }
}
