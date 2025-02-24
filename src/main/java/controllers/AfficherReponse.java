package controllers;

import esprit.tn.entities.Reponse;
import esprit.tn.services.ReponseService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class AfficherReponse {

    @FXML
    private ListView<VBox> listViewreponse;

    private final ReponseService RS = new ReponseService();

    // 🔹 Constructeur par défaut (Obligatoire pour FXML)
    public AfficherReponse() {
    }

    @FXML
    void initialize() {
        if (listViewreponse == null) {
            System.err.println("Erreur: listViewreponse n'est pas injecté via FXML !");
            return;
        }

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

        // 🔹 Nom de l'utilisateur associé à la réclamation
        Label nom = new Label("👤 Utilisateur: " + rec.getNom_utilisateur());
        nom.setFont(new Font("Arial", 12));
        nom.setWrapText(true);
        nom.setTextFill(Color.BLACK);
      // Email
        Label email = new Label("📧 Email: " + rec.getEmail());
        email.setFont(new Font("Arial", 12));
        email.setWrapText(true);
        email.setTextFill(Color.BLACK);
        //description
        Label description = new Label("📝 Reclamation: " + rec.getDescription());
        description.setFont(new Font("Arial", 12));
        description.setWrapText(true);
        description.setTextFill(Color.RED);
        // 🔹 Réponse associée à la réclamation

        Label reponse = new Label("💬 Réponse: " + rec.getReponse());
        reponse.setFont(new Font("Arial", 12));
        reponse.setWrapText(true);
        reponse.setTextFill(Color.STEELBLUE);

        // 🔹 Boutons Modifier et Supprimer
        HBox buttonsBox = new HBox(10);
        Button modifyButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");

        // 🗑️ Action du bouton Supprimer
        deleteButton.setOnAction(event -> supprimerReponse(rec, card));

        buttonsBox.getChildren().addAll(modifyButton, deleteButton);

        // 🔹 Ajouter tous les éléments à la carte
        card.getChildren().addAll(nom,email,description, reponse, buttonsBox);
        return card;
    }

    // ✅ Méthode pour supprimer une réponse
    private void supprimerReponse(Reponse rec, VBox card) {
        RS.supprimer(rec.getId_reponse());

        // 🔹 Suppression correcte de l'élément dans ListView
        listViewreponse.getItems().removeIf(item -> item == card);
    }



}
