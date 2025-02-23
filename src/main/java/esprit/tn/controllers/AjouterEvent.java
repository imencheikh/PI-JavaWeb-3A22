package esprit.tn.controllers;

import esprit.tn.entities.Events;
import esprit.tn.entities.Sponsors;
import esprit.tn.services.EventService;
import esprit.tn.services.SponsorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AjouterEvent  {
    private final EventService eventService = new EventService();

    @FXML
    private DatePicker dateEvent;

    @FXML
    private TextField TFDescription;

    @FXML
    private TextField TFNomEvent;
    @FXML
    private ComboBox<String> comboBoxSponsors;

    private SponsorService sponsorService = new SponsorService();


    @FXML
    void ajouter(ActionEvent event) {
        String nomEv = TFNomEvent.getText().trim();
        String description = TFDescription.getText().trim();
        LocalDate date = dateEvent.getValue();
        String nomSp=comboBoxSponsors.getValue();
        // Vérification des champs obligatoires
        if (nomEv.isEmpty() || description.isEmpty() || date == null || comboBoxSponsors == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }
        // Vérification que le nom de l'événement ne contient que des lettres
        if (!nomEv.matches("[a-zA-Z\\s]+")) {
            showAlert("Erreur", "Le nom de l'événement ne doit contenir que des lettres et des espaces !");
            return;
        }
        // Création de l'événement
        Events ev = new Events();
        ev.setNomEv(nomEv);
        ev.setDescription(description);
        ev.setDateEvent(Date.valueOf(date));
        ev.setNomSp(nomSp);

        // Ajout à la base de données avec gestion d'erreur
        try {
            eventService.ajouter(ev);
            showAlert("Succès", "Événement ajouté avec succès !");
            // Réinitialisation des champs après ajout
            TFNomEvent.clear();
            TFDescription.clear();
            dateEvent.setValue(null);
            comboBoxSponsors.setValue(null);
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
        comboBoxSponsors.getScene().setRoot(root);
        /////////////////////////////////


        //////////////////////////

    }
    @FXML
    void afficherAjouterSponsor(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AjouterSponsor.fxml"));
        TFNomEvent.getScene().setRoot(root);
       /* TFDescription.getScene().setRoot(root);
        dateEvent.getScene().setRoot(root);*/
    }


    @FXML
    void initialize() {
        List<Sponsors> sponsorsList = sponsorService.getAll(); // Récupère tous les sponsors
        ObservableList<String> sponsorNames = FXCollections.observableArrayList();

        // Ajouter uniquement les noms des sponsors dans la ComboBox
        for (Sponsors sponsor : sponsorsList) {
            sponsorNames.add(sponsor.getNomSponsor());
        }

        comboBoxSponsors.setItems(sponsorNames); // Remplir la ComboBox

        // Limiter la sélection du DatePicker aux dates futures (y compris la date actuelle)
        dateEvent.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Désactiver les dates avant aujourd'hui
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
    }



}
