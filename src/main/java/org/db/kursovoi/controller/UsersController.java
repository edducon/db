package org.db.kursovoi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.UsersView;

import java.util.Observable;
import java.util.Observer;

public class UsersController implements Observer {

    private final UsersView view;

    public UsersController(UsersView v) {
        this.view = v;

        Users.get().addObserver(this);
        update(null, null);

        view.getDelete().setOnAction(new DeleteHandler());
    }

    public void update(Observable o, Object arg) {
        view.getTable().setItems(
                FXCollections.observableArrayList(Users.get().getAll()));
    }

    private final class DeleteHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            User sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) Users.get().delete(sel.getId());
        }
    }
}
