package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Tour;

public class ToursView {

    private final TableView<Tour> table = new TableView<Tour>();
    private final Button delBtn = new Button("Удалить");
    private final VBox root;

    public ToursView() {

        TableColumn<Tour,Integer> idCol = new TableColumn<Tour,Integer>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<Tour,Integer>("id"));

        TableColumn<Tour,Integer> clCol = new TableColumn<Tour,Integer>("Client");
        clCol.setCellValueFactory(new PropertyValueFactory<Tour,Integer>("clientId"));

        TableColumn<Tour,Integer> htCol = new TableColumn<Tour,Integer>("Hotel");
        htCol.setCellValueFactory(new PropertyValueFactory<Tour,Integer>("hotelId"));

        TableColumn<Tour,Double> costCol = new TableColumn<Tour,Double>("Cost");
        costCol.setCellValueFactory(new PropertyValueFactory<Tour,Double>("cost"));

        table.getColumns().addAll(idCol, clCol, htCol, costCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ToolBar bar = new ToolBar(delBtn);
        root = new VBox(10, table, bar);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()          { return root; }
    public TableView<Tour> getTable(){ return table; }
    public Button getDelete()        { return delBtn; }
}
