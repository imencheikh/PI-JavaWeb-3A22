package esprit.tn.controllers;

import esprit.tn.entities.Events;
import esprit.tn.entities.Sponsors;
import esprit.tn.services.EventService;
import esprit.tn.services.SponsorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.time.Instant;

public class ModifierEvent {

    @FXML
    private TextField TFNomEvent;
    @FXML
    private TextField TFDescription;
    @FXML
    private DatePicker dateEvent;
    @FXML
    private ComboBox<String> comboBoxSponsors;
    @FXML
    private TextField TFLieuEvent;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    private Events selectedEvent;
    private Sponsors sponsors;
    private final EventService eventService = new EventService();
    private AfficherEvent afficherEventController;
    private final SponsorService sponsorService = new SponsorService();

    private void chargerSponsors() {
        List<String> sponsors = sponsorService.getAll().stream()
                .map(Sponsors::getNomSponsor) // Récupérer uniquement les noms
                .collect(Collectors.toList());

        comboBoxSponsors.getItems().setAll(sponsors);
    }

    public void setEvent(Events event) {
        chargerSponsors(); // Charger les sponsors avant d'affecter la valeur
        if (event != null) {
            selectedEvent = event;
            // Récupérer et afficher les informations de l'événement dans les champs
            TFNomEvent.setText(event.getNomEv());
            TFDescription.setText(event.getDescription());
            comboBoxSponsors.setValue(event.getNomSp());
            TFLieuEvent.setText(event.getLieu());
            Date date = event.getDateEvent();
            if (date instanceof java.sql.Date) {
                LocalDate localDate = ((java.sql.Date) date).toLocalDate();
                dateEvent.setValue(localDate);
            } else if (date instanceof java.util.Date) {
                Instant instant = date.toInstant();
                LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                dateEvent.setValue(localDate);
            }
        }
    }

    @FXML
    private void modifierEvent() {
        if (validerChamps()) {
            selectedEvent.setNomEv(TFNomEvent.getText());
            selectedEvent.setDescription(TFDescription.getText());
            selectedEvent.setDateEvent(java.sql.Date.valueOf(dateEvent.getValue()));
            selectedEvent.setNomSp(comboBoxSponsors.getValue());
            selectedEvent.setLieu(TFLieuEvent.getText());
            eventService.modifier(selectedEvent);

            afficherAlerte("Modification", "Événement modifié avec succès !");

            // Rafraîchir la liste des événements après la modification
            if (afficherEventController != null) {
                afficherEventController.refreshList();
            }

            fermerFenetre();
        }
    }

    @FXML
    private void supprimerEvent() {
        if (selectedEvent == null) {
            afficherAlerte("Erreur", "Aucun événement sélectionné !");
            return;
        }

        eventService.supprimer(selectedEvent); // On passe l'objet entier
        afficherAlerte("Suppression", "Événement supprimé !");

        // Rafraîchir la liste des événements après la suppression
        if (afficherEventController != null) {
            afficherEventController.refreshList();
        }

        fermerFenetre();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) TFNomEvent.getScene().getWindow();
        stage.close();
    }

    public void setAfficherEventController(AfficherEvent afficherEventController) {
        this.afficherEventController = afficherEventController;
    }

    // Méthode générique pour afficher des alertes
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Validation des champs
    private boolean validerChamps() {
        // Vérification des champs obligatoires
        if (TFNomEvent.getText().isEmpty() || TFDescription.getText().isEmpty() || dateEvent.getValue() == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
            return false;
        }

        // Vérification que le nom de l'événement contient uniquement des lettres et des espaces
        String nomEv = TFNomEvent.getText().trim();
        if (!nomEv.matches("[a-zA-Z\\s]+")) {
            afficherAlerte("Erreur", "Le nom de l'événement ne doit contenir que des lettres et des espaces !");
            return false;
        }

        // Vérification que le lieu de l'événement contient uniquement des lettres et des espaces
        String lieu = TFLieuEvent.getText().trim();
        if (!nomEv.matches("[a-zA-Z\\s]+")) {
            afficherAlerte("Erreur", "Le lieu de l'événement ne doit contenir que des lettres et des espaces !");
            return false;
        }

        // Vérification que le sponsor est bien sélectionné et n'est pas vide
        String selectedSponsor = comboBoxSponsors.getValue();
        if (selectedSponsor.trim().isEmpty()) {
            afficherAlerte("Erreur", "Veuillez sélectionner un sponsor valide !");
            return false;
        }
        return true;
    }

    @FXML
    void afficherAjouterSponsor(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AjouterSponsor.fxml"));
        TFNomEvent.getScene().setRoot(root);
       /* TFDescription.getScene().setRoot(root);
        dateEvent.getScene().setRoot(root);*/
    }
}
