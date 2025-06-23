// src/main/java/org/db/kursovoi/view/CartView.java
package org.db.kursovoi.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.db.kursovoi.model.Cart;

import java.util.Observable;
import java.util.Observer;

public class CartView implements Observer {
    private final Cart   model;
    private final Canvas canvas    = new Canvas(600, 400);
    private final Button deleteBtn = new Button("Удалить из корзины");
    private final Button orderBtn  = new Button("Оформить");
    private final Button homeBtn   = new Button("На главную");
    private final VBox   root;

    public CartView(Cart model) {
        this.model = model;
        root = new VBox(10, canvas, deleteBtn, orderBtn, homeBtn);
        root.setPadding(new Insets(20));

        redraw();
        model.addObserver(this);
    }

    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(javafx.scene.text.Font.font(14));
        model.draw(gc);
    }

    @Override
    public void update(Observable o, Object arg) {
        redraw();
    }

    public Parent getRoot()       { return root; }
    public Canvas getCanvas()     { return canvas; }
    public Button getDeleteBtn()  { return deleteBtn; }
    public Button getOrderBtn()   { return orderBtn; }
    public Button getHomeBtn()    { return homeBtn; }
}
