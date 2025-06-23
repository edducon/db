package org.db.kursovoi.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import org.db.kursovoi.model.Cart;
import org.db.kursovoi.model.CartItem;
import org.db.kursovoi.model.Countries;
import org.db.kursovoi.model.Hotels;
import org.db.kursovoi.model.User;
import org.db.kursovoi.view.UserView;

import java.time.LocalDate;

public class UserController {
    private static UserController CURRENT;
    public static UserController getCurrent() { return CURRENT; }

    private final Stage stage;
    private final UserView view;
    private final User user;
    private final Cart cart = new Cart();
    private       String prefCountry;

    public UserController(Stage stage, UserView view, User user, String pref) {
        this.stage       = stage;
        this.view        = view;
        this.user        = user;
        this.prefCountry = pref;
        CURRENT = this;

        view.getHomeButton()   .setOnAction(e -> reload());
        view.getRefreshButton().setOnAction(e -> reload());
        view.getProfileButton().setOnAction(e ->
                new ProfileController(stage, new org.db.kursovoi.view.ProfileView(), user, prefCountry)
        );
        view.getCartButton().setOnAction(e ->
                new CartController(stage, cart, user)
        );

        reload();
    }

    public void setPreferredCountry(String c) { prefCountry = c; }
    public void reload()                     { loadCatalog(); }

    private void loadCatalog() {
        var box = view.getCatalogBox();
        box.getChildren().clear();

        if (prefCountry != null) addCountryBlock(prefCountry, box);

        try (var rs = Countries.get().selectAll()) {
            while (rs.next()) {
                String cn = rs.getString("country_name");
                if (prefCountry == null || !cn.equals(prefCountry))
                    addCountryBlock(cn, box);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void addCountryBlock(String cn, javafx.scene.layout.VBox box) {
        String clim = "?";
        try (var rs = Countries.get().selectByName(cn)) {
            if (rs.next()) clim = rs.getString("climate_features");
        } catch (Exception e) { e.printStackTrace(); }

        box.getChildren().add(new javafx.scene.control.Label(
                "--- "+cn+" (климат: "+clim+") ---"
        ));

        try (var rs = Hotels.get().selectByCountry(cn)) {
            while (rs.next()) {
                int hotelId = rs.getInt("hotel_id");
                String name = rs.getString("hotel_name");
                int stars   = rs.getInt("class");

                var lbl = new javafx.scene.control.Label(name + " ("+stars+"★)");
                var add = new Button("В корзину");
                add.setOnAction(ev -> {
                    var dialog = new ChoiceDialog<>(1, 1,2,4);
                    dialog.setHeaderText("Длительность (нед.)");
                    dialog.showAndWait().ifPresent(w -> {
                        double cost = stars * w * 100.0;
                        cart.add(new CartItem(
                                hotelId, cn, name,
                                cost, w,
                                LocalDate.now().plusWeeks(1)
                        ));
                    });
                });

                box.getChildren().add(new HBox(10, lbl, add));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
