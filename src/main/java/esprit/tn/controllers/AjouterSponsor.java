package esprit.tn.controllers;

import esprit.tn.entities.Sponsors;
import esprit.tn.services.SponsorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class AjouterSponsor {
    private final SponsorService sponsorService = new SponsorService();

    @FXML
    private TextField TFNomSponsor;

    @FXML
    private TextField TFEmailSponsor;

    @FXML
    private TextField TFContribution;

    @FXML
    void ajouterSponsor(ActionEvent event) {
        String nomSponsor = TFNomSponsor.getText().trim();
        String emailSponsor = TFEmailSponsor.getText().trim();
        String contributionStr = TFContribution.getText().trim();

        // Vérification des champs obligatoires
        if (nomSponsor.isEmpty() || emailSponsor.isEmpty() || contributionStr.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        // Vérification si l'email est valide
        if (!isValidEmail(emailSponsor)) {
            showAlert("Erreur", "L'email du sponsor est invalide !");
            return;
        }

        // Vérification du nom du sponsor
        if (!isValidName(nomSponsor)) {
            showAlert("Erreur", "Le nom du sponsor ne doit contenir que des lettres et des espaces !");
            return;
        }

        // Vérification si le sponsor existe déjà dans la base de données
        if (sponsorService.getByName(nomSponsor) != null) {
            showAlert("Erreur", "Un sponsor avec ce nom existe déjà !");
            return;
        }

        // Vérification si la contribution est un nombre valide
        float contribution;
        try {
            contribution = Float.parseFloat(contributionStr);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "La contribution doit être un nombre valide !");
            return;
        }

        // Création du sponsor
        Sponsors sponsor = new Sponsors();
        sponsor.setNomSponsor(nomSponsor);
        sponsor.setEmailSpon(emailSponsor);
        sponsor.setContribution(contribution);

        // Ajout à la base de données avec gestion d'erreur
        try {
            sponsorService.ajouter(sponsor);
            showAlert("Succès", "Sponsor ajouté avec succès !");
            // Réinitialisation des champs après ajout
            TFNomSponsor.clear();
            TFEmailSponsor.clear();
            TFContribution.clear();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ajouter le sponsor : " + e.getMessage());
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Méthode pour valider le nom du sponsor
    private boolean isValidName(String name) {
        return name.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$");
    }
    // Méthode de validation de l'email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @FXML
    void afficher(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherSponsor.fxml"));
        TFNomSponsor.getScene().setRoot(root);
        TFEmailSponsor.getScene().setRoot(root);
        TFContribution.getScene().setRoot(root);
    }
    @FXML
    void afficherSponsors(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherSponsor.fxml"));
        TFNomSponsor.getScene().setRoot(root);
    }
}
