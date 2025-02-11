package esprit.tn.services;
import esprit.tn.main.DatabaseConnection;
import esprit.tn.entities.Reclamation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import esprit.tn.entities.Type;
import java.util.Date;

public  class ReclamationService implements Iservice<Reclamation>{
    Connection cnx;

    public ReclamationService (){

        cnx= DatabaseConnection.instance.getCnx();
    }

    @Override
    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO Reclamation (nom_utilisateur, email, date, description, categorie) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, reclamation.getNom_utilisateur());
            stm.setString(2, reclamation.getEmail());
            stm.setDate(3, new java.sql.Date(reclamation.getDate().getTime())); // Conversion de java.util.Date en java.sql.Date
            stm.setString(4, reclamation.getDescription());
            stm.setString(5, reclamation.getCategorie().name()); // Enregistrer l'énumération sous forme de String

            stm.executeUpdate();
            System.out.println("Réclamation ajoutée avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la réclamation : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Reclamation reclamation) {

        String reqSelect = "SELECT nom_utilisateur, email, date, description, categorie FROM reclamation WHERE Id_reclamation = ?";
        String reqUpdate = "UPDATE reclamation SET nom_utilisateur = ?, email = ?, date = ?, description = ?, categorie = ? WHERE Id_reclamation = ?";

        try {
            PreparedStatement stmSelect = cnx.prepareStatement(reqSelect);
            stmSelect.setInt(1, reclamation.getId_reclamation());
            ResultSet rs = stmSelect.executeQuery();

            if (rs.next()) {  // Vérifie si une réclamation avec cet ID existe
                String oldNomUtilisateur = rs.getString("nom_utilisateur");
                String oldEmail = rs.getString("email");
                Date oldDate = rs.getDate("date");  // Assurez-vous que la date récupérée est un java.sql.Date
                String oldDescription = rs.getString("description");
                String oldCategorie = rs.getString("categorie");

                // Si le nouveau nom_utilisateur est null ou vide, garde l'ancien
                String newNomUtilisateur = (reclamation.getNom_utilisateur() == null || reclamation.getNom_utilisateur().isEmpty())
                        ? oldNomUtilisateur : reclamation.getNom_utilisateur();

                // Si le nouvel email est null ou vide, garde l'ancien
                String newEmail = (reclamation.getEmail() == null || reclamation.getEmail().isEmpty())
                        ? oldEmail : reclamation.getEmail();

                // Si la nouvelle date est null, garde l'ancienne
                Date newDate = (reclamation.getDate() == null) ? oldDate : reclamation.getDate();

                // Si la nouvelle description est null ou vide, garde l'ancienne
                String newDescription = (reclamation.getDescription() == null || reclamation.getDescription().isEmpty())
                        ? oldDescription : reclamation.getDescription();

                // Si la nouvelle catégorie est null, garde l'ancienne
                String newCategorie = (reclamation.getCategorie() == null) ? oldCategorie : reclamation.getCategorie().name();

                // Préparer la requête de mise à jour avec les nouveaux attributs
                PreparedStatement stmUpdate = cnx.prepareStatement(reqUpdate);
                stmUpdate.setString(1, newNomUtilisateur);
                stmUpdate.setString(2, newEmail);

                // Assurez-vous que newDate n'est pas null avant de la passer à setDate
                if (newDate != null) {
                    stmUpdate.setDate(3, new java.sql.Date(newDate.getTime()));  // Conversion de java.util.Date en java.sql.Date
                } else {
                    // Si la date est null, on peut la mettre comme null dans la base
                    stmUpdate.setNull(3, java.sql.Types.DATE);
                }

                stmUpdate.setString(4, newDescription);
                stmUpdate.setString(5, newCategorie);
                stmUpdate.setInt(6, reclamation.getId_reclamation());

                int rowsUpdated = stmUpdate.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Réclamation modifiée avec succès !");
                } else {
                    System.out.println("Aucune réclamation trouvée avec cet ID.");
                }
            } else {
                System.out.println("Réclamation introuvable avec l'ID : " + reclamation.getId_reclamation());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de la réclamation : " + e.getMessage());
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
                Reclamation r = new Reclamation();
                r.setId_reclamation(rs.getInt("Id_reclamation"));
                r.setNom_utilisateur(rs.getString("nom_utilisateur"));
                r.setEmail(rs.getString("email"));
                r.setDate(rs.getDate("date"));
                r.setDescription(rs.getString("description"));
                r.setCategorie(Type.valueOf(rs.getString("categorie").trim().toUpperCase()));

                // Convertir la catégorie en ENUM
                reclamations.add(r);
            }

            System.out.println(reclamations); // Affichage des réclamations récupérées
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



}



