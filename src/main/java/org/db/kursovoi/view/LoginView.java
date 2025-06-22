// src/main/java/org/db/kursovoi/view/LoginView.java
package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView {
    private final TextField    usernameField    = new TextField();
    private final PasswordField passwordField    = new PasswordField();
    private final Button       loginButton      = new Button("Войти");
    private final Button       toRegisterButton = new Button("Регистрация");
    private final VBox         root             = new VBox(10,
            usernameField, passwordField, loginButton, toRegisterButton
    );

    public LoginView() {
        usernameField.setPromptText("Логин");
        passwordField.setPromptText("Пароль");
        root.setPadding(new Insets(20));
        root.setPrefSize(300, 180);
    }

    public Parent      getRoot()               { return root; }
    public TextField   getUsernameField()      { return usernameField; }
    public PasswordField getPasswordField()    { return passwordField; }
    public Button      getLoginButton()        { return loginButton; }
    public Button      getToRegisterButton()   { return toRegisterButton; }
}
