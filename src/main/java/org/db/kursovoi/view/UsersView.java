// src/main/java/org/db/kursovoi/view/UsersView.java
package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.User;

public class UsersView {

    private final TableView<User> table  = new TableView<>();
    private final Button          delBtn = new Button("Удалить");
    private final VBox            root;

    public UsersView() {
        TableColumn<User,Integer> idCol  = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<User,String>  unCol  = new TableColumn<>("Username");
        unCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<User,String>  rlCol  = new TableColumn<>("Role");
        rlCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        table.getColumns().addAll(idCol, unCol, rlCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root = new VBox(10, table, delBtn);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()           { return root; }
    public TableView<User> getTable() { return table; }
    public Button getDelete()         { return delBtn; }
}
