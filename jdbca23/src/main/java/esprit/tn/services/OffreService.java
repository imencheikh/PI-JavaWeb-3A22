package esprit.tn.services;

import esprit.tn.entities.Offre;
import esprit.tn.main.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreService implements Iservice<Offre> {

    Connection cnx;

    public OffreService() {
        cnx = DatabaseConnection.instance.getCnx();
    }

    @Override
    public void ajouter(Offre offre) {
        String req = "INSERT INTO offre (prix_offre, description_offre, id_event) VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setFloat(1, offre.getPrix_offre());
            stm.setString(2, offre.getDescription_offre());
            stm.setInt(3, offre.getId_event());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Offre offre) {
        String req = "UPDATE offre SET prix_offre = ?, description_offre = ?, id_event = ? WHERE id_offre = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setFloat(1, offre.getPrix_offre());
            stm.setString(2, offre.getDescription_offre());
            stm.setInt(3, offre.getId_event());
            stm.setInt(4, offre.getId_offre());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supprimer(Offre offre) {
        String req = "DELETE FROM offre WHERE id_offre = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, offre.getId_offre());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Offre> getall() {
        List<Offre> offres = new ArrayList<>();
        String req = "SELECT * FROM offre";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                Offre offre = new Offre();
                offre.setId_offre(rs.getInt("id_offre"));
                offre.setPrix_offre(rs.getFloat("prix_offre"));
                offre.setDescription_offre(rs.getString("description_offre"));
                offre.setId_event(rs.getInt("id_event"));
                offres.add(offre);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return offres;
    }

    @Override
    public Offre getone() {
        return null;  // Implementation if needed
    }
}
