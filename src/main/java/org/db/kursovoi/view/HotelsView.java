package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Hotel;

public class HotelsView {

    private final TableView<Hotel> table  = new TableView<>();
    private final Button          addBtn  = new Button("Добавить");
    private final Button          editBtn = new Button("Редактировать");
    private final Button          delBtn  = new Button("Удалить");
    private final VBox            root;

    public HotelsView() {
        TableColumn<Hotel,Integer> idCol  = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Hotel,String> cnCol   = new TableColumn<>("Country");
        cnCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        TableColumn<Hotel,Integer> stCol  = new TableColumn<>("★");
        stCol.setCellValueFactory(new PropertyValueFactory<>("stars"));
        TableColumn<Hotel,String> nmCol   = new TableColumn<>("Name");
        nmCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        table.getColumns().addAll(idCol, cnCol, stCol, nmCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root = new VBox(10, table, addBtn, editBtn, delBtn);
        root.setPadding(new Insets(10));
    }

    public Parent getRoot()            { return root; }
    public TableView<Hotel> getTable() { return table; }
    public Button getAdd()             { return addBtn; }
    public Button getEdit()            { return editBtn; }
    public Button getDelete()          { return delBtn; }
}
