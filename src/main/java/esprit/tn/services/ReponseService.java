package esprit.tn.services;
import esprit.tn.entities.Reclamation;
import esprit.tn.entities.Reponse;
import esprit.tn.main.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class ReponseService implements Iservice <Reponse> {
    Connection cnx;

    public ReponseService (){

        cnx= DatabaseConnection.instance.getCnx();
    }
    @Override
    public void ajouter(Reponse reponse) {
        // Requête pour vérifier si l'ID de la réclamation existe
        String checkReq = "SELECT id_reclamation FROM Reclamation WHERE id_reclamation = ?";
        // Requête pour insérer la réponse
        String insertReq = "INSERT INTO Reponse (id_reclamation, reponse, date_reponse) VALUES (?, ?, ?)";

        try {
            // Vérifier si la réclamation avec cet ID existe
            PreparedStatement checkStmt = cnx.prepareStatement(checkReq);
            checkStmt.setInt(1, reponse.getId_reclamation());
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Erreur : La réclamation avec l'ID " + reponse.getId_reclamation() + " n'existe pas !");
                System.exit(1); // Arrêter l'exécution si l'ID de la réclamation n'existe pas
            }

            // Si l'ID de la réclamation existe, procéder à l'insertion
            PreparedStatement insertStmt = cnx.prepareStatement(insertReq);
            insertStmt.setInt(1, reponse.getId_reclamation());
            insertStmt.setString(2, reponse.getReponse());
            insertStmt.setDate(3, new java.sql.Date(reponse.getDate_reponse().getTime()));

            insertStmt.executeUpdate();
            System.out.println("Réponse ajoutée avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la réponse : " + e.getMessage());
        }
    }


    @Override
    public void modifier(Reponse reponse) {

        String reqSelect = "SELECT id_reclamation, reponse, date_reponse FROM reponse WHERE id_reponse = ?";
        String reqUpdate = "UPDATE reponse SET id_reclamation = ?, reponse = ?, date_reponse = ? WHERE id_reponse = ?";

        try {
            PreparedStatement stmSelect = cnx.prepareStatement(reqSelect);
            stmSelect.setInt(1, reponse.getId_reponse());
            ResultSet rs = stmSelect.executeQuery();

            if (rs.next()) {  // Vérifie si une réponse avec cet ID existe
                int oldIdReclamation = rs.getInt("id_reclamation");
                String oldReponse = rs.getString("reponse");
                Date oldDateReponse = rs.getDate("date_reponse");

                // Si la nouvelle réponse est null ou vide, garde l'ancienne
                String newReponse = (reponse.getReponse() == null || reponse.getReponse().isEmpty())
                        ? oldReponse : reponse.getReponse();

                // Si la nouvelle date est null, garde l'ancienne
                Date newDateReponse = (reponse.getDate_reponse() == null) ? oldDateReponse : reponse.getDate_reponse();

                // Préparer la requête de mise à jour avec les nouveaux attributs
                PreparedStatement stmUpdate = cnx.prepareStatement(reqUpdate);
                stmUpdate.setInt(1, oldIdReclamation); // Garder l'ID de réclamation d'origine
                stmUpdate.setString(2, newReponse);

                // Assurez-vous que newDateReponse n'est pas null avant de la passer à setDate
                if (newDateReponse != null) {
                    stmUpdate.setDate(3, new java.sql.Date(newDateReponse.getTime()));  // Conversion de java.util.Date en java.sql.Date
                } else {
                    // Si la date est null, on peut la mettre comme null dans la base
                    stmUpdate.setNull(3, java.sql.Types.DATE);
                }

                stmUpdate.setInt(4, reponse.getId_reponse());

                int rowsUpdated = stmUpdate.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Réponse modifiée avec succès !");
                } else {
                    System.out.println("Aucune réponse trouvée avec cet ID.");
                }
            } else {
                System.out.println("Réponse introuvable avec l'ID : " + reponse.getId_reponse());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de la réponse : " + e.getMessage());
        }

    }


    @Override
    public void supprimer(int idReponse) {
        String req = "DELETE FROM Reponse WHERE Id_reponse = ?";

        try (PreparedStatement stm = cnx.prepareStatement(req)) {
            stm.setInt(1, idReponse);  // On remplace le "?" par l'ID de la réclamation

            // Exécution de la requête de suppression
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reponse supprimée avec succès.");
            } else {
                System.out.println("Aucune Reponse trouvée avec cet ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reponse> getall() {
        return null;
    }



    public List<Reponse> getAll() {
        List<Reponse> reponses = new ArrayList<>();
        String req = "SELECT * FROM reponse";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while (rs.next()) {
                Reponse reponse = new Reponse();
                reponse.setId_reponse(rs.getInt("id_reponse"));
                reponse.setId_reclamation(rs.getInt("id_reclamation"));
                reponse.setReponse(rs.getString("reponse"));
                reponse.setDate_reponse(rs.getDate("date_reponse"));

                reponses.add(reponse);  // Add the Reponse object to the list
            }

            System.out.println(reponses); // Affichage des réponses récupérées
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des réponses", e);
        }

        return reponses;
    }

    @Override
    public Reponse getone() {
        return null;
    }

    public void afficher() {
        List<Reponse> reponses = getAll(); // Récupérer toutes les réponses

        // Affichage des réponses dans le terminal
        if (reponses.isEmpty()) {
            System.out.println("Aucune réponse à afficher.");
        } else {
            for (Reponse reponse : reponses) {
                System.out.println("ID: " + reponse.getId_reponse() +
                        ", ID Réclamation: " + reponse.getId_reclamation() +
                        ", Reponse: " + reponse.getReponse() +
                        ", Date: " + reponse.getDate_reponse());
            }
        }
    }



}
