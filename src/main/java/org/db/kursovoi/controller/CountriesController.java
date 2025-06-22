package org.db.kursovoi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.CountriesView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CountriesController implements Observer {

    private final CountriesView view;

    public CountriesController(CountriesView v) {
        this.view = v;

        Countries.get().addObserver(this);   // подписка
        update(null, null);

        view.getAdd()   .setOnAction(new AddHandler());
        view.getEdit()  .setOnAction(new EditHandler());
        view.getDelete().setOnAction(new DeleteHandler());
    }

    public void update(java.util.Observable o, Object arg) {
        List<Country> list = Countries.get().getAll();
        view.getTable().setItems(FXCollections.observableArrayList(list));
    }

    private final class AddHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            TextInputDialog d1 = new TextInputDialog();
            d1.setHeaderText("Название страны");
            if (!d1.showAndWait().isPresent()) return;

            TextInputDialog d2 = new TextInputDialog();
            d2.setHeaderText("Климатические условия");
            if (!d2.showAndWait().isPresent()) return;

            Countries.get().insert(
                    d1.getEditor().getText(),
                    d2.getEditor().getText());
        }
    }

    private final class EditHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Country sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;

            TextInputDialog d1 = new TextInputDialog(sel.getName());
            d1.setHeaderText("Новое название страны");
            if (!d1.showAndWait().isPresent()) return;

            TextInputDialog d2 = new TextInputDialog(sel.getClimateFeatures());
            d2.setHeaderText("Новые климатические условия");
            if (!d2.showAndWait().isPresent()) return;

            Countries.get().update(
                    sel.getName(),
                    d1.getEditor().getText(),
                    d2.getEditor().getText());
        }
    }

    private final class DeleteHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Country sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) Countries.get().delete(sel.getName());
        }
    }
}
