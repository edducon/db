package org.db.kursovoi.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.db.kursovoi.model.*;
import org.db.kursovoi.view.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AdminController {

    private final Stage     window;
    private final AdminView view;

    public AdminController(Stage owner, AdminView view) {
        this.view = view;
        this.window = new Stage();

        window.initOwner(owner);
        window.setScene(new Scene(view.getRoot()));
        window.setTitle("Админ-панель");

        initCountriesTab();
        initHotelsTab();
        initClientsTab();
        initToursTab();
        initUsersTab();

        view.getClose().setOnAction(e -> window.close());
        window.show();
    }

    private void initCountriesTab() {
        CountriesView cv = view.getCountriesView();
        loadCountries(cv);

        cv.getAdd().setOnAction(e -> {
            TextInputDialog d1 = new TextInputDialog();
            d1.setHeaderText("Название страны");
            var name = d1.showAndWait();
            if (name.isEmpty()) return;
            TextInputDialog d2 = new TextInputDialog();
            d2.setHeaderText("Климат");
            var climate = d2.showAndWait();
            if (climate.isEmpty()) return;

            Countries.get().insert(name.get(), climate.get());
            loadCountries(cv);
        });

        cv.getEdit().setOnAction(e -> {
            String sel = cv.getList().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            String[] parts = sel.split(" — ", 2);
            String oldName = parts[0], oldClimate = parts[1];

            TextInputDialog d1 = new TextInputDialog(oldName);
            d1.setHeaderText("Новое название");
            var newName = d1.showAndWait();
            if (newName.isEmpty()) return;

            TextInputDialog d2 = new TextInputDialog(oldClimate);
            d2.setHeaderText("Новый климат");
            var newClimate = d2.showAndWait();
            if (newClimate.isEmpty()) return;

            Countries.get().update(oldName, newName.get(), newClimate.get());
            loadCountries(cv);
        });

        cv.getDelete().setOnAction(e -> {
            String sel = cv.getList().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            String country = sel.split(" — ",1)[0];
            Countries.get().delete(country);
            loadCountries(cv);
        });
    }

    private void loadCountries(CountriesView cv) {
        var items = cv.getList().getItems();
        items.clear();
        try (var rs = Countries.get().selectAll()) {
            while (rs.next()) {
                String name    = rs.getString("country_name");
                String climate = rs.getString("climate_features");
                items.add(name + " — " + climate);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initHotelsTab() {
        HotelsView hv = view.getHotelsView();
        loadHotels(hv);

        hv.getAdd().setOnAction(e -> {
            TextInputDialog d1 = new TextInputDialog();
            d1.setHeaderText("Страна");
            Optional<String> country = d1.showAndWait();
            if (!country.isPresent()) return;

            TextInputDialog d2 = new TextInputDialog();
            d2.setHeaderText("Класс (количество звёзд)");
            Optional<String> stars = d2.showAndWait();
            if (!stars.isPresent()) return;

            TextInputDialog d3 = new TextInputDialog();
            d3.setHeaderText("Название отеля");
            Optional<String> name = d3.showAndWait();
            if (!name.isPresent()) return;

            Hotels.get().insert(
                    country.get(),
                    Integer.parseInt(stars.get()),
                    name.get()
            );
            loadHotels(hv);
        });

        hv.getEdit().setOnAction(e -> {
            Hotel sel = hv.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;

            TextInputDialog d1 = new TextInputDialog(sel.getCountryName());
            d1.setHeaderText("Новая страна");
            Optional<String> nc = d1.showAndWait();
            if (!nc.isPresent()) return;

            TextInputDialog d2 = new TextInputDialog(String.valueOf(sel.getStars()));
            d2.setHeaderText("Новый класс");
            Optional<String> ns = d2.showAndWait();
            if (!ns.isPresent()) return;

            TextInputDialog d3 = new TextInputDialog(sel.getName());
            d3.setHeaderText("Новое название отеля");
            Optional<String> nn = d3.showAndWait();
            if (!nn.isPresent()) return;

            Hotels.get().update(
                    sel.getId(),
                    nc.get(),
                    Integer.parseInt(ns.get()),
                    nn.get()
            );
            loadHotels(hv);
        });

        hv.getDelete().setOnAction(e -> {
            Hotel sel = hv.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            Hotels.get().delete(sel.getId());
            loadHotels(hv);
        });
    }

    private void loadHotels(HotelsView hv) {
        ObservableList<Hotel> items = FXCollections.observableArrayList();
        try (ResultSet rs = Hotels.get().selectAll()) {
            while (rs.next()) {
                items.add(new Hotel(
                        rs.getInt("hotel_id"),
                        rs.getString("country_name"),
                        rs.getInt("class"),
                        rs.getString("hotel_name")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        hv.getTable().setItems(items);
    }

    private void initClientsTab() {
        ClientsView cv = view.getClientsView();
        loadClients(cv);

        cv.getAdd().setOnAction(e -> {
            TextInputDialog d1 = new TextInputDialog();
            d1.setHeaderText("Фамилия");       Optional<String> ln = d1.showAndWait(); if (!ln.isPresent()) return;
            TextInputDialog d2 = new TextInputDialog();
            d2.setHeaderText("Имя");           Optional<String> fn = d2.showAndWait(); if (!fn.isPresent()) return;
            TextInputDialog d3 = new TextInputDialog();
            d3.setHeaderText("Отчество");      Optional<String> pt = d3.showAndWait(); if (!pt.isPresent()) return;
            TextInputDialog d4 = new TextInputDialog();
            d4.setHeaderText("Адрес");         Optional<String> ad = d4.showAndWait(); if (!ad.isPresent()) return;
            TextInputDialog d5 = new TextInputDialog();
            d5.setHeaderText("Телефон");       Optional<String> ph = d5.showAndWait(); if (!ph.isPresent()) return;

            Clients.get().insert(
                    ln.get(), fn.get(), pt.get(), ad.get(), ph.get()
            );
            loadClients(cv);
        });

        cv.getEdit().setOnAction(e -> {
            Client sel = cv.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;

            TextInputDialog d1 = new TextInputDialog(sel.getLastName());
            d1.setHeaderText("Новая фамилия");      Optional<String> ln = d1.showAndWait(); if (!ln.isPresent()) return;
            TextInputDialog d2 = new TextInputDialog(sel.getFirstName());
            d2.setHeaderText("Новое имя");          Optional<String> fn = d2.showAndWait(); if (!fn.isPresent()) return;
            TextInputDialog d3 = new TextInputDialog(sel.getPatronymic());
            d3.setHeaderText("Новое отчество");     Optional<String> pt = d3.showAndWait(); if (!pt.isPresent()) return;
            TextInputDialog d4 = new TextInputDialog(sel.getAddress());
            d4.setHeaderText("Новый адрес");        Optional<String> ad = d4.showAndWait(); if (!ad.isPresent()) return;
            TextInputDialog d5 = new TextInputDialog(sel.getPhone());
            d5.setHeaderText("Новый телефон");     Optional<String> ph = d5.showAndWait(); if (!ph.isPresent()) return;

            Clients.get().update(
                    sel.getId(),
                    ln.get(), fn.get(), pt.get(), ad.get(), ph.get()
            );
            loadClients(cv);
        });

        cv.getDelete().setOnAction(e -> {
            Client sel = cv.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            Clients.get().delete(sel.getId());
            loadClients(cv);
        });
    }

    private void loadClients(ClientsView cv) {
        ObservableList<Client> items = FXCollections.observableArrayList();
        try (ResultSet rs = Clients.get().selectAll()) {
            while (rs.next()) {
                items.add(new Client(
                        rs.getInt("client_id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("patronymic"),
                        rs.getString("address"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        cv.getTable().setItems(items);
    }

    private void initToursTab() {
        ToursView tv = view.getToursView();
        loadTours(tv);

        tv.getDelete().setOnAction(e -> {
            org.db.kursovoi.model.Tour sel = tv.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            Tours.get().delete(sel.getId());
            loadTours(tv);
        });
    }

    private void loadTours(ToursView tv) {
        ObservableList<org.db.kursovoi.model.Tour> items = FXCollections.observableArrayList();
        try (ResultSet rs = Tours.get().selectAll()) {
            while (rs.next()) {
                items.add(new org.db.kursovoi.model.Tour(
                        rs.getInt("tour_id"),
                        rs.getInt("client_id"),
                        rs.getInt("hotel_id"),
                        rs.getDouble("cost"),
                        rs.getInt("duration"),
                        rs.getDate("departure_date").toLocalDate(),
                        rs.getDate("sale_date").toLocalDate(),
                        rs.getInt("discount_percent")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tv.getTable().setItems(items);
    }

    private void initUsersTab() {
        UsersView uv = view.getUsersView();
        loadUsers(uv);

        uv.getDelete().setOnAction(e -> {
            User sel = uv.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            Users.get().delete(sel.getId());
            loadUsers(uv);
        });
    }

    private void loadUsers(UsersView uv) {
        ObservableList<User> items = FXCollections.observableArrayList();
        try (ResultSet rs = Users.get().selectAll()) {
            while (rs.next()) {
                items.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getObject("client_id")==null ? null : rs.getInt("client_id")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        uv.getTable().setItems(items);
    }
}