package org.db.kursovoi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.CartView;
import org.db.kursovoi.view.ProfileView;
import org.db.kursovoi.view.UserView;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public class UserController {

    private static UserController CURRENT;
    public  static UserController getCurrent() { return CURRENT; }

    private final Stage    stage;
    private final UserView view;
    private final User     user;
    private final Cart     cart = new Cart();
    private String         preferredCountry;

    public UserController(Stage s, UserView v, User u, String pref) {
        this.stage = s; this.view = v; this.user = u; this.preferredCountry = pref;
        CURRENT = this;

        view.getHomeButton()   .setOnAction(new HomeHandler());
        view.getRefreshButton().setOnAction(new RefreshHandler());
        view.getProfileButton().setOnAction(new ProfileHandler());
        view.getCartButton()   .setOnAction(new CartHandler());

        loadCatalog();
    }

    public void setPreferredCountry(String c) { preferredCountry = c; }
    public void reload() { loadCatalog(); }

    private void loadCatalog() {
        VBox box = view.getCatalogBox();
        box.getChildren().clear();

        Countries ct = Countries.get();
        Hotels    ht = Hotels.get();

        if (preferredCountry != null)
            addCountryBlock(box, preferredCountry, ct, ht);

        List<Country> all = ct.getAll();
        for (Iterator<Country> it = all.iterator(); it.hasNext();) {
            Country c = it.next();
            if (preferredCountry == null || !c.getName().equals(preferredCountry))
                addCountryBlock(box, c.getName(), ct, ht);
        }
    }

    private void addCountryBlock(VBox box, String cn,
                                 Countries ct, Hotels ht) {

        String climate = "?";
        for (Iterator<Country> it = ct.getAll().iterator(); it.hasNext();) {
            Country c = it.next();
            if (c.getName().equals(cn)) { climate = c.getClimateFeatures(); break; }
        }
        box.getChildren().add(new Label("--- " + cn + " (климат: " + climate + ") ---"));

        List<Hotel> hotels = ht.findByCountry(cn);
        for (Iterator<Hotel> it = hotels.iterator(); it.hasNext();) {
            Hotel h = it.next();
            Label  lbl = new Label(h.getName() + " (" + h.getStars() + "★)");
            Button add = new Button("В корзину");

            add.setOnAction(new AddToCartHandler(h));
            box.getChildren().add(new HBox(10, lbl, add));
        }
    }

    private final class HomeHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) { loadCatalog(); }
    }
    private final class RefreshHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) { loadCatalog(); }
    }
    private final class ProfileHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            ProfileView pv = new ProfileView();
            new ProfileController(stage, pv, user, preferredCountry);
        }
    }
    private final class CartHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            CartView cv = new CartView(cart);
            new CartController(stage, cv, cart, user);
        }
    }

    private static final class AddToCartHandler implements EventHandler<ActionEvent> {
        private final Hotel hotel;
        AddToCartHandler(Hotel h){ this.hotel = h; }
        public void handle(ActionEvent e) {
            ChoiceDialog<Integer> cd = new ChoiceDialog<Integer>(1, new Integer[]{1,2,4});
            cd.setHeaderText("Выберите длительность путевки");
            java.util.Optional<Integer> res = cd.showAndWait();
            if (res.isPresent()){
                int w = res.get().intValue();
                double cost = hotel.getStars()*w*100;
                UserController.getCurrent().cart.add(new CartItem(
                        hotel.getId(), hotel.getCountryName(), hotel.getName(),
                        cost, w, LocalDate.now().plusWeeks(1)));
            }
        }
    }
}
