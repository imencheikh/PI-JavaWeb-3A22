package esprit.tn.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class TESTFX extends Application {

    public static void main(String[] args) {
        launch(args);
        DatabaseConnection.getInstance();
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_reclamation.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Reclamation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // e.printStackTrace();
        }











    }









}


