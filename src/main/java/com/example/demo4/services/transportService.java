/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temtransporte file, choose Tools | Temtransportes
 * and open the temtransporte in the editor.
 */
package com.example.demo4.services;

//import com.sun.javafx.iio.ImageStorage.ImageStatut;
import com.example.demo4.entities.transport;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.demo4.entities.categorie;

import com.example.demo4.utils.MyDB;
import javafx.collections.ObservableList;

//**************//
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;



/**
 *
 * @author asus
 */
public class transportService implements ItransportService<transport> {

    Connection cnx;
    public Statement ste;
    public PreparedStatement pst;

    public transportService() {
        cnx = MyDB.getInstance().getCnx();

    }

    @Override
    public void ajoutertransport(transport e) throws SQLException {
        categorieService es = new categorieService();

        String requete = "INSERT INTO `transport` (`name`,`image`,`statut`,`updated`,`capacite`,`categories_id`) "
                + "VALUES (?,?,?,?,?,?);";
        try {
            categorie tempev = es.FetchOneev(e.getCategories_id());
            System.out.println("before" + tempev);
            es.modifiercategorie(tempev);
            int new_id = tempev.getId();
            e.setCategorie(tempev);
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            pst.setString(1, e.getName());

            pst.setString(2, e.getImage());
            pst.setString(3, e.getStatut());
            pst.setDate(4, e.getUpdated());
            pst.setInt(5, e.getCapacite());
            pst.setInt(6, e.getCategories_id());

            pst.executeUpdate();
            System.out.println("ev " + e.getName() + " added successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifiertransport(transport e) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Temtransportes.
        String req = "UPDATE transport SET name = ?,image=?,statut = ?,updated=?,capacite=?,categories_id = ? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, e.getName());

        ps.setString(2, e.getImage());
        ps.setString(3, e.getStatut());
        ps.setDate(4, e.getUpdated());
        ps.setInt(5, e.getCapacite());
        ps.setInt(6, e.getCategories_id());

        ps.setInt(7, e.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimertransport(transport e) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Temtransportes.
        String req = "delete from transport where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, e.getId());
        ps.executeUpdate();
        System.out.println("ev with id= " + e.getId() + "  is deleted successfully");
    }





    @Override
    public List<transport> recuperertransport() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Temtransportes.

        List<transport> transport = new ArrayList<>();
        String s = "select * from transport";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            transport e = new transport();
            e.setName(rs.getString("name"));

            e.setImage(rs.getString("Image"));
            e.setStatut(rs.getString("statut"));
            e.setUpdated(rs.getDate("updated"));
            e.setCapacite(rs.getInt("capacite"));
            e.setCategories_id(rs.getInt("categories_id"));


            e.setId(rs.getInt("id"));

            transport.add(e);

        }
        return transport;
    }

    public transport FetchOneev(int id) {
        transport ev = new transport();
        String requete = "SELECT * FROM `transport` where id = " + id;

        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {

                ev = new transport(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getString("statut"), rs.getDate("updated"), rs.getInt("capacite"), rs.getInt("categories_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(transportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ev;
    }

    public ObservableList<transport> Fetchevs() {
        ObservableList<transport> evs = FXCollections.observableArrayList();
        String requete = "SELECT * FROM `transport`";
        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {
                evs.add(new transport(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getString("statut"), rs.getDate("updated"), rs.getInt("capacite"), rs.getInt("categories_id")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(transportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return evs;
    }


    

    public ObservableList<transport> chercherev(String chaine) {
        String sql = "SELECT * FROM transport WHERE (name LIKE ? or statut LIKE ?  ) order by name ";
        //Connection cnx= Maconnexion.getInstance().getCnx();
        String ch = "%" + chaine + "%";
        ObservableList<transport> myList = FXCollections.observableArrayList();
        try {

            Statement ste = cnx.createStatement();
            // PreparedStatement pst = myCNX.getCnx().prepareStatement(requete6);
            PreparedStatement stee = cnx.prepareStatement(sql);
            stee.setString(1, ch);
            stee.setString(2, ch);

            ResultSet rs = stee.executeQuery();
            while (rs.next()) {
                transport e = new transport();

                e.setName(rs.getString("name"));

                e.setImage(rs.getString("Image"));
                e.setStatut(rs.getString("statut"));
                e.setUpdated(rs.getDate("updated"));
                e.setCapacite(rs.getInt("capacite"));


                e.setId(rs.getInt("id"));

                myList.add(e);
                System.out.println("ev trouvé! ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<transport> trierev()throws SQLException {
        List<transport> transport = new ArrayList<>();
        String s = "select * from transport order by name ";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            transport e = new transport();
            e.setName(rs.getString("name"));

            e.setImage(rs.getString("Image"));
            e.setStatut(rs.getString("statut"));
            e.setUpdated(rs.getDate("updated"));
            e.setCapacite(rs.getInt("capacite"));


            e.setId(rs.getInt("id"));
            transport.add(e);
        }
        return transport;
    }
   

}
