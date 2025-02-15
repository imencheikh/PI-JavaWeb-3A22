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
            selectedSponsor.setNomSponsor(TFNomSponsor.getText());
            selectedSponsor.setContribution(Float.parseFloat(TFMontantSponsor.getText()));

            selectedSponsor.setEmailSpon(TFEmailSponsor.getText());

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
        if (TFNomSponsor.getText().trim().isEmpty() || TFMontantSponsor.getText().trim().isEmpty() || TFEmailSponsor.getText().trim().isEmpty()) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
            return false;
        }

        try {
            float montant = Float.parseFloat(TFMontantSponsor.getText());
            if (montant < 0) {
                afficherAlerte("Erreur", "Le montant doit être positif !");
                return false;
            }
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Le montant doit être un nombre valide !");
            return false;
        }

        // Validation de l'email
        String email = TFEmailSponsor.getText();
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            afficherAlerte("Erreur", "L'email n'est pas valide !");
            return false;
        }

        return true;
    }

}
