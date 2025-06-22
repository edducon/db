package org.db.kursovoi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.ToursView;

import java.util.Observable;
import java.util.Observer;

public class ToursController implements Observer {

    private final ToursView view;

    public ToursController(ToursView v) {
        this.view = v;

        Tours.get().addObserver(this);
        update(null, null);

        view.getDelete().setOnAction(new DeleteHandler());
    }

    public void update(Observable o, Object arg) {
        view.getTable().setItems(
                FXCollections.observableArrayList(Tours.get().getAll()));
    }

    private final class DeleteHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Tour sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) Tours.get().delete(sel.getId());
        }
    }
}
