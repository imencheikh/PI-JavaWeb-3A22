package controllers;

import esprit.tn.entities.Reclamation;
import esprit.tn.services.ReclamationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;



public class AfficherReclamation {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<VBox> listViewReclamations;

    private final ReclamationService RS = new ReclamationService();

    @FXML
    void initialize() {

        List<Reclamation> reclamationList = RS.getAll();

        if (reclamationList.isEmpty()) {

            listViewReclamations.getItems().add(createEmptyMessage());
        } else {
            for (Reclamation rec : reclamationList) {
                listViewReclamations.getItems().add(createCard(rec));
            }
        }
    }

    private VBox createCard(Reclamation rec) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setStyle("-fx-background-color: #FFFFFF; " +  // Blanc
                "-fx-background-radius: 15; " +
                "-fx-padding: 12px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);");

        // Nom de l'utilisateur
        Label nom = new Label();
        Text symbolNom = new Text("👤 ");
        symbolNom.setFill(Color.web("#FF5722")); // Changer la couleur du symbole (ici orange)

        // Nom avec texte en gras et couleur orange
        Text textNom = new Text(rec.getNom_utilisateur());
        textNom.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Texte en gras
        textNom.setFill(Color.web("#FF5722")); // Texte en orange

        // Combine le symbole et le texte dans un TextFlow
        nom.setGraphic(new TextFlow(symbolNom, textNom));


        // Email de l'utilisateur
        Label email = new Label("📧 " + rec.getEmail());
        email.setFont(new Font("Arial", 12));
        email.setTextFill(Color.BLACK); // Changer la couleur en noir


        // Description de la réclamation
        Label description = new Label("📝 " + rec.getDescription());
        description.setFont(new Font("Arial", 12));
        description.setWrapText(true);
        description.setTextFill(Color.BLACK);

        // Boutons Modifier et Supprimer
        HBox buttonsBox = new HBox(10);
        Button modifyButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");
        Button responseButton = new Button("Repondre");

        // Styles des boutons
        modifyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");
        responseButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");


        // Modifier Button action
        modifyButton.setOnAction(event -> modifier(rec));

        // Suppression de la réclamation
        deleteButton.setOnAction(event -> {
            RS.supprimer(rec.getId_reclamation());
            listViewReclamations.getItems().remove(card);
            System.out.println("Réclamation supprimée : " + rec.getNom_utilisateur());
        });
        responseButton.setOnAction(event -> afficher1(rec));


        buttonsBox.getChildren().addAll(modifyButton, deleteButton, responseButton);


        card.getChildren().addAll(nom, email, description, buttonsBox);
        return card;
    }


    // Ouvrir la fenêtre de modification
    @FXML
    private void modifier(Reclamation rec) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier_reclamation.fxml"));

        try {
            // Charger la fenêtre FXML de modification
            Parent root = loader.load();

            // Passer la réclamation à la fenêtre ModifierReclamation
            ModifierReclamation controller = loader.getController();


            controller.initData(rec);

            // Création de la nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setTitle("Modifier Réclamation");
            newStage.setScene(new Scene(root));

            // Afficher la fenêtre
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Affiche l'erreur dans la console
        }
    }

    private VBox createEmptyMessage() {
        VBox box = new VBox();
        Label label = new Label("Aucune réclamation trouvée.");
        label.setFont(new Font("Arial", 16));
        label.setTextFill(Color.RED);
        box.getChildren().add(label);
        return box;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void afficher1(Reclamation rec) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_reponse.fxml"));

        try {
            // Charger la fenêtre FXML d'ajout de réponse
            Parent root = loader.load();

            // Récupérer le contrôleur associé au fichier FXML
            AjouterReponse controller = loader.getController();

            // Passer la réclamation à la fenêtre d'ajout de réponse
            controller.initData(rec);

            // Création de la nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setTitle("Ajouter Reponse Réclamation");
            newStage.setScene(new Scene(root));

            // Afficher la fenêtre
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Affiche l'erreur dans la console
        }
    }


    @FXML
    private void rechercherReclamation() {
        String emailRecherche = searchField.getText().trim().toLowerCase();

        listViewReclamations.getItems().clear(); // Effacer la liste actuelle

        List<Reclamation> resultats = RS.getAll().stream()
                .filter(rec -> rec.getEmail().toLowerCase().contains(emailRecherche))
                .toList();

        if (resultats.isEmpty()) {
            listViewReclamations.getItems().add(createEmptyMessage());
        } else {
            for (Reclamation rec : resultats) {
                listViewReclamations.getItems().add(createCard(rec));
            }
        }




    }


    @FXML
    void afficherstat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_statistique.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setTitle("statistique");
            newStage.setScene(new Scene(root));


            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



