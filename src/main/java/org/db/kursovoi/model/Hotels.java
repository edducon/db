package org.db.kursovoi.model;

import java.sql.*;
import java.util.*;

public final class Hotels extends Observable {

    private static Hotels INSTANCE;
    private Hotels() { loadAll(); }
    public static synchronized Hotels get() {
        if (INSTANCE == null) INSTANCE = new Hotels();
        return INSTANCE;
    }

    private final List<Hotel> cache = new ArrayList<Hotel>();
    public List<Hotel> getAll() { return Collections.unmodifiableList(cache); }

    public List<Hotel> findByCountry(String c){
        List<Hotel> res = new ArrayList<Hotel>();
        for (Hotel h : cache) if (h.getCountryName().equals(c)) res.add(h);
        return res;
    }

    public void refresh(){ cache.clear(); loadAll(); setChanged(); notifyObservers(); }

    public void insert(String country,int stars,String name){
        String sql="INSERT INTO hotels(country_name,class,hotel_name) VALUES(?,?,?)";
        try(Connection cn=Database.get();
            PreparedStatement ps=cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,country); ps.setInt(2,stars); ps.setString(3,name);
            ps.executeUpdate();
            ResultSet g = ps.getGeneratedKeys();
            if(g.next()) cache.add(new Hotel(g.getInt(1),country,stars,name));
            setChanged(); notifyObservers();
        }catch(SQLException e){e.printStackTrace();}
    }

    public void update(int id,String country,int stars,String name){
        String sql="UPDATE hotels SET country_name=?, class=?, hotel_name=? WHERE hotel_id=?";
        try(Connection cn=Database.get();
            PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setString(1,country); ps.setInt(2,stars); ps.setString(3,name); ps.setInt(4,id);
            ps.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
        refresh();
    }

    public void delete(int id){
        String sql="DELETE FROM hotels WHERE hotel_id=?";
        try(Connection cn=Database.get();
            PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setInt(1,id); ps.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
        for (Iterator<Hotel> it=cache.iterator(); it.hasNext();) {
            if(it.next().getId()==id){ it.remove(); break; }
        }
        setChanged(); notifyObservers();
    }

    private void loadAll(){
        String sql="SELECT hotel_id,country_name,class,hotel_name FROM hotels";
        try(Connection cn=Database.get();
            PreparedStatement ps=cn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                cache.add(new Hotel(
                        rs.getInt("hotel_id"),
                        rs.getString("country_name"),
                        rs.getInt("class"),
                        rs.getString("hotel_name")));
            }
        }catch(SQLException e){e.printStackTrace();}
    }
}
