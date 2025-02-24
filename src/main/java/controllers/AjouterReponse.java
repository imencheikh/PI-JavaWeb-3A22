package controllers;

import esprit.tn.entities.Reclamation;
import esprit.tn.entities.Reponse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import esprit.tn.services.ReponseService;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterReponse {

    // Utilisation d'un nom clair pour le service de réponse
    private final ReponseService reponseService = new ReponseService();

    @FXML
    private TextField TFid;

    @FXML
    private TextField TFcat;

    @FXML
    private TextField TFnom; // Champ pour le nom de la réclamation

    @FXML
    private TextField TFemail; // Champ pour l'email de la réclamation

    @FXML
    private TextField TFdesc;

    @FXML
    private TextField TFrep;

    private Reclamation reclamation;

    // Initialise les champs avec les données de la réclamation
    public void initData(Reclamation reclamation) {
        if (reclamation != null) {
            TFnom.setText(reclamation.getNom_utilisateur());
            TFemail.setText(reclamation.getEmail());
            TFdesc.setText(reclamation.getDescription());
            TFcat.setText(reclamation.getCategorie());
            TFid.setText(String.valueOf(reclamation.getId_reclamation()));
        }
    }

    @FXML
    void ajouter(ActionEvent actionEvent) {
        String reponse = TFrep.getText();

        // Vérifier si le champ réponse est vide
        if (reponse.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champ vide", "Veuillez saisir une réponse !");
            return;
        }

        int idrec = Integer.parseInt(TFid.getText());
        Reponse r = new Reponse(idrec, reponse);
        reponseService.ajouter(r);

        // Affichage du message de confirmation
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Ajout de réponse effectué !");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Pas d'en-tête
        alert.setContentText(content);
        alert.showAndWait();
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
}
