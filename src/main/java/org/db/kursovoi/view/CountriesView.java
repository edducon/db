// src/main/java/org/db/kursovoi/view/CountriesView.java
package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * View: простой список стран без TableView.
 */
public class CountriesView {
    private final ListView<String> list   = new ListView<>();
    private final Button           addBtn = new Button("Добавить");
    private final Button           editBtn= new Button("Редактировать");
    private final Button           delBtn = new Button("Удалить");
    private final VBox             root;

    public CountriesView() {
        root = new VBox(10, list, addBtn, editBtn, delBtn);
        root.setPadding(new Insets(10));
    }

    public Parent         getRoot()   { return root; }
    public ListView<String> getList() { return list; }
    public Button         getAdd()    { return addBtn; }
    public Button         getEdit()   { return editBtn; }
    public Button         getDelete() { return delBtn; }
}
