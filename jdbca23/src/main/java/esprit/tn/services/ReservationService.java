package esprit.tn.services;

import esprit.tn.entities.Reservation;
import esprit.tn.main.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements Iservice<Reservation> {

    Connection cnx;

    public ReservationService() {
        cnx = DatabaseConnection.instance.getCnx();
    }

    @Override
    public void ajouter(Reservation reservation) {
        String req = "INSERT INTO reservation (id_user, date_reservation, id_offre) VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, reservation.getId_user());
            stm.setTimestamp(2, Timestamp.valueOf(reservation.getDate_reservation()));
            stm.setInt(3, reservation.getId_offre());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Reservation reservation) {
        String req = "UPDATE reservation SET id_user = ?, date_reservation = ?, id_offre = ? WHERE id_reservation = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, reservation.getId_user());
            stm.setTimestamp(2, Timestamp.valueOf(reservation.getDate_reservation()));
            stm.setInt(3, reservation.getId_offre());
            stm.setInt(4, reservation.getId_reservation());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supprimer(Reservation reservation) {
        String req = "DELETE FROM reservation WHERE id_reservation = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, reservation.getId_reservation());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> getall() {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId_reservation(rs.getInt("id_reservation"));
                reservation.setId_user(rs.getInt("id_user"));
                reservation.setDate_reservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                reservation.setId_offre(rs.getInt("id_offre"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    @Override
    public Reservation getone() {
        return null;  // Implementation if needed
    }
}
