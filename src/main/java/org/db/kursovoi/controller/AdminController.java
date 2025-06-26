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

public class AdminController {
    private final Stage     window;
    private final AdminView view;

    public AdminController(Stage owner) {
        view   = new AdminView();
        window = new Stage();
        window.initOwner(owner);
        Scene scene = new Scene(view.getRoot());
            scene.getStylesheets().add(
                        getClass().getResource("/app.css").toExternalForm()
                            );
            window.setScene(scene);
        window.setTitle("Админ-панель");

        initCountriesTab();
        initHotelsTab();
        initClientsTab();
        initClientsCountriesTab();
        initToursTab();

        view.getClose().setOnAction(e -> window.close());
        window.show();
    }

    private void initCountriesTab() {
        CountriesView cv = view.getCountriesView();
        loadCountries(cv);
        cv.getAdd().setOnAction(e -> {
            var d1 = new TextInputDialog(); d1.setHeaderText("Название страны");
            d1.showAndWait().ifPresent(n -> {
                var d2 = new TextInputDialog(); d2.setHeaderText("Климат");
                d2.showAndWait().ifPresent(c -> Countries.get().insert(n, c));
                loadCountries(cv);
            });
        });
        cv.getEdit().setOnAction(e -> {
            String sel = cv.getList().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            String[] parts = sel.split(" — ", 2);
            var d1 = new TextInputDialog(parts[0]); d1.setHeaderText("Новое название");
            d1.showAndWait().ifPresent(nn -> {
                var d2 = new TextInputDialog(parts[1]); d2.setHeaderText("Новый климат");
                d2.showAndWait().ifPresent(nc -> Countries.get().update(parts[0], nn, nc));
                loadCountries(cv);
            });
        });
        cv.getDelete().setOnAction(e -> {
            String sel = cv.getList().getSelectionModel().getSelectedItem();
            if (sel != null) {
                Countries.get().delete(sel.split(" — ", 2)[0]);
                loadCountries(cv);
            }
        });
    }

    private void loadCountries(CountriesView cv) {
        var items = cv.getList().getItems();
        items.clear();
        try (ResultSet rs = Countries.get().selectAll()) {
            while (rs.next()) {
                items.add(rs.getString("country_name") + " — " + rs.getString("climate_features"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initHotelsTab() {
        HotelsView hv = view.getHotelsView();
        loadHotels(hv);
        hv.getAdd().setOnAction(e -> {
            var d1 = new TextInputDialog(); d1.setHeaderText("Страна");
            d1.showAndWait().ifPresent(cn -> {
                var d2 = new TextInputDialog(); d2.setHeaderText("Класс");
                d2.showAndWait().ifPresent(st -> {
                    var d3 = new TextInputDialog(); d3.setHeaderText("Название");
                    d3.showAndWait().ifPresent(n -> {
                        Hotels.get().insert(cn, Integer.parseInt(st), n);
                        loadHotels(hv);
                    });
                });
            });
        });
        hv.getEdit().setOnAction(e -> {
            Hotel sel = hv.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            var d1 = new TextInputDialog(sel.getCountryName()); d1.setHeaderText("Новая страна");
            d1.showAndWait().ifPresent(nc -> {
                var d2 = new TextInputDialog(String.valueOf(sel.getStars())); d2.setHeaderText("Новый класс");
                d2.showAndWait().ifPresent(st -> {
                    var d3 = new TextInputDialog(sel.getName()); d3.setHeaderText("Новое название");
                    d3.showAndWait().ifPresent(n -> {
                        Hotels.get().update(sel.getId(), nc, Integer.parseInt(st), n);
                        loadHotels(hv);
                    });
                });
            });
        });
        hv.getDelete().setOnAction(e -> {
            Hotel sel = hv.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) {
                Hotels.get().delete(sel.getId());
                loadHotels(hv);
            }
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
            var d1 = new TextInputDialog(); d1.setHeaderText("Фамилия");
            d1.showAndWait().ifPresent(ln -> {
                var d2 = new TextInputDialog(); d2.setHeaderText("Имя");
                d2.showAndWait().ifPresent(fn -> {
                    var d3 = new TextInputDialog(); d3.setHeaderText("Отчество");
                    d3.showAndWait().ifPresent(pt -> {
                        var d4 = new TextInputDialog(); d4.setHeaderText("Адрес");
                        d4.showAndWait().ifPresent(ad -> {
                            var d5 = new TextInputDialog(); d5.setHeaderText("Телефон");
                            d5.showAndWait().ifPresent(ph -> {
                                Clients.get().insert(ln, fn, pt, ad, ph);
                                loadClients(cv);
                            });
                        });
                    });
                });
            });
        });
        cv.getEdit().setOnAction(e -> {
            Client sel = cv.getTable().getSelectionModel().getSelectedItem();
            if (sel == null) return;
            var d1 = new TextInputDialog(sel.getLastName()); d1.setHeaderText("Новая фамилия");
            d1.showAndWait().ifPresent(ln -> {
                var d2 = new TextInputDialog(sel.getFirstName()); d2.setHeaderText("Новое имя");
                d2.showAndWait().ifPresent(fn -> {
                    var d3 = new TextInputDialog(sel.getPatronymic()); d3.setHeaderText("Новое отчество");
                    d3.showAndWait().ifPresent(pt -> {
                        var d4 = new TextInputDialog(sel.getAddress()); d4.setHeaderText("Новый адрес");
                        d4.showAndWait().ifPresent(ad -> {
                            var d5 = new TextInputDialog(sel.getPhone()); d5.setHeaderText("Новый телефон");
                            d5.showAndWait().ifPresent(ph -> {
                                Clients.get().update(sel.getId(), ln, fn, pt, ad, ph);
                                loadClients(cv);
                            });
                        });
                    });
                });
            });
        });
        cv.getDelete().setOnAction(e -> {
            Client sel = cv.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) {
                Clients.get().delete(sel.getId());
                loadClients(cv);
            }
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

    private void initClientsCountriesTab() {
        ClientsCountriesView cv = view.getClientsCountriesView();
        loadClientsCountries(cv);
    }

    private void loadClientsCountries(ClientsCountriesView cv) {
        ObservableList<Preferences.ClientCountry> items =
                FXCollections.observableArrayList(Preferences.INSTANCE.selectAll());
        cv.getTable().setItems(items);
    }

    private void initToursTab() {
        ToursView tv = view.getToursView();
        loadTours(tv);
        tv.getDelete().setOnAction(e -> {
            var sel = tv.getTable().getSelectionModel().getSelectedItem();
            if (sel != null) {
                Tours.get().delete(sel.getId());
                loadTours(tv);
            }
        });
    }

    private void loadTours(ToursView tv) {
        ObservableList<Tour> items = FXCollections.observableArrayList();
        try (ResultSet rs = Tours.get().selectAll()) {
            while (rs.next()) {
                items.add(new Tour(
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
}