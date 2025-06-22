package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class AdminView {

    public final CountriesView countriesView = new CountriesView();
    public final HotelsView    hotelsView    = new HotelsView();
    public final ClientsView   clientsView   = new ClientsView();
    public final ToursView     toursView     = new ToursView();
    public final UsersView     usersView     = new UsersView();

    private final TabPane tabs = new TabPane();
    private final Button  closeBtn = new Button("Закрыть");
    private final BorderPane root  = new BorderPane();

    public AdminView() {

        tabs.getTabs().addAll(
                new Tab("Страны", countriesView.getRoot()),
                new Tab("Отели",    hotelsView.getRoot()),
                new Tab("Клиента",   clientsView.getRoot()),
                new Tab("Туры",     toursView.getRoot()),
                new Tab("Пользователи",     usersView.getRoot())
        );
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        HBox top = new HBox(10, closeBtn);
        top.setPadding(new Insets(10));

        root.setTop(top);
        root.setCenter(tabs);
        root.setPrefSize(900, 600);
    }

    public Parent getRoot()   { return root; }
    public Button getClose()  { return closeBtn; }
}
