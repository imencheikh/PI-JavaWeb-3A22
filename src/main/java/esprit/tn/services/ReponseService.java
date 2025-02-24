package esprit.tn.services;
import esprit.tn.entities.Reclamation;
import esprit.tn.entities.Reponse;
import esprit.tn.main.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseService implements Iservice <Reponse> {
    Connection cnx;

    public ReponseService (){
        cnx = DatabaseConnection.instance.getCnx();
    }

    @Override
    public void ajouter(Reponse reponse) {
        // Requête pour vérifier si l'ID de la réclamation existe
       // String checkReq = "SELECT id_reclamation FROM Reclamation WHERE id_reclamation = ?";
        // Requête pour insérer la réponse
        String insertReq = "INSERT INTO Reponse (id_reclamation, reponse) VALUES (?, ?)";

        try {
               // Si l'ID de la réclamation existe, procéder à l'insertion
            PreparedStatement insertStmt = cnx.prepareStatement(insertReq);
            insertStmt.setInt(1, reponse.getId_reclamation());
            insertStmt.setString(2, reponse.getReponse());


            insertStmt.executeUpdate();
            System.out.println("Réponse ajoutée avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la réponse : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Reponse reponse) {
        String reqSelect = "SELECT id_reclamation, reponse FROM reponse WHERE id_reponse = ?";
        String reqUpdate = "UPDATE reponse SET id_reclamation = ?, reponse = ? WHERE id_reponse = ?";

        try {
            PreparedStatement stmSelect = cnx.prepareStatement(reqSelect);
            stmSelect.setInt(1, reponse.getId_reponse());
            ResultSet rs = stmSelect.executeQuery();

            if (rs.next()) {  // Vérifie si une réponse avec cet ID existe
                int oldIdReclamation = rs.getInt("id_reclamation");
                String oldReponse = rs.getString("reponse");

                // Si la nouvelle réponse est null ou vide, garde l'ancienne
                String newReponse = (reponse.getReponse() == null || reponse.getReponse().isEmpty())
                        ? oldReponse : reponse.getReponse();

                // Préparer la requête de mise à jour avec les nouveaux attributs
                PreparedStatement stmUpdate = cnx.prepareStatement(reqUpdate);
                stmUpdate.setInt(1, oldIdReclamation); // Garder l'ID de réclamation d'origine
                stmUpdate.setString(2, newReponse);
                stmUpdate.setInt(3, reponse.getId_reponse());

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
                System.out.println("Réponse supprimée avec succès.");
            } else {
                System.out.println("Aucune réponse trouvée avec cet ID.");
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
        String req = "SELECT   reponse.id_reponse,reponse.id_reclamation,reponse.reponse,reponse.date_reponse,reclamation.id_reclamation,reclamation.nom_utilisateur,reclamation.email,reclamation.description FROM reponse INNER JOIN reclamation ON reponse.id_reclamation = reclamation.id_reclamation;";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while (rs.next()) {
                Reponse reponse = new Reponse();
                reponse.setId_reponse(rs.getInt("id_reponse"));
                reponse.setId_reclamation(rs.getInt("id_reclamation"));
                reponse.setNom_utilisateur(rs.getString("nom_utilisateur"));
                reponse.setEmail(rs.getString("email"));
                reponse.setDescription(rs.getString("description"));
                reponse.setReponse(rs.getString("reponse"));



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
                        ", Réponse: " + reponse.getReponse());
            }
        }
    }

    @Override
    public void enregistrer(Reponse reponse) {

    }


}
