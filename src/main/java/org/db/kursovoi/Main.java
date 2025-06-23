package org.db.kursovoi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.kursovoi.view.LoginView;
import org.db.kursovoi.controller.LoginController;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        // View
        LoginView view = new LoginView();

        // Controller
        new LoginController(stage, view);

        // первичная сцена
        stage.setScene(new Scene(view.getRoot()));
        stage.setTitle("Вход");
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
