package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserView {

    private final Button homeButton    = new Button("Главная");
    private final Button profileButton = new Button("Личный кабинет");
    private final Button cartButton    = new Button("Корзина");
    private final Button adminButton   = new Button("Админ-панель");
    private final Button logoutButton  = new Button("Выйти");
    private final Button refreshButton = new Button("Обновить");

    private final VBox       catalogBox = new VBox(10);
    private final ScrollPane scrollPane = new ScrollPane(catalogBox);
    private final BorderPane root       = new BorderPane();

    public UserView() {
        HBox top = new HBox(10,
                homeButton,
                profileButton,
                cartButton,
                adminButton,
                logoutButton
        );
        top.setPadding(new Insets(10));

        scrollPane.setFitToWidth(true);

        root.setTop(top);
        root.setCenter(scrollPane);
        root.setBottom(refreshButton);
        root.setPrefSize(650, 500);

        adminButton.setVisible(false);
    }

    public Parent getRoot()            { return root; }
    public VBox   getCatalogBox()      { return catalogBox; }
    public Button getHomeButton()      { return homeButton; }
    public Button getProfileButton()   { return profileButton; }
    public Button getCartButton()      { return cartButton; }
    public Button getAdminButton()     { return adminButton; }
    public Button getLogoutButton()    { return logoutButton; }
    public Button getRefreshButton()   { return refreshButton; }
}
