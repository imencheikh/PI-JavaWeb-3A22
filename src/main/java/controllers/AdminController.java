package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {
    @FXML
    private ListView<String> userListView;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        loadUsers();
    }

    private void loadUsers() {
        Connection conn = DatabaseConnection.getInstance();
        String query = "SELECT username FROM users WHERE username != 'admin'";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userListView.getItems().add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteUser(ActionEvent event) {
        String selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Connection conn = DatabaseConnection.getInstance();
            String query = "DELETE FROM users WHERE username = ?";

            try {
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, selectedUser);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    userListView.getItems().remove(selectedUser);
                    showAlert("Success", "User deleted successfully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Please select a user to delete.");
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        switchScene(event, "/login.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
