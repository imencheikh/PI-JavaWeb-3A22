/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temtransporte file, choose Tools | Temtransportes
 * and open the temtransporte in the editor.
 */
package com.example.demo4.services;

import com.example.demo4.entities.trajet;
import com.example.demo4.entities.transport;
import com.example.demo4.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.demo4.utils.MyDB;

/**
 *
 * @author asus
 */
public class trajetService {

    Connection cnx;
    public Statement ste;
    public PreparedStatement pst;

    public trajetService() {

        cnx = MyDB.getInstance().getCnx();
    }

    public void ajoutertrajet(trajet p) {
        User U = new User();
        transportService es = new transportService();
        String requete = "INSERT INTO `trajet` (`created`,`transports_id` ,`id_user`) VALUES(?,?,?) ;";

        try {
            transport tempev = es.FetchOneev(p.getTransports_id());
            System.out.println("before" + tempev);
            es.modifiertransport(tempev);
            int new_id = tempev.getId();
            p.setTransport(tempev);
            System.out.println("after" + tempev);

            pst = (PreparedStatement) cnx.prepareStatement(requete);
            pst.setDate(1, p.getCreated());
            pst.setInt(2, p.getTransports_id());
            pst.setInt(3, p.getId_user());

            pst.executeUpdate();
          

            System.out.println("trajet with id ev = " + p.getTransports_id() + " is added successfully");

        } catch (SQLException ex) {
            System.out.println("error in adding trajet");
            Logger.getLogger(trajetService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<trajet> recupererTrajet() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Temtransportes.
        trajet dernierCommentaire = null;
        List<trajet> particip = new ArrayList<>();
        String s = "SELECT * FROM trajet WHERE id = (SELECT MAX(id) FROM trajet)";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            trajet pa = new trajet();
            pa.setId(rs.getInt("id"));
            pa.setId_user(rs.getInt("id_user"));
            pa.setTransports_id(rs.getInt("transports_id"));
            pa.setCreated(rs.getDate("created"));
            pa.setStatut(rs.getString("statut"));
            pa.setDeparture(rs.getString("departure"));
            pa.setDestination(rs.getString("destination"));
            pa.setSchedule(rs.getString("schedule"));


            particip.add(pa);

        }
        return particip;
    }
    public List<trajet> recupererComment() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Temtransportes.
        trajet dernierCommentaire = null;
        List<trajet> particip = new ArrayList<>();
        String s = "select * from trajet";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            trajet pa = new trajet();
            pa.setId(rs.getInt("id"));
            pa.setId_user(rs.getInt("id_user"));
            pa.setTransports_id(rs.getInt("transports_id"));
            pa.setCreated(rs.getDate("created"));
            pa.setStatut(rs.getString("statut"));
            pa.setDeparture(rs.getString("departure"));
            pa.setDestination(rs.getString("destination"));
            pa.setSchedule(rs.getString("schedule"));
            particip.add(pa);

        }
        return particip;
    }


    public trajet FetchOneRes(int id) throws SQLException {
        trajet r = new trajet();
        String requete = "SELECT * FROM `trajet` where id=" + id;

        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {

                r = new trajet(rs.getInt("id"), rs.getDate("created"), rs.getInt("id_user"), rs.getInt("transports_id"), rs.getString("statut"), rs.getString("departure"), rs.getString("destination"), rs.getString("schedule"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(transportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public void Deletetrajet(trajet p) throws SQLException {
        transportService es = new transportService();
        trajetService rs = new trajetService();

        trajet r = rs.FetchOneRes(p.getId());

        String requete = "delete from trajet where id=" + p.getId();
        try {
            transport tempev = es.FetchOneev(r.getTransports_id());
            System.out.println("before" + tempev);

            es.modifiertransport(tempev);
            System.out.println("after" + tempev);
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            //pst.setInt(1, id);

            pst.executeUpdate();
            System.out.println("trajet with id=" + p.getId() + " is deleted successfully");
        } catch (SQLException ex) {
            System.out.println("error in delete trajet " + ex.getMessage());
        }
    }
    
    public void modifiertrajet(trajet p) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Temtransportes.
        String req = "UPDATE trajet SET id_user = ?,transports_id = ?,created=?,statut = ?,departure = ?,destination = ?,schedule = ? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, p.getId_user());
        ps.setInt(2, p.getTransports_id());
        ps.setDate(3, p.getCreated());
        ps.setString(4, p.getStatut());
        ps.setString(5, p.getDeparture());
        ps.setString(6, p.getDestination());
        ps.setString(7, p.getSchedule());
        ps.setInt(8, p.getId());


        ps.executeUpdate();
    }
    public void ajouterreserv(trajet p) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Temtransportes.
        String req = "UPDATE trajet SET id_user = ?,transports_id = ?,created=?,statut = ?,departure = ?,destination = ?,schedule = ? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, p.getId_user());
        ps.setInt(2, p.getTransports_id());
        ps.setDate(3, p.getCreated());
        ps.setString(4, p.getStatut());
        ps.setString(5, p.getDeparture());
        ps.setString(6, p.getDestination());
        ps.setString(7, p.getSchedule());
        ps.setInt(8, p.getId());


        ps.executeUpdate();
    }



    public double calculerTotalCapaciteTrajet() throws SQLException {
        double total = 0;
        String requete = "SELECT p.capacite FROM transport p JOIN trajet pa ON p.id = pa.transports_id";

        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(requete);

        while (rs.next()) {
            total += rs.getDouble("capacite");
        }

        return total;
    }

}
