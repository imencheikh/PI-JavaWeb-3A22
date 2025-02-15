package esprit.tn.services;
import esprit.tn.entities.Events;
import esprit.tn.main.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventService implements Iservice<Events> {
    Connection cnx;

    public EventService() {
        cnx = DatabaseConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Events event) {
        String req = "INSERT INTO events (nomEv, description, dateEvent) VALUES (?, ?, ?)";

        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, event.getNomEv());
            stm.setString(2, event.getDescription());
            java.sql.Date sqlDate = new java.sql.Date(event.getDateEvent().getTime());
            stm.setDate(3, sqlDate);

            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modifier(Events event) {
        String reqSelect = "SELECT nomEv, description, dateEvent FROM events WHERE idEvent = ?";
        String reqUpdate = "UPDATE events SET nomEv = ?, description = ?, dateEvent = ? WHERE idEvent = ?";

        try (
                PreparedStatement stmSelect = cnx.prepareStatement(reqSelect);
                PreparedStatement stmUpdate = cnx.prepareStatement(reqUpdate)
        ) {
            stmSelect.setInt(1, event.getIdEvent());
            try (ResultSet rs = stmSelect.executeQuery()) {
                if (rs.next()) {
                    String oldNomEv = rs.getString("nomEv");
                    String oldDescription = rs.getString("description");
                    java.sql.Date oldDateEvent = rs.getDate("dateEvent");

                    String newNomEv = (event.getNomEv() == null || event.getNomEv().isEmpty()) ? oldNomEv : event.getNomEv();
                    String newDescription = (event.getDescription() == null || event.getDescription().isEmpty()) ? oldDescription : event.getDescription();
                    java.sql.Date newDateEvent = (event.getDateEvent() == null) ? oldDateEvent : new java.sql.Date(event.getDateEvent().getTime());

                    stmUpdate.setString(1, newNomEv);
                    stmUpdate.setString(2, newDescription);
                    stmUpdate.setDate(3, newDateEvent);
                    stmUpdate.setInt(4, event.getIdEvent());

                    int rowsUpdated = stmUpdate.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Modification réussie !");
                    } else {
                        System.out.println("Aucune modification effectuée.");
                    }
                } else {
                    System.out.println("Événement introuvable avec l'ID : " + event.getIdEvent());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur de base de données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void supprimer(Events event) {
        String req = "DELETE FROM events WHERE idEvent = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, event.getIdEvent());
            int rowsDeleted = stm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Suppression réussie !");
            } else {
                System.out.println("Aucune event trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Events> getAll() {
        List<Events> ev = new ArrayList<>();
        String req = "SELECT * FROM events";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while (rs.next()) {
                Events E = new Events();
                E.setIdEvent(rs.getInt("IdEvent"));
                E.setNomEv(rs.getString("NomEv"));
                E.setDescription(rs.getString("Description"));
                E.setDateEvent(rs.getDate("DateEvent"));
                ev.add(E);
            }

            System.out.println(ev);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ev;
    }

    public Events getOne(int idEvent) {
        String req = "SELECT * FROM events WHERE idEvent = ?";
        try (PreparedStatement stm = cnx.prepareStatement(req)) {
            stm.setInt(1, idEvent);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Events event = new Events();
                    event.setIdEvent(rs.getInt("IdEvent"));
                    event.setNomEv(rs.getString("NomEv"));
                    event.setDescription(rs.getString("Description"));
                    event.setDateEvent(rs.getDate("DateEvent"));
                    return event;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur de récupération de l'événement : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
