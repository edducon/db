package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserView {

    private final Button homeBtn    = new Button("Главная");
    private final Button profileBtn = new Button("Личный кабинет");
    private final Button cartBtn    = new Button("Корзина");
    private final Button adminBtn   = new Button("Админ-панель");
    private final Button logoutBtn  = new Button("Выйти");
    private final Button refreshBtn = new Button("Обновить");

    private final VBox       catalogBox = new VBox(10);
    private final ScrollPane scroll     = new ScrollPane(catalogBox);
    private final BorderPane root       = new BorderPane();

    public UserView() {
        HBox top = new HBox(10, homeBtn, profileBtn, cartBtn, adminBtn, logoutBtn);
        top.setPadding(new Insets(10));

        scroll.setFitToWidth(true);

        root.setTop(top);
        root.setCenter(scroll);
        root.setBottom(refreshBtn);
        root.setPrefSize(650, 500);

        adminBtn.setVisible(false);
    }

    public Parent getRoot()         { return root; }
    public VBox   getCatalogBox()   { return catalogBox; }
    public Button getHomeButton()   { return homeBtn; }
    public Button getProfileButton(){ return profileBtn; }
    public Button getCartButton()   { return cartBtn; }
    public Button getAdminButton()  { return adminBtn; }
    public Button getLogoutButton() { return logoutBtn; }
    public Button getRefreshButton(){ return refreshBtn; }
}
