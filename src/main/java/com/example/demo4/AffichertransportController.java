/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temtransporte file, choose Tools | Temtransportes
 * and open the temtransporte in the editor.
 */
package com.example.demo4;

import com.example.demo4.entities.transport;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import com.example.demo4.services.transportService;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AffichertransportController implements Initializable {

    @FXML
    private GridPane gridev;

    transportService ab=new transportService();
    @FXML
    private TextField chercherevField;
    @FXML
    private Button ajouter;
    @FXML
    private Button mailButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        affichertransport();
               
    }    


    @FXML
    private void ajoutertransport(ActionEvent ev) {
      try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("ajoutertransport.fxml"));
            chercherevField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void affichertransport(){
         try {
            List<transport> transport = ab.recuperertransport();
            gridev.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < transport.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("transport.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                transportController controller = loader.getController();
                controller.settransport(transport.get(i));
                controller.setIdev(transport.get(i).getId());
                gridev.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }

            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }

    @FXML
    private void recherchertransport(KeyEvent ev) {
        try {
            List<transport> transport = ab.chercherev(chercherevField.getText());
            gridev.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < transport.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("transport.fxml"));
                AnchorPane pane = loader.load();         
                //passage de parametres
                transportController controller = loader.getController();
                controller.settransport(transport.get(i));
                controller.setIdev(transport.get(i).getId());
                gridev.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }



    @FXML
    private void triertransport(ActionEvent ev) throws SQLException {
        try {
            List<transport> transport = ab.trierev();
            gridev.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < transport.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("transport.fxml"));
                AnchorPane pane = loader.load();      
                //passage de parametres
                transportController controller = loader.getController();
                controller.settransport(transport.get(i));
                controller.setIdev(transport.get(i).getId());
                gridev.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
 
    }
    
}
