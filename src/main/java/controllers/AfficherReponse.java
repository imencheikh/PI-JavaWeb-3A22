package controllers;

import esprit.tn.entities.Reclamation;
import esprit.tn.entities.Reponse;
import esprit.tn.services.ReclamationService;
import esprit.tn.services.ReponseService;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AfficherReponse {

    @FXML
    private ListView<VBox> listViewreponse;

    private final ReponseService RS = new ReponseService();

    @FXML
    void initialize() {

        List<Reponse> reponseList = RS.getAll();

        if (reponseList.isEmpty()) {

            listViewreponse.getItems().add(createEmptyMessage());
        } else {
            for (Reponse rec : reponseList) {
                listViewreponse.getItems().add(createCard(rec));
            }
        }
    }

    private VBox createEmptyMessage() {
        VBox box = new VBox();
        Label label = new Label("Aucune Réponse trouvée.");
        label.setFont(new Font("Arial", 16));
        label.setTextFill(Color.RED);
        box.getChildren().add(label);
        return box;
    }

    private VBox createCard(Reponse rec) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setStyle("-fx-background-color: #FFFFFF; " +  // Blanc
                "-fx-background-radius: 15; " +
                "-fx-padding: 12px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);");
        // ID de la reponse
        Label idReponse = new Label("🆔 ID Reponse: " + rec.getId_reponse());
        idReponse.setFont(new Font("Arial", 14));
        idReponse.setTextFill(Color.BLACK);
        // ID de la réclamation
        Label idReclamation = new Label("🆔 ID Reclamation: " + rec.getId_reclamation());
        idReclamation.setFont(new Font("Arial", 14));
        idReclamation.setTextFill(Color.BLACK);

        // Réponse associée à la réclamation
        Label reponse = new Label("💬 Réponse: " + (rec.getReponse() != null ? rec.getReponse() : "Aucune réponse"));
        reponse.setFont(new Font("Arial", 12));
        reponse.setWrapText(true);
        reponse.setTextFill(Color.BLACK);


        // Boutons Modifier, Supprimer et Répondre
        HBox buttonsBox = new HBox(10);
        Button modifyButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");
        //Button responseButton = new Button("Répondre");

        // Action du bouton Modifier
       // modifyButton.setOnAction(event -> modifier(rec));

        // Action du bouton Supprimer
        deleteButton.setOnAction(event -> {
            RS.supprimer(rec.getId_reponse());
            listViewreponse.getItems().remove(card);
           // System.out.println("Réponse supprimée : " + rec.getReponse());
        });

        // Action du bouton Répondre
      //  responseButton.setOnAction(event -> afficher1(rec));

        buttonsBox.getChildren().addAll(modifyButton, deleteButton);

        // Ajouter tous les éléments à la carte
        card.getChildren().addAll(idReponse,idReclamation, reponse,  buttonsBox);
        return card;
    }

}


