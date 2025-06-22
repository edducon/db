package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Hotel;

public class HotelsView {

    private final TableView<Hotel> table = new TableView<Hotel>();
    private final Button addBtn  = new Button("Добавить");
    private final Button editBtn = new Button("Редактировать");
    private final Button delBtn  = new Button("Удалить");
    private final VBox root;

    public HotelsView() {

        TableColumn<Hotel,Integer> idCol = new TableColumn<Hotel,Integer>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<Hotel,Integer>("id"));

        TableColumn<Hotel,String> cnCol = new TableColumn<Hotel,String>("Country");
        cnCol.setCellValueFactory(new PropertyValueFactory<Hotel,String>("countryName"));

        TableColumn<Hotel,Integer> stCol = new TableColumn<Hotel,Integer>("★");
        stCol.setCellValueFactory(new PropertyValueFactory<Hotel,Integer>("stars"));

        TableColumn<Hotel,String> nmCol = new TableColumn<Hotel,String>("Name");
        nmCol.setCellValueFactory(new PropertyValueFactory<Hotel,String>("name"));

        table.getColumns().addAll(idCol, cnCol, stCol, nmCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ToolBar bar = new ToolBar(addBtn, editBtn, delBtn);
        root = new VBox(10, table, bar);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()            { return root; }
    public TableView<Hotel> getTable() { return table; }
    public Button getAdd()             { return addBtn; }
    public Button getEdit()            { return editBtn; }
    public Button getDelete()          { return delBtn; }
}
