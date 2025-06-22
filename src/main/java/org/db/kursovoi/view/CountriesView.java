package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Country;

public class CountriesView {

    private final TableView<Country> table = new TableView<Country>();
    private final Button addBtn  = new Button("Добавить");
    private final Button editBtn = new Button("Редактировать");
    private final Button delBtn  = new Button("Удалить");
    private final VBox root;

    public CountriesView() {

        TableColumn<Country,String> nameCol = new TableColumn<Country,String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Country,String>("name"));

        TableColumn<Country,String> climCol = new TableColumn<Country,String>("Climate");
        climCol.setCellValueFactory(new PropertyValueFactory<Country,String>("climateFeatures"));

        table.getColumns().addAll(nameCol, climCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ToolBar bar = new ToolBar(addBtn, editBtn, delBtn);
        root = new VBox(10, table, bar);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()               { return root; }
    public TableView<Country> getTable()  { return table; }
    public Button getAdd()                { return addBtn; }
    public Button getEdit()               { return editBtn; }
    public Button getDelete()             { return delBtn; }
}
