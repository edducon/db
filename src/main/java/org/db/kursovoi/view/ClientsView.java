package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Client;

public class ClientsView {

    private final TableView<Client> table  = new TableView<>();
    private final Button           addBtn  = new Button("Добавить");
    private final Button           editBtn = new Button("Редактировать");
    private final Button           delBtn  = new Button("Удалить");
    private final VBox             root;

    public ClientsView() {
        TableColumn<Client,Integer> idCol  = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Client,String> lnCol  = new TableColumn<>("Фамилия");
        lnCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Client,String> fnCol  = new TableColumn<>("Имя");
        fnCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Client,String> ptCol  = new TableColumn<>("Отчество");
        ptCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        TableColumn<Client,String> adCol  = new TableColumn<>("Адрес");
        adCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Client,String> phCol  = new TableColumn<>("Телефон");
        phCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        table.getColumns().addAll(idCol, lnCol, fnCol, ptCol, adCol, phCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox.setMargin(table, new Insets(0, 0, 5, 0));
        root = new VBox(10, table, addBtn, editBtn, delBtn);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()             { return root; }
    public TableView<Client> getTable() { return table; }
    public Button getAdd()              { return addBtn; }
    public Button getEdit()             { return editBtn; }
    public Button getDelete()           { return delBtn; }
}
