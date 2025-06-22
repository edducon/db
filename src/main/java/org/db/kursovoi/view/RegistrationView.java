package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/** Окно регистрации нового пользователя */
public class RegistrationView {

    private final TextField     userName   = new TextField();
    private final PasswordField password   = new PasswordField();
    private final TextField     lastName   = new TextField();
    private final TextField     firstName  = new TextField();
    private final TextField     patronymic = new TextField();
    private final TextField     address    = new TextField();
    private final TextField     phone      = new TextField();
    private final ComboBox<String> countryBox = new ComboBox<String>();
    private final Button        registerBtn = new Button("Зарегистрироваться");
    private final Button        backBtn     = new Button("Назад");
    private final VBox          root;

    public RegistrationView() {

        userName  .setPromptText("Логин");
        password  .setPromptText("Пароль");
        lastName  .setPromptText("Фамилия");
        firstName .setPromptText("Имя");
        patronymic.setPromptText("Отчество");
        address   .setPromptText("Адрес");
        phone     .setPromptText("Телефон");
        countryBox.setPromptText("Предпочитаемая страна");

        root = new VBox(10,
                userName, password,
                lastName, firstName, patronymic,
                address, phone, countryBox,
                registerBtn, backBtn);
        root.setPadding(new Insets(20));
        root.setPrefSize(300, 380);
    }


    public Parent        getRoot()        { return root; }
    public TextField     getUsernameField(){ return userName; }
    public PasswordField getPasswordField(){ return password; }
    public TextField     getLastNameField(){ return lastName; }
    public TextField     getFirstNameField(){ return firstName; }
    public TextField     getPatronymicField(){ return patronymic; }
    public TextField     getAddressField(){ return address; }
    public TextField     getPhoneField() { return phone; }
    public ComboBox<String> getCountryBox(){ return countryBox; }
    public Button        getRegisterButton(){ return registerBtn; }
    public Button        getBackButton()  { return backBtn; }
}
