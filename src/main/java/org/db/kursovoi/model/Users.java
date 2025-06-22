package org.db.kursovoi.model;

import java.sql.*;
import java.util.*;

public final class Users extends java.util.Observable {

    private static Users INSTANCE;
    private Users(){ loadAll(); }
    public static synchronized Users get(){
        if(INSTANCE==null) INSTANCE = new Users();
        return INSTANCE;
    }

    private final List<User> cache = new ArrayList<User>();
    public List<User> getAll(){ return Collections.unmodifiableList(cache); }

    public void refresh(){
        cache.clear(); loadAll();
        setChanged(); notifyObservers();
    }

    /* ---------- util ---------- */
    public boolean exists(String username){
        for (User u : cache) if (u.getUsername().equals(username)) return true;
        return false;
    }

    /* ---------- auth ---------- */
    public User auth(String u,String p){
        String sql = "SELECT user_id,username,role,client_id FROM users WHERE username=? AND password=?";
        try (Connection cn = Database.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, u);
            ps.setString(2, p);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getObject("client_id")==null ? null : rs.getInt("client_id"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /* ---------- CRUD ---------- */
    public void insert(String un,String pw,int cid,String role){
        String sql="INSERT INTO users(username,password,role,client_id) VALUES(?,?,?,?)";
        try(Connection cn=Database.get();
            PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setString(1,un); ps.setString(2,pw);
            ps.setString(3,role); ps.setInt(4,cid);
            ps.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
        refresh();
    }

    public void updateUsername(int id,String un){
        String sql="UPDATE users SET username=? WHERE user_id=?";
        try(Connection cn=Database.get();
            PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setString(1,un); ps.setInt(2,id);
            ps.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
        refresh();
    }

    public void updatePassword(int id,String pw){
        String sql="UPDATE users SET password=? WHERE user_id=?";
        try(Connection cn=Database.get();
            PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setString(1,pw); ps.setInt(2,id);
            ps.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
    }

    public void delete(int id){
        String sql="DELETE FROM users WHERE user_id=?";
        try(Connection cn=Database.get();
            PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}

        for (Iterator<User> it = cache.iterator(); it.hasNext();) {
            if (it.next().getId() == id) { it.remove(); break; }
        }
        setChanged(); notifyObservers();
    }

    /* ---------- load cache ---------- */
    private void loadAll(){
        String sql = "SELECT user_id,username,role,client_id FROM users";
        try(Connection cn=Database.get();
            PreparedStatement ps=cn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                cache.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getObject("client_id")==null ? null : rs.getInt("client_id")));
            }
        }catch(SQLException e){e.printStackTrace();}
    }
}
