package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Tour;

public class ToursView {

    private final TableView<Tour> table = new TableView<>();
    private final Button          delBtn = new Button("Удалить");
    private final VBox            root;

    public ToursView() {
        TableColumn<Tour,Integer> idCol   = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Tour,Integer> clCol   = new TableColumn<>("Client");
        clCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        TableColumn<Tour,Integer> htCol   = new TableColumn<>("Hotel");
        htCol.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
        TableColumn<Tour,Double>  costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));

        table.getColumns().addAll(idCol, clCol, htCol, costCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root = new VBox(10, table, delBtn);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()          { return root; }
    public TableView<Tour> getTable(){ return table; }
    public Button getDelete()        { return delBtn; }
}
