package org.db.kursovoi.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.kursovoi.model.Cart;
import org.db.kursovoi.model.CartItem;
import org.db.kursovoi.model.Tour;
import org.db.kursovoi.model.Tours;
import org.db.kursovoi.model.User;
import org.db.kursovoi.view.CartView;

import java.time.LocalDate;

public class CartController {
    private final Stage win = new Stage();

    public CartController(Stage owner, Cart cart, User user) {
        CartView view = new CartView(cart);

        win.initOwner(owner);
        win.setTitle("Корзина");
        Scene scene = new Scene(view.getRoot());
            scene.getStylesheets().add(
                        getClass().getResource("/app.css").toExternalForm()
                            );
            win.setScene(scene);
        win.show();

        view.getHomeBtn().setOnAction(e -> win.close());

        view.getDeleteBtn().setOnAction(e -> {
            int idx = cart.getItems().size() - 1;
            cart.remove(idx);
        });

        view.getOrderBtn().setOnAction(e -> {
            for (CartItem ci : cart.getItems()) {
                Tours.get().insert(new Tour(
                        0,
                        user.getClientId(),
                        ci.getHotelId(),
                        ci.getCost(),
                        ci.getDurationWeeks() * 7,
                        ci.getDepartureDate(),
                        LocalDate.now(),
                        0
                ));
            }
            cart.clear();
            win.close();
        });
    }
}
