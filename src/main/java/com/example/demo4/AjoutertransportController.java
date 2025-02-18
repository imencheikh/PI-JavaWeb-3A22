/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temtransporte file, choose Tools | Temtransportes
 * and open the temtransporte in the editor.
 */
package com.example.demo4;

import com.example.demo4.entities.transport;
import com.example.demo4.services.categorieService;
import com.example.demo4.services.transportService;
import com.example.demo4.services.trajetService;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;















/**
 * FXML Controller class
 *
 * @author asus
 */
public class AjoutertransportController implements Initializable {

    @FXML
    private TextField descriptionevField;
    @FXML
    private DatePicker updatedevField;
    @FXML
    private TextField capaciteevField;


    @FXML
    private ComboBox<String> idevComboBox;
    @FXML
    private TextField imageevField;
    @FXML
    private TextField nameField;


  
    @FXML
    private TableView<transport> transportTv;
    @FXML
    private TableColumn<transport, String> nomevTv;

    @FXML
    private TableColumn<transport, String> imageevTv;
    @FXML
    private TableColumn<transport, String> updatedevTv;

    @FXML
    private TableColumn<transport, String> descriptionevTv;
    @FXML
    private TableColumn<transport, String> capaciteevTv;

    @FXML
    private TableColumn<transport, String> categories_idevTv;



    

    transportService Ev=new transportService();
    trajetService Pservice =new trajetService();

    
    @FXML
    private TextField idmodifierField;

    @FXML
    private ImageView imageview;
    @FXML
    private TextField rechercher;
    @FXML
    private ImageView QrCode;

    /**
     * Initializes the controller class.
     */
@Override
public void initialize(URL url, ResourceBundle rb) {



    //idLabel.setText("");
    getevs();
    categorieService service = new categorieService();
    List<String> nameCategorieDons = service.getAllNameCategorieDon();
    ObservableList<String> options = FXCollections.observableArrayList(nameCategorieDons);
    idevComboBox.setItems(options);
}




    
  
     private boolean NoDate() {
         LocalDate currentDate = LocalDate.now();     
         LocalDate myDate = updatedevField.getValue();
         int comparisonResult = myDate.compareTo(currentDate);      
         boolean test = true;
        if (comparisonResult < 0) {

        test = true;
        } else if (comparisonResult > 0) {

         test = false;
        }
        return test;
    }
          @FXML
    private void ajoutertransport(ActionEvent ev) {
    
         int part=0;
        if ((nameField.getText().isEmpty())  || (imageevField.getText().length() == 0) ||  (descriptionevField.getText().length() == 0) || (idevComboBox.getValue() == null) ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be empty");
            alert.showAndWait();
        }
       else if (NoDate() == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("la date de updated  doit être aprés la date d'aujourd'hui");
            alert.showAndWait();
        }
       else{     

        transport e = new transport();

            // Récupérer la valeur sélectionnée dans le ComboBox
            String categoryName = idevComboBox.getValue();


            categorieService categorieService = new categorieService();
            int categoryId = categorieService.getCategoryIDFromName(categoryName);
            e.setCategories_id(categoryId);
        e.setName(nameField.getText());
        e.setCapacite(Integer.  parseInt(capaciteevField.getText()));

        e.setStatut(descriptionevField.getText());//feild
        java.util.Date date_debut=java.util.Date.from(updatedevField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date sqlDate = new Date(date_debut.getTime());
        e.setUpdated(sqlDate);
                //




        //lel image
        e.setImage(imageevField.getText());      
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information ");
            alert.setHeaderText("transport add");
            alert.setContentText("transport added successfully!");
            alert.showAndWait();      
        try {
            Ev.ajoutertransport(e);
            reset();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }      
        getevs();
        

    }}
    

    private void reset() {
        nameField.setText("");

        descriptionevField.setText("");
        imageevField.setText("");


        updatedevField.setValue(null);
        capaciteevField.setText("");
    }
    
   public void getevs() {  
         try {
            // TODO
            List<transport> transport = Ev.recuperertransport();
            ObservableList<transport> olp = FXCollections.observableArrayList(transport);
            transportTv.setItems(olp);
            nomevTv.setCellValueFactory(new PropertyValueFactory<>("name"));

            imageevTv.setCellValueFactory(new PropertyValueFactory<>("image"));
             updatedevTv.setCellValueFactory(new PropertyValueFactory<>("updated"));
            descriptionevTv.setCellValueFactory(new PropertyValueFactory<>("statut"));
            capaciteevTv.setCellValueFactory(new PropertyValueFactory<>("capacite"));
             categories_idevTv.setCellValueFactory(new PropertyValueFactory<>("Categorie"));



        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

     
     @FXML
   private void modifiertransport(ActionEvent ev) throws SQLException {
        transport e = new transport();
        e.setId(Integer.parseInt(idmodifierField.getText()));
        e.setName(nameField.getText());
        e.setCapacite(Integer.parseInt(capaciteevField.getText()));

        e.setStatut(descriptionevField.getText()); 
        Date d=Date.valueOf(updatedevField.getValue());
        e.setUpdated(d);
        e.setImage(imageevField.getText());


         Ev.modifiertransport(e);
        reset();
        getevs();         
    }

    @FXML
    private void supprimertransport(ActionEvent ev) {
           transport e = transportTv.getItems().get(transportTv.getSelectionModel().getSelectedIndex());
        try {
            Ev.supprimertransport(e);
        } catch (SQLException ex) {
            Logger.getLogger(AjoutertransportController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("transport delete");
        alert.setContentText("transport deleted successfully!");
        alert.showAndWait();        
        getevs();    
    }

    @FXML
    private void affichertransport(ActionEvent ev) {
         try {
            //navigation
            Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("affichertransport.fxml")));
             nameField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

  
    @FXML

    private void choisirev(MouseEvent ev) throws IOException {
        transport e = transportTv.getItems().get(transportTv.getSelectionModel().getSelectedIndex());

        idmodifierField.setText(String.valueOf(e.getId()));
        nameField.setText(e.getName());
        capaciteevField.setText(String.valueOf(e.getCapacite()));

        imageevField.setText(e.getImage());
        descriptionevField.setText(e.getStatut());



        //lel image
        String path = e.getImage();
               File file=new File(path);
              Image img = new Image(file.toURI().toString());
                imageview.setImage(img);
                

            
    }



    @FXML
    private void affichertrajets(ActionEvent ev) {
         try {

            Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("affichertrajet.fxml")));
             nameField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void uploadImage(ActionEvent ev)throws FileNotFoundException, IOException  {

        Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String DBPath = "C:\\\\xampp\\\\htdocs\\\\imageP\\\\"  + x + ".jpg";
        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path=file.getAbsolutePath();
            Image img = new Image(file.toURI().toString());
            imageview.setImage(img);    
            imageevField.setText(DBPath);
            int b = 0;
            while (b != -1) {
                b = bin.read();
                bou.write(b);
            }
            bin.close();
            bou.close();          
        } else {
            System.out.println("error");
        }
    }


    

    @FXML
    private void rechercherev(KeyEvent ev) {
        
        transportService bs=new transportService();
        transport b= new transport();
        ObservableList<transport>filter= bs.chercherev(rechercher.getText());
        populateTable(filter);
    }
     private void populateTable(ObservableList<transport> branlist){
       transportTv.setItems(branlist);
   
       }







    }


    





    

