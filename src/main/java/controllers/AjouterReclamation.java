package controllers;

import esprit.tn.entities.Reclamation;
import esprit.tn.services.ReclamationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterReclamation implements Initializable {
    private final ReclamationService reclamationService = new ReclamationService();

    @FXML
    private ComboBox<String> TFcat;

    @FXML
    private TextField TFdesc;

    @FXML
    private TextField TFemail;

    @FXML
    private TextField TFnom;
    @FXML
    private TextField TFid;
    @FXML
    private Button btnEnvoyer; // Le bouton "Envoyer"private int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TFcat.setItems(FXCollections.observableArrayList("Bug Technique", "Probleme Transport", "Probleme Paiement", "Probleme Logistique"));
    }

    @FXML
    void getComboBoxInfo(ActionEvent event) {
        System.out.println(TFcat.getValue());
    }

    @FXML
    void ajouter(ActionEvent event) {

        Button btn = (Button) event.getSource();
        btn.setDisable(true);

        // Récupérer les informations des champs
        String nom = TFnom.getText();
        String email = TFemail.getText();
        String desc = TFdesc.getText();
        String cat = TFcat.getValue();


        if (nom.isEmpty() || email.isEmpty() || desc.isEmpty() || cat == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs manquants");
            alert.setContentText("Veuillez remplir tous les champs avant de soumettre.");
            alert.showAndWait();
            btn.setDisable(false);
            return;
        }


        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Email invalide");
            alert.setContentText("Veuillez entrer une adresse email valide (ex: exemple@gmail.com).");
            alert.showAndWait();
            btn.setDisable(false);
            return;
        }


        Reclamation r = new Reclamation(nom, email, desc, cat, 0);

        try {

            reclamationService.ajouter(r);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText("Réclamation ajoutée");
            successAlert.setContentText("Votre réclamation a été ajoutée avec succès !");
            successAlert.showAndWait();

            // Vider les champs après l'ajout
            TFnom.clear();
            TFemail.clear();
            TFdesc.clear();
            TFcat.setValue(null);

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Problème lors de l'ajout");
            errorAlert.setContentText("Erreur lors de l'ajout de la réclamation : " + e.getMessage());
            errorAlert.showAndWait();
        }

        btn.setDisable(false);
    }


    @FXML
    void afficher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_reclamation.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setTitle("Liste des réclamations");
            newStage.setScene(new Scene(root));


            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficher2(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_reponse.fxml"));
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Liste des Réponses");
            stage.setScene(new Scene(root));


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   /* @FXML
    void afficher1(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_reponse.fxml"));
            Parent root = loader.load();

            // Création d'une nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setTitle("ajouter_reponse"); // Définir le titre
            newStage.setScene(new Scene(root));

            // Afficher la nouvelle fenêtre
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Affiche l'erreur dans la console
        }
    }*/



}



