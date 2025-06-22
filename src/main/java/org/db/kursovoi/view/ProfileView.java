package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

//Личный кабинет пользователя
public class ProfileView {

    private final TextField     username   = new TextField();
    private final PasswordField password   = new PasswordField();
    private final TextField     lastName   = new TextField();
    private final TextField     firstName  = new TextField();
    private final TextField     patronymic = new TextField();
    private final TextField     address    = new TextField();
    private final TextField     phone      = new TextField();
    private final ComboBox<String> countryBox = new ComboBox<String>();
    private final Button        saveBtn   = new Button("Сохранить");
    private final Button        homeBtn   = new Button("На главную");
    private final ListView<String> orders = new ListView<String>();
    private final VBox          root;

    public ProfileView() {

        username  .setPromptText("Логин");
        password  .setPromptText("Новый пароль");
        lastName  .setPromptText("Фамилия");
        firstName .setPromptText("Имя");
        patronymic.setPromptText("Отчество");
        address   .setPromptText("Адрес");
        phone     .setPromptText("Телефон");
        countryBox.setPromptText("Предпочитаемая страна");

        root = new VBox(10,
                username, password,
                lastName, firstName, patronymic,
                address, phone, countryBox,
                new Label("Путевки:"), orders,
                saveBtn, homeBtn);
        root.setPadding(new Insets(20));
        root.setPrefSize(400, 600);
    }

    public Parent getRoot()               { return root; }
    public TextField     getUsername()    { return username; }
    public PasswordField getPassword()    { return password; }
    public TextField     getLastName()    { return lastName; }
    public TextField     getFirstName()   { return firstName; }
    public TextField     getPatronymic()  { return patronymic; }
    public TextField     getAddress()     { return address; }
    public TextField     getPhone()       { return phone; }
    public ComboBox<String> getCountryBox(){ return countryBox; }
    public Button        getSaveButton()  { return saveBtn; }
    public Button        getHomeButton()  { return homeBtn; }
    public ListView<String> getOrdersList(){ return orders; }
}
