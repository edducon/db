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
        // ID
        TableColumn<Tour,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        // Client
        TableColumn<Tour,Integer> clCol = new TableColumn<>("Client ID");
        clCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        // Hotel
        TableColumn<Tour,Integer> htCol = new TableColumn<>("Hotel ID");
        htCol.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
        // Cost
        TableColumn<Tour,Double> costCol = new TableColumn<>("Цена");
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        // Duration
        TableColumn<Tour,Integer> durCol = new TableColumn<>("Продолжительность");
        durCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        // Departure date
        TableColumn<Tour,java.time.LocalDate> depCol = new TableColumn<>("Дата отправления");
        depCol.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        // Sale date
        TableColumn<Tour,java.time.LocalDate> saleCol = new TableColumn<>("Дата продажи");
        saleCol.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        // Discount percent
        TableColumn<Tour,Integer> discCol = new TableColumn<>("Скидка (%)");
        discCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));

        table.getColumns().addAll(
                idCol, clCol, htCol, costCol,
                durCol, depCol, saleCol, discCol
        );
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root = new VBox(10, table, delBtn);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()            { return root; }
    public TableView<Tour> getTable()  { return table; }
    public Button getDelete()          { return delBtn; }
}
