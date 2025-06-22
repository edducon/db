package org.db.kursovoi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.*;

public class RegistrationController {

    private final Stage            stage;
    private final RegistrationView view;

    public RegistrationController(Stage s, RegistrationView v) {
        stage = s;
        view  = v;

        view.getRegisterButton().setOnAction(new Register());
        view.getBackButton()    .setOnAction(new Back());

        view.getCountryBox().getItems().clear();
        try (java.sql.ResultSet rs = Countries.get().selectAll()) {
            while (rs.next())
                view.getCountryBox().getItems().add(rs.getString("country_name"));
        } catch (java.sql.SQLException ex) { ex.printStackTrace(); }
    }

    /* ------ handlers ------ */
    private final class Register implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){

            String u  = view.getUsernameField().getText().trim();
            String pw = view.getPasswordField().getText().trim();
            String ln = view.getLastNameField().getText().trim();
            String fn = view.getFirstNameField().getText().trim();
            String pt = view.getPatronymicField().getText().trim();
            String ad = view.getAddressField().getText().trim();
            String ph = view.getPhoneField().getText().trim();
            String cn = view.getCountryBox().getValue();

            if (u.isEmpty()||pw.isEmpty()||ln.isEmpty()
                    ||fn.isEmpty()||ad.isEmpty()||ph.isEmpty()||cn==null){
                alert("Ошибка","Заполните все поля.");
                return;
            }
            if (Users.get().exists(u)){
                alert("Ошибка","Логин занят."); return;
            }

            int cid = Clients.get().insert(ln,fn,pt,ad,ph);
            Preferences.INSTANCE.setPreferredCountry(cid,cn);
            Users.get().insert(u,pw,cid,"USER");

            alert("OK","Вы зарегистрированы");
            new Back().handle(null);
        }
    }
    private final class Back implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            LoginView lv = new LoginView();
            new LoginController(stage,lv);
            stage.setScene(new Scene(lv.getRoot()));
            stage.setTitle("Вход");
        }
    }

    private void alert(String h,String m){
        Alert a=new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(h);a.setContentText(m);a.initOwner(stage);a.showAndWait();
    }
}
