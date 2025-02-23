
package esprit.tn.services;

import esprit.tn.entities.Sponsors;
import esprit.tn.main.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsorService {
    private Connection connection;

    public SponsorService() {
        connection = DatabaseConnection.getInstance().getCnx();
    }

    public void ajouter(Sponsors sponsor) {
        String query = "INSERT INTO sponsors (nomSponsor, emailSpon, contribution) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, sponsor.getNomSponsor());
            pst.setString(2, sponsor.getEmailSpon());
            pst.setFloat(3, sponsor.getContribution());
            pst.executeUpdate();
            System.out.println("Sponsor ajouté avec succès!");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du sponsor: " + e.getMessage());
        }
    }

    public void modifier(Sponsors sponsor) {
        String reqSelect = "SELECT nomSponsor, emailSpon, contribution FROM sponsors WHERE idsponsor = ?";
        String reqUpdate = "UPDATE sponsors SET nomSponsor = ?, emailSpon = ?, contribution = ? WHERE idsponsor = ?";

        try (
                PreparedStatement stmSelect = connection.prepareStatement(reqSelect);
                PreparedStatement stmUpdate = connection.prepareStatement(reqUpdate)
        ) {
            stmSelect.setInt(1, sponsor.getIdsponsor());
            try (ResultSet rs = stmSelect.executeQuery()) {
                if (rs.next()) {
                    String oldNomSponsor = rs.getString("nomSponsor");
                    String oldEmailSpon = rs.getString("emailSpon");
                    float oldContribution = rs.getFloat("contribution");

                    // Vérification et mise à jour des valeurs si elles ne sont pas nulles ou vides
                    String newNomSponsor = (sponsor.getNomSponsor() == null || sponsor.getNomSponsor().isEmpty()) ? oldNomSponsor : sponsor.getNomSponsor();
                    String newEmailSpon = (sponsor.getEmailSpon() == null || sponsor.getEmailSpon().isEmpty()) ? oldEmailSpon : sponsor.getEmailSpon();
                    float newContribution = (sponsor.getContribution() == 0) ? oldContribution : sponsor.getContribution();

                    stmUpdate.setString(1, newNomSponsor);
                    stmUpdate.setString(2, newEmailSpon);
                    stmUpdate.setFloat(3, newContribution);
                    stmUpdate.setInt(4, sponsor.getIdsponsor());

                    int rowsUpdated = stmUpdate.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Modification du sponsor réussie !");
                    } else {
                        System.out.println("Aucune modification effectuée.");
                    }
                } else {
                    System.out.println("Sponsor introuvable avec l'ID : " + sponsor.getIdsponsor());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du sponsor : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void supprimer(Sponsors sponsor) {
        String req = "DELETE FROM sponsors WHERE idsponsor = ?";
        try (PreparedStatement stm = connection.prepareStatement(req)) {
            stm.setInt(1, sponsor.getIdsponsor());  // Utiliser getIdSponsor() pour récupérer l'ID
            int rowsDeleted = stm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Suppression réussie !");
            } else {
                System.out.println("Aucun sponsor trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du sponsor : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<Sponsors> getAll() {
        List<Sponsors> sponsors = new ArrayList<>();
        String query = "SELECT * FROM sponsors";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Sponsors sponsor = new Sponsors(
                        rs.getInt("idsponsor"),
                        rs.getString("nomSponsor"),
                        rs.getString("emailSpon"),
                        rs.getFloat("contribution")
                );
                sponsors.add(sponsor);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des sponsors: " + e.getMessage());
        }

        // Afficher la liste des sponsors de manière lisible
        for (Sponsors sponsor : sponsors) {
            System.out.println(sponsor);
        }

        return sponsors;
    }

    public Sponsors getOne(int idSponsor) {
        String query = "SELECT * FROM sponsors WHERE idsponsor = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, idSponsor);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Sponsors(
                            rs.getInt("idsponsor"),
                            rs.getString("nomSponsor"),
                            rs.getString("emailSpon"),
                            rs.getFloat("contribution")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du sponsor: " + e.getMessage());
        }
        return null;
    }
    public Sponsors getByName(String nomSponsor) {
        String query = "SELECT * FROM sponsors WHERE nomSponsor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nomSponsor);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Sponsors sponsor = new Sponsors();
                sponsor.setNomSponsor(resultSet.getString("nomSponsor"));
                sponsor.setEmailSpon(resultSet.getString("emailSpon"));
                sponsor.setContribution(resultSet.getFloat("contribution"));
                return sponsor; // Retourne l'objet sponsor si trouvé
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retourne null si aucun sponsor avec ce nom n'est trouvé
    }

}
