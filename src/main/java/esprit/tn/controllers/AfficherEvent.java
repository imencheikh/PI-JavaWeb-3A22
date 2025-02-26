package esprit.tn.controllers;
import esprit.tn.entities.Events;
import esprit.tn.services.EventService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.Comparator;
import java.util.List ;
import java.util.stream.Collectors;

public class AfficherEvent {
    @FXML
    private Text noResultsText;

    @FXML
    private ListView<Events> eventListView;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> sortComboBox;

    private EventService ev = new EventService();
    private ObservableList<Events> eventsList;
    private ObservableList<Events> filteredEvents; // Liste affichée après la recherche

    @FXML
    void initialize() {
        eventsList = FXCollections.observableArrayList(ev.getAll());
        filteredEvents = FXCollections.observableArrayList(eventsList); // Initialiser avec tous les événements
        setupListView();
        setupSearchAndSort();
    }

    private void setupListView() {
        eventListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Events> call(ListView<Events> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Events event, boolean empty) {
                        super.updateItem(event, empty);
                        if (empty || event == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            VBox eventBox = new VBox(5);
                            eventBox.setStyle("-fx-background-color: #F8F9FA; -fx-padding: 15px; -fx-border-radius: 10px; -fx-border-color: #D1D1D1;");

                            Text eventName = new Text(event.getNomEv());
                            eventName.setFont(new Font("Arial", 20));
                            eventName.setStyle("-fx-font-weight: bold; -fx-fill: #2C3E50;");

                            Text eventDate = new Text("📅 " + event.getDateEvent());
                            eventDate.setFont(new Font("Arial", 14));
                            eventDate.setStyle("-fx-fill: #7B7B7B;");

                            Text eventDesc = new Text("A propos de l'événement: " + event.getDescription());
                            eventDesc.setFont(new Font("Arial", 14));
                            eventDesc.setStyle("-fx-fill: #555;");

                          Text eventLieu = new Text("Lieu : " + event.getLieu());
                            eventLieu.setFont(new Font("Arial", 14));
                            eventLieu.setStyle("-fx-fill: #555;");

                            Text eventSpon = new Text("Sponsorisé par: " + event.getNomSp());
                            eventSpon.setFont(new Font("Arial", 14));
                            eventSpon.setStyle("-fx-fill: #555;");

                            eventBox.getChildren().addAll(eventName, eventDate, eventDesc, eventLieu,eventSpon);
                            setGraphic(eventBox);
                        }
                    }
                };
            }
        });
        eventListView.setItems(filteredEvents);

        eventListView.setOnMouseClicked(event -> {
            Events selectedEvent = eventListView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                ouvrirModifierEvent(selectedEvent);
            }
        });
    }

    private void setupSearchAndSort() {
        sortComboBox.getItems().addAll("Nom", "Date");

        sortComboBox.setOnAction(event -> sortEvents());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchEvents(newValue));
    }

    private void searchEvents(String query) {
        filteredEvents.setAll(eventsList.stream()
                .filter(event -> event.getNomEv().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList()));

        noResultsText.setVisible(filteredEvents.isEmpty());
        eventListView.setItems(filteredEvents);

        sortEvents(); // Appliquer le tri après la recherche
    }

    private void sortEvents() {
        if (sortComboBox.getValue() == null) return;

        Comparator<Events> comparator = sortComboBox.getValue().equals("Nom")
                ? Comparator.comparing(Events::getNomEv, String.CASE_INSENSITIVE_ORDER)
                : Comparator.comparing(Events::getDateEvent);

        FXCollections.sort(filteredEvents, comparator);
    }

    @FXML
    private void ouvrirAjouterEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion des événements");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ouvrirModifierEvent(Events event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
            Parent root = loader.load();
            ModifierEvent controller = loader.getController();
            controller.setEvent(event);
            controller.setAfficherEventController(this);
            Stage stage = new Stage();
            stage.setTitle("Modifier / Supprimer un Événement");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshList() {
        eventsList.setAll(ev.getAll());
        filteredEvents.setAll(eventsList); // Mettre aussi à jour la liste filtrée
        eventListView.requestLayout();
    }

    @FXML
    private void ouvrirHistorique() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Historique.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Historique des événements");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
