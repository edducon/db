package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Client;

public class ClientsView {

    private final TableView<Client> table = new TableView<Client>();
    private final Button addBtn  = new Button("Добавить");
    private final Button editBtn = new Button("Редактировать");
    private final Button delBtn  = new Button("Удалить");
    private final VBox root;

    public ClientsView() {

        TableColumn<Client,Integer> idCol = new TableColumn<Client,Integer>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<Client,Integer>("id"));

        TableColumn<Client,String> lnCol = new TableColumn<Client,String>("Last");
        lnCol.setCellValueFactory(new PropertyValueFactory<Client,String>("lastName"));

        TableColumn<Client,String> fnCol = new TableColumn<Client,String>("First");
        fnCol.setCellValueFactory(new PropertyValueFactory<Client,String>("firstName"));

        TableColumn<Client,String> phCol = new TableColumn<Client,String>("Phone");
        phCol.setCellValueFactory(new PropertyValueFactory<Client,String>("phone"));

        table.getColumns().addAll(idCol, lnCol, fnCol, phCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ToolBar bar = new ToolBar(addBtn, editBtn, delBtn);
        root = new VBox(10, table, bar);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()            { return root; }
    public TableView<Client> getTable(){ return table; }
    public Button getAdd()             { return addBtn; }
    public Button getEdit()            { return editBtn; }
    public Button getDelete()          { return delBtn; }
}
