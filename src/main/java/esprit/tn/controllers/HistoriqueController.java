package esprit.tn.controllers;
import esprit.tn.entities.Historique;
import esprit.tn.main.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class HistoriqueController {

    @FXML private TableView<Historique> historiqueTable;
    @FXML private TableColumn<Historique, String> colAction;
    @FXML private TableColumn<Historique, Timestamp> colDate;
    @FXML private TableColumn<Historique, Integer> colEventId;
    @FXML private TableColumn<Historique, String> colDetails;

    private Connection conn;

    @FXML
    public void initialize() {
        conn = DatabaseConnection.getInstance().getCnx();
        afficherHistorique();
    }

    public void afficherHistorique() {
        ObservableList<Historique> historiqueList = FXCollections.observableArrayList();
        String query = "SELECT * FROM historique ORDER BY date_action DESC";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                historiqueList.add(new Historique(
                        rs.getString("action"),
                        rs.getInt("idEvent"),
                        rs.getString("details"),
                        rs.getTimestamp("date_action")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateAction"));

        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));

        historiqueTable.setItems(historiqueList);
    }

}
