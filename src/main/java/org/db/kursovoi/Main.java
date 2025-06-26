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

        // создаём сцену в переменной, чтобы к ней добавить стили
        Scene scene = new Scene(view.getRoot());
        // подключаем файл app.css из ресурсов
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());

        // устанавливаем сцену и показываем окно
        stage.setScene(scene);
        stage.setTitle("Вход");
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
