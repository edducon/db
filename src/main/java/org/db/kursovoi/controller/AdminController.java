package org.db.kursovoi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.kursovoi.view.AdminView;

public class AdminController {

    private final Stage     window = new Stage();
    private final AdminView view;

    public AdminController(Stage owner, AdminView v) {
        this.view = v;

        new CountriesController(view.countriesView);
        new HotelsController   (view.hotelsView);
        new ClientsController  (view.clientsView);
        new ToursController    (view.toursView);
        new UsersController    (view.usersView);

        view.getClose().setOnAction(new CloseHandler());

        window.initOwner(owner);
        window.setScene(new Scene(view.getRoot()));
        window.setTitle("Админ-панель");
        window.show();
    }

    private final class CloseHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) { window.close(); }
    }
}
