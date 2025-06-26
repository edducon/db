package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class AdminView {
    public final CountriesView        countriesView        = new CountriesView();
    public final HotelsView           hotelsView           = new HotelsView();
    public final ClientsView          clientsView          = new ClientsView();
    public final ClientsCountriesView clientsCountriesView = new ClientsCountriesView();
    public final ToursView            toursView            = new ToursView();

    private final TabPane   tabs     = new TabPane();
    private final Button    closeBtn = new Button("Закрыть");
    private final BorderPane root    = new BorderPane();

    public AdminView() {
        closeBtn.getStyleClass().add("close-button");
        tabs.getTabs().addAll(
                new Tab("Страны",          countriesView.getRoot()),
                new Tab("Отели",           hotelsView.getRoot()),
                new Tab("Клиенты",         clientsView.getRoot()),
                new Tab("Клиенты–Страны",  clientsCountriesView.getRoot()),
                new Tab("Путевки",            toursView.getRoot())
        );
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        HBox top = new HBox(10, closeBtn);
        top.setPadding(new Insets(10));

        root.setTop(top);
        root.setCenter(tabs);
        root.setPrefSize(900, 600);
    }

    public Parent getRoot()   { return root; }
    public Button  getClose() { return closeBtn; }

    public CountriesView        getCountriesView()        { return countriesView; }
    public HotelsView           getHotelsView()           { return hotelsView;    }
    public ClientsView          getClientsView()          { return clientsView;   }
    public ClientsCountriesView getClientsCountriesView() { return clientsCountriesView; }
    public ToursView            getToursView()            { return toursView;     }
}
