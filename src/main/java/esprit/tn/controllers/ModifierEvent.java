package esprit.tn.controllers;

import esprit.tn.entities.Events;
import esprit.tn.services.EventService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    private Events selectedEvent;
    private final EventService eventService = new EventService();
    private AfficherEvent afficherEventController;

    public void setEvent(Events event) {
        if (event != null) {
            selectedEvent = event;
            // Récupérer et afficher les informations de l'événement dans les champs
            TFNomEvent.setText(event.getNomEv());
            TFDescription.setText(event.getDescription());

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
            selectedEvent.setDateEvent(java.sql.Date.valueOf(dateEvent.getValue())); // Correct

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
        if (TFNomEvent.getText().isEmpty() || TFDescription.getText().isEmpty() || dateEvent.getValue() == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
            return false;
        }
        return true;
    }
}
