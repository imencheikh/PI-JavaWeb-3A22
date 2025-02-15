package esprit.tn.controllers;

import esprit.tn.entities.Events;
import esprit.tn.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AjouterEvent {
    private final EventService eventService = new EventService();

    @FXML
    private DatePicker dateEvent;

    @FXML
    private TextField TFDescription;

    @FXML
    private TextField TFNomEvent;

    @FXML
    void ajouter(ActionEvent event) {
        String nomEv = TFNomEvent.getText().trim();
        String description = TFDescription.getText().trim();
        LocalDate date = dateEvent.getValue();

        // Vérification des champs obligatoires
        if (nomEv.isEmpty() || description.isEmpty() || date == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        // Création de l'événement
        Events ev = new Events();
        ev.setNomEv(nomEv);
        ev.setDescription(description);
        ev.setDateEvent(Date.valueOf(date));

        // Ajout à la base de données avec gestion d'erreur
        try {
            eventService.ajouter(ev);
            showAlert("Succès", "Événement ajouté avec succès !");
            // Réinitialisation des champs après ajout
            TFNomEvent.clear();
            TFDescription.clear();
            dateEvent.setValue(null);
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ajouter l'événement : " + e.getMessage());
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

    @FXML
    void afficher(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/AfficherEvent.fxml"));
        TFNomEvent.getScene().setRoot(root);
        TFDescription.getScene().setRoot(root);
        dateEvent.getScene().setRoot(root);

    }
    @FXML
    void afficherAjouterSponsor(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AjouterSponsor.fxml"));
        TFNomEvent.getScene().setRoot(root);
       /* TFDescription.getScene().setRoot(root);
        dateEvent.getScene().setRoot(root);*/
    }


}
