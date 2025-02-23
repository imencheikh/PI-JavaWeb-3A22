package esprit.tn.controllers;

import esprit.tn.entities.Sponsors;
import esprit.tn.services.SponsorService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AfficherSponsor {

    @FXML
    private ListView<Sponsors> sponsorListView;

    @FXML
    private TextField searchField;  // Champ de recherche

    SponsorService sponsorService = new SponsorService();

    @FXML
    void initialize() {
        List<Sponsors> sponsorsList = sponsorService.getAll();

        // Personnalisation de l'affichage des sponsors
        sponsorListView.setCellFactory(new Callback<ListView<Sponsors>, ListCell<Sponsors>>() {
            @Override
            public ListCell<Sponsors> call(ListView<Sponsors> listView) {
                return new ListCell<Sponsors>() {
                    @Override
                    protected void updateItem(Sponsors sponsor, boolean empty) {
                        super.updateItem(sponsor, empty);
                        if (empty || sponsor == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            VBox sponsorBox = new VBox();
                            sponsorBox.setSpacing(5);
                            sponsorBox.setStyle("-fx-background-color: #F8F9FA; -fx-padding: 15px; -fx-border-radius: 10px; -fx-border-color: #D1D1D1;");

                            // Nom en GRAND
                            Text sponsorName = new Text(sponsor.getNomSponsor());
                            sponsorName.setFont(new Font("Arial", 20));
                            sponsorName.setStyle("-fx-font-weight: bold; -fx-fill: #2C3E50;");

                            // Email et Contribution
                            Text sponsorEmail = new Text("✉️ " + sponsor.getEmailSpon());
                            sponsorEmail.setFont(new Font("Arial", 14));
                            sponsorEmail.setStyle("-fx-fill: #7B7B7B;");

                            Text sponsorContribution = new Text("💰 " + sponsor.getContribution() + " DT");
                            sponsorContribution.setFont(new Font("Arial", 14));
                            sponsorContribution.setStyle("-fx-fill: #555;");

                            sponsorBox.getChildren().addAll(sponsorName, sponsorEmail, sponsorContribution);
                            setGraphic(sponsorBox);
                        }
                    }
                };
            }
        });

        // Ajouter les sponsors à la ListView
        sponsorListView.getItems().setAll(sponsorsList);
    }

    // Méthode pour rechercher les sponsors
    @FXML
    void rechercherSponsors() {
        String recherche = searchField.getText().toLowerCase();
        List<Sponsors> sponsorsList = sponsorService.getAll();

        // Filtrer la liste des sponsors par nom
        List<Sponsors> filteredList = sponsorsList.stream()
                .filter(sponsor -> sponsor.getNomSponsor().toLowerCase().contains(recherche))
                .collect(Collectors.toList());

        // Mettre à jour la ListView
        sponsorListView.getItems().setAll(filteredList);
    }

    // Méthode pour trier les sponsors par nom
    @FXML
    void trierSponsors() {
        List<Sponsors> sponsorsList = sponsorService.getAll();

        // Trier la liste des sponsors par nom
        List<Sponsors> sortedList = sponsorsList.stream()
                .sorted((s1, s2) -> s1.getNomSponsor().compareToIgnoreCase(s2.getNomSponsor()))
                .collect(Collectors.toList());

        // Mettre à jour la ListView avec la liste triée
        sponsorListView.getItems().setAll(sortedList);
    }

    // Méthode pour ouvrir la fenêtre de modification d'un sponsor
    private void ouvrirModifierSponsor(Sponsors sponsor) {
        try {
            System.out.println("Ouverture de la fenêtre de modification...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSponsor.fxml"));
            Parent root = loader.load();

            ModifierSponsor controller = loader.getController();
            controller.setSponsor(sponsor);

            // Passez le contrôleur d'affichage pour mettre à jour la liste après modification ou suppression
            controller.setAfficherSponsorController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier / Supprimer un Sponsor");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Vérifier si une erreur se produit
            System.err.println("Erreur lors du chargement de ModifierSponsor.fxml !");
        }
    }

    // Méthode pour rafraîchir la liste des sponsors
    public void refreshList() {
        // Recharger les sponsors depuis le service
        List<Sponsors> sponsorsList = sponsorService.getAll();

        // Mettre à jour la ListView
        sponsorListView.getItems().setAll(sponsorsList);

        // Forcer la réaffichage de la ListView
        sponsorListView.requestLayout();  // Demander une nouvelle disposition
    }
}
