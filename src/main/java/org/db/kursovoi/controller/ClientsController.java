package org.db.kursovoi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.ClientsView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ClientsController implements Observer {

    private final ClientsView view;

    public ClientsController(ClientsView v) {
        this.view = v;

        Clients.get().addObserver(this);
        update(null, null);

        view.getAdd()   .setOnAction(new AddHandler());
        view.getEdit()  .setOnAction(new EditHandler());
        view.getDelete().setOnAction(new DeleteHandler());
    }

    public void update(Observable o, Object arg) {
        view.getTable().setItems(
                FXCollections.observableArrayList(Clients.get().getAll()));
    }

    private Dialog<Client> clientDialog(Client init) {
        Dialog<Client> dlg = new Dialog<Client>();
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField ln = new TextField(init == null ? "" : init.getLastName());
        TextField fn = new TextField(init == null ? "" : init.getFirstName());
        TextField pt = new TextField(init == null ? "" : init.getPatronymic());
        TextField ad = new TextField(init == null ? "" : init.getAddress());
        TextField ph = new TextField(init == null ? "" : init.getPhone());

        dlg.getDialogPane().setContent(new VBox(6,
                new Label("Имя:"), ln,
                new Label("Фамилия:"), fn,
                new Label("Отчество:"), pt,
                new Label("Адрес:"), ad,
                new Label("Телефон:"), ph));

        dlg.setResultConverter(new ClientConverter(ln, fn, pt, ad, ph));
        return dlg;
    }

    private static final class ClientConverter implements Callback<ButtonType, Client> {
        private final TextField ln, fn, pt, ad, ph;
        ClientConverter(TextField l, TextField f, TextField p, TextField a, TextField phn){
            ln=l; fn=f; pt=p; ad=a; ph=phn;
        }
        public Client call(ButtonType bt){
            if (bt == ButtonType.OK){
                return new Client(0, ln.getText(), fn.getText(), pt.getText(), ad.getText(), ph.getText());
            }
            return null;
        }
    }

    private final class AddHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Client c = clientDialog(null).showAndWait().orElse(null);
            if (c != null)
                Clients.get().insert(c.getLastName(), c.getFirstName(), c.getPatronymic(),
                        c.getAddress(),  c.getPhone());
        }
    }
    private final class EditHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Client sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            Client c = clientDialog(sel).showAndWait().orElse(null);
            if (c != null)
                Clients.get().update(sel.getId(),
                        c.getLastName(), c.getFirstName(), c.getPatronymic(),
                        c.getAddress(),  c.getPhone());
        }
    }
    private final class DeleteHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Client sel = view.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) Clients.get().delete(sel.getId());
        }
    }
}
