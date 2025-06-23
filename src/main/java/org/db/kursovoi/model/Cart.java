package org.db.kursovoi.model;

import javafx.scene.canvas.GraphicsContext;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Cart extends Observable {
    private final List<CartItem> items = new ArrayList<>();

    public void add(CartItem it) {
        items.add(it);
        setChanged(); notifyObservers();
    }

    public void remove(int idx) {
        if (idx >= 0 && idx < items.size()) {
            items.remove(idx);
            setChanged(); notifyObservers();
        }
    }

    public void clear() {
        items.clear();
        setChanged(); notifyObservers();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.fillText("Корзина", 10, 20);
        int y = 40;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (CartItem it : items) {
            String line = it.getCountryName() + " / " + it.getHotelName()
                    + " | " + it.getDurationWeeks() + " нед."
                    + " | " + it.getCost() + " руб."
                    + " | вылет: " + it.getDepartureDate().format(fmt);
            gc.fillText(line, 10, y);
            y += 20;
        }
    }
}
