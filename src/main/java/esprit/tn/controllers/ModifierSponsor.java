package esprit.tn.controllers;

import esprit.tn.entities.Sponsors;
import esprit.tn.services.SponsorService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierSponsor {

    @FXML
    private TextField TFNomSponsor;
    @FXML
    private TextField TFEmailSponsor;
    @FXML
    private TextField TFMontantSponsor;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    private Sponsors selectedSponsor;
    private final SponsorService sponsorService = new SponsorService();
    private AfficherSponsor afficherSponsorController;

    public void setSponsor(Sponsors sponsor) {
        if (sponsor != null) {
            selectedSponsor = sponsor;
            // Préremplir les champs avec les valeurs existantes
            TFNomSponsor.setText(sponsor.getNomSponsor());
            TFEmailSponsor.setText(sponsor.getEmailSpon());
            TFMontantSponsor.setText(String.valueOf(sponsor.getContribution()));
        }
    }

    @FXML
    private void modifierSponsor() {
        if (validerChamps()) {
            selectedSponsor.setNomSponsor(TFNomSponsor.getText().trim());
            selectedSponsor.setContribution(Float.parseFloat(TFMontantSponsor.getText().trim()));
            selectedSponsor.setEmailSpon(TFEmailSponsor.getText().trim());

            sponsorService.modifier(selectedSponsor);
            afficherAlerte("Modification", "Sponsor modifié avec succès !");

            // Rafraîchir la liste des sponsors après modification
            if (afficherSponsorController != null) {
                afficherSponsorController.refreshList();
            }

            fermerFenetre();
        }
    }

    @FXML
    private void supprimerSponsor() {
        if (selectedSponsor == null) {
            afficherAlerte("Erreur", "Aucun sponsor sélectionné !");
            return;
        }

        sponsorService.supprimer(selectedSponsor);
        afficherAlerte("Suppression", "Sponsor supprimé avec succès !");

        // Rafraîchir la liste des sponsors après suppression
        if (afficherSponsorController != null) {
            afficherSponsorController.refreshList();
        }

        fermerFenetre();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) TFNomSponsor.getScene().getWindow();
        stage.close();
    }

    public void setAfficherSponsorController(AfficherSponsor afficherSponsorController) {
        this.afficherSponsorController = afficherSponsorController;
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validerChamps() {
        String nom = TFNomSponsor.getText().trim();
        String email = TFEmailSponsor.getText().trim();
        String montantStr = TFMontantSponsor.getText().trim();

        if (nom.isEmpty() || email.isEmpty() || montantStr.isEmpty()) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
            return false;
        }

        // Vérification du format du nom (uniquement lettres et espaces)
        if (!isValidName(nom)) {
            afficherAlerte("Erreur", "Le nom du sponsor ne doit contenir que des lettres et des espaces !");
            return false;
        }

        // Vérification du format de l'email
        if (!isValidEmail(email)) {
            afficherAlerte("Erreur", "L'email n'est pas valide !");
            return false;
        }

        // Vérification du format du montant
        try {
            float montant = Float.parseFloat(montantStr);
            if (montant < 0) {
                afficherAlerte("Erreur", "Le montant doit être un nombre positif !");
                return false;
            }
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Le montant doit être un nombre valide !");
            return false;
        }

        return true;
    }

    // Méthode pour valider que le nom ne contient que des lettres et des espaces
    private boolean isValidName(String name) {
        return name.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$");
    }

    // Méthode pour valider un email avec une expression régulière
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
