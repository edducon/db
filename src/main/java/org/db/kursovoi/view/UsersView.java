package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.User;

public class UsersView {

    private final TableView<User> table = new TableView<User>();
    private final Button delBtn = new Button("Удалить");
    private final VBox root;

    public UsersView() {

        TableColumn<User,Integer> idCol = new TableColumn<User,Integer>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));

        TableColumn<User,String> unCol = new TableColumn<User,String>("Username");
        unCol.setCellValueFactory(new PropertyValueFactory<User,String>("username"));

        TableColumn<User,String> rlCol = new TableColumn<User,String>("Role");
        rlCol.setCellValueFactory(new PropertyValueFactory<User,String>("role"));

        table.getColumns().addAll(idCol, unCol, rlCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ToolBar bar = new ToolBar(delBtn);
        root = new VBox(10, table, bar);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()          { return root; }
    public TableView<User> getTable(){ return table; }
    public Button getDelete()        { return delBtn; }
}
