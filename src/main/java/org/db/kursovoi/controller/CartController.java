package org.db.kursovoi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.CartView;

import java.time.LocalDate;
import java.util.Iterator;

public class CartController {

    private final CartView view;
    private final Cart     cart;
    private final User     user;
    private final Stage    window = new Stage();

    public CartController(Stage owner, CartView v, Cart c, User u) {
        this.view = v; this.cart = c; this.user = u;

        window.initOwner(owner);
        window.setScene(new Scene(view.getRoot()));
        window.setTitle("Корзина");
        window.show();

        view.getHomeButton()  .setOnAction(new HomeHandler());
        view.getDeleteButton().setOnAction(new DeleteHandler());
        view.getOrderButton() .setOnAction(new OrderHandler());
    }

    private final class HomeHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) { window.close(); }
    }
    private final class DeleteHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            int idx = view.getList().getSelectionModel().getSelectedIndex();
            cart.remove(idx);
            view.refresh(cart);
        }
    }
    private final class OrderHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            for (Iterator<CartItem> it = cart.getItems().iterator(); it.hasNext();) {
                CartItem ci = it.next();
                Tours.get().insert(new Tour(
                        0, user.getClientId(), ci.getHotelId(), ci.getCost(),
                        ci.getDurationWeeks()*7,
                        ci.getDepartureDate(), LocalDate.now(), 0));
            }
            cart.clear();
            Tours.get().refresh();
            window.close();
        }
    }
}
