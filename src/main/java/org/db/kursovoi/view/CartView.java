package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Cart;
import org.db.kursovoi.model.CartItem;

public class CartView {

    private final ListView<String> list     = new ListView<String>();
    private final Button           deleteBtn= new Button("Удалить из корзины");
    private final Button           orderBtn = new Button("Оформить");
    private final Button           homeBtn  = new Button("На главную");
    private final VBox             root;

    public CartView(Cart cart) {
        refresh(cart);
        root = new VBox(10, list, deleteBtn, orderBtn, homeBtn);
        root.setPadding(new Insets(20));
        root.setPrefSize(420, 380);
    }

    public void refresh(Cart cart) {
        list.getItems().clear();
        for (CartItem it : cart.getItems()) {
            list.getItems().add(
                    it.getCountryName() + " / " + it.getHotelName() +
                            " — " + it.getDurationWeeks() + " недели / " + it.getCost() + "руб.");
        }
    }

    public Parent getRoot()        { return root; }
    public Button getDeleteButton(){ return deleteBtn; }
    public Button getOrderButton() { return orderBtn; }
    public Button getHomeButton()  { return homeBtn; }
    public ListView<String> getList(){ return list; }
}
