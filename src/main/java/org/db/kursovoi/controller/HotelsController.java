package org.db.kursovoi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.HotelsView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class HotelsController implements Observer {

    private final HotelsView view;

    public HotelsController(HotelsView v) {
        this.view = v;

        Hotels.get().addObserver(this);
        update(null, null);

        view.getAdd()   .setOnAction(new AddHandler());
        view.getEdit()  .setOnAction(new EditHandler());
        view.getDelete().setOnAction(new DeleteHandler());
    }

    public void update(Observable o, Object arg) {
        view.getTable().setItems(
                FXCollections.observableArrayList(Hotels.get().getAll()));
    }

    private Dialog<Hotel> hotelDialog(Hotel init) {
        Dialog<Hotel> dlg = new Dialog<Hotel>();
        dlg.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK, ButtonType.CANCEL);

        TextField country = new TextField(init == null ? "" : init.getCountryName());
        TextField stars   = new TextField(init == null ? "" : String.valueOf(init.getStars()));
        TextField name    = new TextField(init == null ? "" : init.getName());

        dlg.getDialogPane().setContent(new VBox(6,
                new Label("Страна:"), country,
                new Label("Класс:"),   stars,
                new Label("Название:"),    name));

        dlg.setResultConverter(new ResultConverter(country, stars, name));
        return dlg;
    }

    private static final class ResultConverter implements Callback<ButtonType, Hotel> {
        private final TextField country, stars, name;
        ResultConverter(TextField c, TextField s, TextField n){
            country=c; stars=s; name=n;
        }
        public Hotel call(ButtonType bt){
            if (bt == ButtonType.OK){
                return new Hotel(0,
                        country.getText(),
                        Integer.parseInt(stars.getText()),
                        name.getText());
            }
            return null;
        }
    }

    private final class AddHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Dialog<Hotel> dlg = hotelDialog(null);
            Hotel h = dlg.showAndWait().orElse(null);
            if (h != null)
                Hotels.get().insert(h.getCountryName(), h.getStars(), h.getName());
        }
    }

    private final class EditHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Hotel sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            Dialog<Hotel> dlg = hotelDialog(sel);
            Hotel h = dlg.showAndWait().orElse(null);
            if (h != null)
                Hotels.get().update(sel.getId(), h.getCountryName(), h.getStars(), h.getName());
        }
    }

    private final class DeleteHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Hotel sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) Hotels.get().delete(sel.getId());
        }
    }
}
