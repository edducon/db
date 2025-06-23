package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Preferences;

public class ClientsCountriesView {
    private final TableView<Preferences.ClientCountry> table = new TableView<>();
    private final VBox root;

    public ClientsCountriesView() {
        TableColumn<Preferences.ClientCountry, Integer> clientCol =
                new TableColumn<>("Client ID");
        clientCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));

        TableColumn<Preferences.ClientCountry, String> countryCol =
                new TableColumn<>("Название страны");
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));

        table.getColumns().addAll(clientCol, countryCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root = new VBox(10, table);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot() {
        return root;
    }

    public TableView<Preferences.ClientCountry> getTable() {
        return table;
    }
}