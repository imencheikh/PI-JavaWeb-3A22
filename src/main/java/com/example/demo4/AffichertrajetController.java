/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temtransporte file, choose Tools | Temtransportes
 * and open the temtransporte in the editor.
 */
package com.example.demo4;

import com.example.demo4.entities.trajet;
import com.example.demo4.entities.transport;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.demo4.services.trajetService;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.web.WebView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
    import com.example.demo4.services.transportService;
import javafx.scene.input.MouseEvent;


/**
 * FXML Controller class
 *
 * @author asus
 */
public class AffichertrajetController implements Initializable {
   // Label qui affiche le total

    private long totalAmount = 1000L; // Montant fictif (10,00 €)
    @FXML
    private Label totalCapaciteLabel;
    @FXML
    private TableView<trajet> tabletrajet;
    transportService ab=new transportService();
    @FXML
    private TableColumn<trajet, Integer> iduserTv;
    @FXML
    private TableColumn<trajet, Integer> idevTv;
    @FXML
    private TableColumn<trajet, Date> datePartTv;
    @FXML
    private TableColumn<trajet, String> descriptionevTv;
    @FXML
    private TableColumn<trajet, String> departureevTv;
    @FXML
    private TableColumn<trajet, String> destinationevTv;
    @FXML
    private TableColumn<trajet, String> scheduleevTv;
    @FXML
    private Label capaciteevLabel;
    @FXML
    private TextField descriptionevField;

    @FXML
    private TextField idread;
    @FXML
    private TextField iduserField;
    @FXML
    private TextField idevField;
    @FXML
    private DatePicker datepartField;
    @FXML
    private TextField chercherevField;



    @FXML
    private TextField departureevField;
    @FXML
    private TextField destinationevField;
    @FXML
    private TextField scheduleevField;



    trajetService Ps=new trajetService();
    @FXML
    private TextField datepartField1;

    /**
     * Initializes the controller class.
     */
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            // TODO

            getTrajet();



        }
    @FXML
    private void ajoutertransport(ActionEvent ev) {
        try {
            //navigation
            Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ajoutertransport.fxml")));
            chercherevField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    private void recherchertransport(KeyEvent ev) {
        try {
            List<transport> transport = ab.chercherev(chercherevField.getText());

            int row = 0;
            int column = 0;
            for (int i = 0; i < transport.size(); i++) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("transport.fxml"));
                AnchorPane pane = loader.load();

                transportController controller = loader.getController();
                controller.settransport(transport.get(i));
                controller.setIdev(transport.get(i).getId());





            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


    }
    @FXML
    private void modifiertrajet(ActionEvent ev) throws SQLException {

        trajet pa = new trajet();
        pa.setId(Integer.valueOf(idread.getText()));
        pa.setTransports_id(Integer.valueOf(idevField.getText()));
        pa.setId_user(Integer.valueOf(iduserField.getText()));
        Date d=Date.valueOf(datepartField.getValue());
        pa.setCreated(d);
        pa.setStatut(descriptionevField.getText());
        pa.setDeparture(departureevField.getText());
        pa.setDestination(destinationevField.getText());
        pa.setSchedule(scheduleevTv.getText());


        Ps.modifiertrajet(pa);
        resetPart();
        getComment();

    }

    @FXML
    private void reserverev(MouseEvent ev) throws SQLException {

        trajet pa = new trajet();
        pa.setId(Integer.valueOf(idread.getText()));
        pa.setTransports_id(Integer.valueOf(idevField.getText()));
        pa.setId_user(Integer.valueOf(iduserField.getText()));
        Date d=Date.valueOf(datepartField.getValue());
        pa.setCreated(d);
        pa.setStatut(descriptionevField.getText());
        pa.setDeparture(departureevField.getText());
        pa.setDestination(destinationevField.getText());
        pa.setSchedule(scheduleevTv.getText());

        Ps.ajouterreserv(pa);
        resetPart();
        getComment();






    }

    @FXML
    private void supprimertrajet(ActionEvent ev) {
        trajet p = tabletrajet.getItems().get(tabletrajet.getSelectionModel().getSelectedIndex());

        try {
            Ps.Deletetrajet(p);
        } catch (SQLException ex) {
            Logger.getLogger(AjoutertransportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("trajet delete");
        alert.setContentText("trajet deleted successfully!");
        alert.showAndWait();
        getComment();
    }

    @FXML
    private void choisirtrajet(MouseEvent ev)  throws IOException {

        trajet part = tabletrajet.getItems().get(tabletrajet.getSelectionModel().getSelectedIndex());

        idread.setText(String.valueOf(part.getId()));
        idevField.setText(String.valueOf(part.getTransports_id()));
        iduserField.setText(String.valueOf(part.getId_user()));
        datepartField1.setText(String.valueOf(part.getCreated()));
        descriptionevField.setText(String.valueOf(part.getStatut()));
        departureevField.setText(String.valueOf(part.getDeparture()));
        destinationevField.setText(String.valueOf(part.getDestination()));
        scheduleevField.setText(String.valueOf(part.getSchedule()));


    }


    public void getTrajet(){
        try {
            List<trajet> part = Ps.recupererTrajet();
            ObservableList<trajet> olp = FXCollections.observableArrayList(part);
            tabletrajet.setItems(olp);
            iduserTv.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            idevTv.setCellValueFactory(new PropertyValueFactory<>("transports_id"));
            datePartTv.setCellValueFactory(new PropertyValueFactory<>("created"));
            descriptionevTv.setCellValueFactory(new PropertyValueFactory<>("statut"));
            destinationevTv.setCellValueFactory(new PropertyValueFactory<>("destination"));
            departureevTv.setCellValueFactory(new PropertyValueFactory<>("departure"));
            scheduleevTv.setCellValueFactory(new PropertyValueFactory<>("schedule"));

            // Rendre le label invisible


            // Vérifier si le trajet contient des éléments

        } catch (SQLException ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }

    public void getComment(){
        try {


            // TODO
            List<trajet> part = Ps.recupererComment();
            ObservableList<trajet> olp = FXCollections.observableArrayList(part);
            tabletrajet.setItems(olp);
            iduserTv.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            idevTv.setCellValueFactory(new PropertyValueFactory<>("transports_id"));
            datePartTv.setCellValueFactory(new PropertyValueFactory<>("created"));
            descriptionevTv.setCellValueFactory(new PropertyValueFactory<>("statut"));
            destinationevTv.setCellValueFactory(new PropertyValueFactory<>("destination"));
            departureevTv.setCellValueFactory(new PropertyValueFactory<>("departure"));
            scheduleevTv.setCellValueFactory(new PropertyValueFactory<>("schedule"));
            // this.delete();
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    public void resetPart() {
        idread.setText("");
        idevField.setText("");
        iduserField.setText("");
        datepartField.setValue(null);
    }

























}


 