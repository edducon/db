package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ProfileView {

    private final TextField       usernameField   = new TextField();
    private final PasswordField   passwordField   = new PasswordField();
    private final TextField       lastNameField   = new TextField();
    private final TextField       firstNameField  = new TextField();
    private final TextField       patronymicField = new TextField();
    private final TextField       addressField    = new TextField();
    private final TextField       phoneField      = new TextField();
    private final ComboBox<String> countryBox     = new ComboBox<>();
    private final Button          saveButton      = new Button("Сохранить");
    private final Button          homeButton      = new Button("На главную");
    private final ListView<String> ordersList     = new ListView<>();
    private final VBox            root;

    public ProfileView() {
        usernameField.setPromptText("Логин");
        passwordField.setPromptText("Новый пароль");
        lastNameField.setPromptText("Фамилия");
        firstNameField.setPromptText("Имя");
        patronymicField.setPromptText("Отчество");
        addressField.setPromptText("Адрес");
        phoneField.setPromptText("Телефон");
        countryBox.setPromptText("Предпочитаемая страна");

        root = new VBox(10,
                usernameField,
                passwordField,
                lastNameField,
                firstNameField,
                patronymicField,
                addressField,
                phoneField,
                countryBox,
                new Label("Путевки:"),
                ordersList,
                saveButton,
                homeButton
        );
        root.setPadding(new Insets(20));
        root.setPrefSize(400, 600);
    }

    public Parent         getRoot()          { return root; }
    public TextField      getUsername()      { return usernameField; }
    public PasswordField  getPassword()      { return passwordField; }
    public TextField      getLastName()      { return lastNameField; }
    public TextField      getFirstName()     { return firstNameField; }
    public TextField      getPatronymic()    { return patronymicField; }
    public TextField      getAddress()       { return addressField; }
    public TextField      getPhone()         { return phoneField; }
    public ComboBox<String> getCountryBox()  { return countryBox; }
    public Button         getSaveButton()    { return saveButton; }
    public Button         getHomeButton()    { return homeButton; }
    public ListView<String> getOrdersList()  { return ordersList; }
}
