package org.db.kursovoi.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public final class Tours extends java.util.Observable {

    private static Tours INSTANCE;
    private Tours(){ loadAll(); }
    public static synchronized Tours get(){
        if(INSTANCE==null) INSTANCE = new Tours();
        return INSTANCE;
    }

    private final List<Tour> cache = new ArrayList<Tour>();
    public List<Tour> getAll(){ return Collections.unmodifiableList(cache); }

    public void refresh(){
        cache.clear(); loadAll();
        setChanged(); notifyObservers();
    }

    public void insert(Tour t){
        String sql = "INSERT INTO tours(client_id,hotel_id,cost,duration,departure_date,sale_date,discount_percent) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt   (1, t.getClientId());
            ps.setInt   (2, t.getHotelId());
            ps.setDouble(3, t.getCost());
            ps.setInt   (4, t.getDuration());
            ps.setDate  (5, Date.valueOf(t.getDepartureDate()));
            ps.setDate  (6, Date.valueOf(t.getSaleDate()));
            ps.setInt   (7, t.getDiscountPercent());
            ps.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
        refresh();
    }

    public void delete(int id){
        String sql = "DELETE FROM tours WHERE tour_id=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }

        for (Iterator<Tour> it = cache.iterator(); it.hasNext();) {
            if (it.next().getId() == id) { it.remove(); break; }
        }
        setChanged(); notifyObservers();
    }

    private void loadAll(){
        String sql = "SELECT tour_id,client_id,hotel_id,cost,duration,departure_date,sale_date,discount_percent FROM tours";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cache.add(new Tour(
                        rs.getInt("tour_id"),
                        rs.getInt("client_id"),
                        rs.getInt("hotel_id"),
                        rs.getDouble("cost"),
                        rs.getInt("duration"),
                        rs.getDate("departure_date").toLocalDate(),
                        rs.getDate("sale_date").toLocalDate(),
                        rs.getInt("discount_percent")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
