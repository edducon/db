package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

//Окно входа в систему
public class LoginView {

    private final TextField     usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button        loginButton   = new Button("Войти");
    private final Button        registerButton= new Button("Зарегистрироваться");
    private final VBox          root;

    public LoginView() {
        usernameField.setPromptText("Логин");
        passwordField.setPromptText("Пароль");

        root = new VBox(10,
                usernameField,
                passwordField,
                loginButton,
                registerButton);
        root.setPadding(new Insets(20));
        root.setPrefSize(260, 180);
    }

    public Parent        getRoot()          { return root; }
    public TextField     getUsernameField() { return usernameField; }
    public PasswordField getPasswordField() { return passwordField; }
    public Button        getLoginButton()   { return loginButton; }
    public Button        getRegisterButton(){ return registerButton; }
}
