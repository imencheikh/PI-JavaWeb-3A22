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

public class ModifierReclamation {

    private final ReclamationService reclamationService = new ReclamationService();
    @FXML
    private TextField TFnom;

    @FXML
    private TextField TFemail;

    @FXML
    private TextField TFdesc;
    @FXML
    private TextField TFid;
    @FXML
    private Button enregistrer;
    private Reclamation reclamation;


    public void initData(Reclamation reclamation) {

        this.reclamation = reclamation;
        if (reclamation != null) {
            TFnom.setText(reclamation.getNom_utilisateur()); // Afficher le titre
            TFemail.setText(reclamation.getEmail()); // Afficher la description
            TFdesc.setText(reclamation.getDescription()); // Afficher la date
           TFid.setText(String.valueOf(reclamation.getId_reclamation()));
        }
    }

    @FXML
 void save() {



        // Récupérer les informations des champs
        reclamation.setNom_utilisateur( TFnom.getText());
        reclamation.setEmail(TFemail.getText());
        reclamation.setDescription(TFdesc.getText());

        reclamationService.enregistrer(reclamation);
    }
}





