package org.db.kursovoi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Простая корзина - список CartItem (без Observable). */
public class Cart {

    private final List<CartItem> items = new ArrayList<CartItem>();

    public void add(CartItem it)      { items.add(it); }
    public void remove(int index)     { if (index>=0 && index<items.size()) items.remove(index); }
    public void clear()               { items.clear(); }
    public List<CartItem> getItems()  { return Collections.unmodifiableList(items); }
}
