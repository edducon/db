package org.db.kursovoi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.kursovoi.controller.LoginController;
import org.db.kursovoi.view.LoginView;

//Точка входа в приложение
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        new LoginController(stage, loginView);

        stage.setScene(new Scene(loginView.getRoot()));
        stage.setTitle("Приложение для бронирования туров | Вход в систему");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
