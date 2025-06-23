// src/main/java/org/db/kursovoi/view/RegistrationView.java
package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.db.kursovoi.model.Country;

public class RegistrationView {
    private final TextField        usernameField    = new TextField();
    private final PasswordField    passwordField    = new PasswordField();
    private final TextField        lastNameField    = new TextField();
    private final TextField        firstNameField   = new TextField();
    private final TextField        patronymicField  = new TextField();
    private final TextField        addressField     = new TextField();
    private final TextField        phoneField       = new TextField();
    private final ComboBox<Country> countryBox      = new ComboBox<>();
    private final Button           registerButton   = new Button("Зарегистрироваться");
    private final Button           backButton       = new Button("Назад");
    private final GridPane         root             = new GridPane();

    public RegistrationView() {
        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(20));

        root.add(new Label("Логин:"),      0, 0); root.add(usernameField,  1, 0);
        root.add(new Label("Пароль:"),     0, 1); root.add(passwordField,  1, 1);
        root.add(new Label("Фамилия:"),    0, 2); root.add(lastNameField,  1, 2);
        root.add(new Label("Имя:"),        0, 3); root.add(firstNameField, 1, 3);
        root.add(new Label("Отчество:"),   0, 4); root.add(patronymicField,1, 4);
        root.add(new Label("Адрес:"),      0, 5); root.add(addressField,   1, 5);
        root.add(new Label("Телефон:"),    0, 6); root.add(phoneField,     1, 6);
        root.add(new Label("Страна:"),     0, 7); root.add(countryBox,     1, 7);

        root.add(registerButton,           1, 8);
        root.add(backButton,               1, 9);

        countryBox.setPromptText("Выберите страну");
    }

    public Parent             getRoot()             { return root; }
    public TextField          getUsernameField()    { return usernameField; }
    public PasswordField      getPasswordField()    { return passwordField; }
    public TextField          getLastNameField()    { return lastNameField; }
    public TextField          getFirstNameField()   { return firstNameField; }
    public TextField          getPatronymicField()  { return patronymicField; }
    public TextField          getAddressField()     { return addressField; }
    public TextField          getPhoneField()       { return phoneField; }
    public ComboBox<Country>  getCountryBox()       { return countryBox; }
    public Button             getRegisterButton()   { return registerButton; }
    public Button             getBackButton()       { return backButton; }
}
