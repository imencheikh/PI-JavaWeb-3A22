package esprit.tn.services;
import controllers.ModifierReclamation;
import esprit.tn.main.DatabaseConnection;
import esprit.tn.entities.Reclamation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public  class ReclamationService implements Iservice<Reclamation>{
    Connection cnx;

    public ReclamationService (){

        cnx= DatabaseConnection.getInstance().getCnx();
    }


    // Déclaration de la liste des réclamations
    private List<Reclamation> reclamations;

    @Override
    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO Reclamation (nom_utilisateur, email,  description,categorie) VALUES (?, ?, ?,?)";

        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, reclamation.getNom_utilisateur());
            stm.setString(2, reclamation.getEmail());
          //  stm.setDate(3, new java.sql.Date(reclamation.getDate().getTime())); // Conversion de java.util.Date en java.sql.Date
            stm.setString(3, reclamation.getDescription());
           stm.setString(4, reclamation.getCategorie()); // Enregistrer l'énumération sous forme de String

            stm.executeUpdate();
            System.out.println("Réclamation ajoutée avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la réclamation : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Reclamation reclamation) {

        try {
            // Charger le fichier FXML de la fenêtre de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier_reclamation.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la fenêtre de modification
            ModifierReclamation controller = loader.getController();
           controller.initData(reclamation);  // Envoyer les données de la réclamation sélectionnée

            // Créer un nouveau Stage (fenêtre)
            Stage stage = new Stage();
            stage.setTitle("Modifier Réclamation");
            stage.setScene(new Scene(root));


            // Afficher la nouvelle fenêtre
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void supprimer(int idReclamation) {
        String req = "DELETE FROM reclamation WHERE Id_reclamation = ?";

        try (PreparedStatement stm = cnx.prepareStatement(req)) {
            stm.setInt(1, idReclamation);  // On remplace le "?" par l'ID de la réclamation

            // Exécution de la requête de suppression
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Réclamation supprimée avec succès.");
            } else {
                System.out.println("Aucune réclamation trouvée avec cet ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reclamation> getall() {
        return null;
    }


    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList<>();

        String req = "SELECT * FROM reclamation";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while (rs.next()) {
                // Récupérer les valeurs directement à partir de ResultSet
                String nom_utilisateur = rs.getString("nom_utilisateur");
                String email = rs.getString("email");
                String description = rs.getString("description");
                String categorie= rs.getString("categorie");
                int id =rs.getInt("id_reclamation");
                // Créer un objet Reclamation en utilisant les valeurs récupérées
                Reclamation r = new Reclamation(nom_utilisateur, email, description,categorie, id);

                // Initialiser les autres propriétés
                r.setId_reclamation(rs.getInt("Id_reclamation"));
                r.setDate(rs.getDate("date"));
               // r.setCategorie(Type.valueOf(rs.getString("categorie").trim().toUpperCase()));  // Assurer que "categorie" est une valeur valide de l'énum Type

                // Ajouter l'objet Reclamation à la liste
                reclamations.add(r);
            }

           // System.out.println(reclamations); // Affichage des réclamations récupérées
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des réclamations", e);
        }

        return reclamations;
    }


    @Override
    public Reclamation getone() {
        return null;
    }


    public void afficher() {
        List<Reclamation> reclamations = getAll(); // Récupérer toutes les réclamations

        // Affichage des réclamations dans le terminal
        if (reclamations.isEmpty()) {
            System.out.println("Aucune réclamation à afficher.");
        } else {
            for (Reclamation reclamation : reclamations) {
                System.out.println("ID: " + reclamation.getId_reclamation() +
                        ", Nom: " + reclamation.getNom_utilisateur() +
                        ", Email: " + reclamation.getEmail() +
                        ", Date: " + reclamation.getDate() +
                        ", Description: " + reclamation.getDescription() +
                        ", Catégorie: " + reclamation.getCategorie());
            }
        }
    }
    @Override
    public void enregistrer(Reclamation reclamation) {
        // Vérification de la connexion
        if (cnx == null) {
            System.err.println("La connexion à la base de données est fermée ou invalide.");
            return;
        }

        // Vérification de l'ID
        if (reclamation.getId_reclamation() == 0) {
            System.err.println("Erreur : ID réclamation non valide (0). Vérifiez le champ ID.");
            return;
        }

        System.out.println("Tentative de modification pour ID : " + reclamation.getId_reclamation());

        // Requête pour vérifier si l'ID existe avant modification
        String reqSelect = "SELECT nom_utilisateur, email, description FROM reclamation WHERE id_reclamation = ?";

        // Requête de mise à jour SQL
        String reqUpdate = "UPDATE reclamation SET nom_utilisateur = ?, email = ?, description = ? WHERE id_reclamation = ?";

        try {
            // Vérification si l'ID existe
            PreparedStatement reqsel = cnx.prepareStatement(reqSelect);
            reqsel.setInt(1, reclamation.getId_reclamation());
            ResultSet rs = reqsel.executeQuery();

            if (!rs.next()) {
                System.out.println("Aucune réclamation trouvée avec l'ID : " + reclamation.getId_reclamation());
                return;
            }

            // Remplissage des paramètres pour la mise à jour
            PreparedStatement stm = cnx.prepareStatement(reqUpdate);
            stm.setString(1, reclamation.getNom_utilisateur());
            stm.setString(2, reclamation.getEmail());
            stm.setString(3, reclamation.getDescription());
            stm.setInt(4, reclamation.getId_reclamation());

            // Exécution de la mise à jour
            int rowsAffected = stm.executeUpdate();

            // Vérification si la mise à jour a bien été effectuée
            if (rowsAffected > 0) {
                System.out.println("Réclamation mise à jour avec succès !");
            } else {
                System.out.println("Échec de la mise à jour. Aucune modification apportée.");
            }

        } catch (SQLException e) {
            // Gestion des erreurs SQL
            e.printStackTrace();
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }


    public Reclamation getReclamationById(int id) {

        for (Reclamation reclamation : reclamations) {
            // Utilisez le getter pour accéder à l'ID de la réclamation
            if (reclamation.getId_reclamation() == id) {
                return reclamation;
            }
        }
        return null; // Si la réclamation n'est pas trouvée
    }


}



