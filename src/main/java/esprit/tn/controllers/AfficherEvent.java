package esprit.tn.controllers;

import esprit.tn.entities.Events;
import esprit.tn.services.EventService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class AfficherEvent {

    @FXML
    private ListView<Events> eventListView;

    EventService ev = new EventService();

    @FXML
    void initialize() {
        List<Events> eventsList = ev.getAll();

        // Personnalisation de l'affichage des événements
        eventListView.setCellFactory(new Callback<ListView<Events>, ListCell<Events>>() {
            @Override
            public ListCell<Events> call(ListView<Events> listView) {
                return new ListCell<Events>() {
                    @Override
                    protected void updateItem(Events event, boolean empty) {
                        super.updateItem(event, empty);
                        if (empty || event == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            VBox eventBox = new VBox();
                            eventBox.setSpacing(5);
                            eventBox.setStyle("-fx-background-color: #F8F9FA; -fx-padding: 15px; -fx-border-radius: 10px; -fx-border-color: #D1D1D1;");

                            // Nom en GRAND
                            Text eventName = new Text(event.getNomEv());
                            eventName.setFont(new Font("Arial", 20));
                            eventName.setStyle("-fx-font-weight: bold; -fx-fill: #2C3E50;");

                            // Date et Description
                            Text eventDate = new Text("📅 " + event.getDateEvent());
                            eventDate.setFont(new Font("Arial", 14));
                            eventDate.setStyle("-fx-fill: #7B7B7B;");

                            Text eventDesc = new Text(event.getDescription());
                            eventDesc.setFont(new Font("Arial", 14));
                            eventDesc.setStyle("-fx-fill: #555;");

                            eventBox.getChildren().addAll(eventName, eventDate, eventDesc);
                            setGraphic(eventBox);
                        }
                    }
                };
            }
        });

        // Ajouter les événements à la ListView
        eventListView.getItems().setAll(eventsList);

        // Événement de clic sur un élément
        eventListView.setOnMouseClicked(event -> {
            Events selectedEvent = eventListView.getSelectionModel().getSelectedItem();
            System.out.println("Événement sélectionné : " + (selectedEvent != null ? selectedEvent.getNomEv() : "Aucun"));

            if (selectedEvent != null) {
                ouvrirModifierEvent(selectedEvent);
            }
        });
    }

    private void ouvrirModifierEvent(Events event) {
        try {
            System.out.println("Ouverture de la fenêtre de modification...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
            Parent root = loader.load();

            ModifierEvent controller = loader.getController();
            controller.setEvent(event);

            // Passez le contrôleur d'affichage pour mettre à jour la liste après modification ou suppression
            controller.setAfficherEventController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier / Supprimer un Événement");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Vérifier si une erreur se produit
            System.err.println("Erreur lors du chargement de ModifierEvent.fxml !");
        }
    }

    // Méthode pour rafraîchir la liste des événements
    public void refreshList() {
        // Recharger les événements depuis le service
        List<Events> eventsList = ev.getAll();

        // Mettre à jour la ListView
        eventListView.getItems().setAll(eventsList);

        // Forcer la réaffichage de la ListView
        eventListView.requestLayout();  // Demander une nouvelle disposition
    }
}
