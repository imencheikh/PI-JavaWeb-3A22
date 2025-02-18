package esprit.tn.controllers;

import esprit.tn.entities.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtDateReservation;

    @FXML
    private TextField txtOffreId;

    @FXML
    private Button btnConfirmer;

    @FXML
    public void initialize() {
        // Ajouter des événements si nécessaires
        btnConfirmer.setOnAction(event -> ajouterReservation());
    }

    private void ajouterReservation() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int userId = Integer.parseInt(txtUserId.getText());
            int offreId = Integer.parseInt(txtOffreId.getText());

            // Convertir la date de String à LocalDateTime
            LocalDateTime dateReservation = LocalDateTime.parse(txtDateReservation.getText(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Créer l'objet Reservation
            Reservation reservation = new Reservation(id, userId, dateReservation, offreId);

            // Ajouter ici la logique de sauvegarde dans la base de données via un service
            System.out.println("Réservation créée : " + reservation);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la réservation !");
        }
    }
}
