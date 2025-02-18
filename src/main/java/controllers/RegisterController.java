package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    void handleRegister(ActionEvent event) {
        Connection conn = DatabaseConnection.getInstance();
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, usernameField.getText());
            pstmt.setString(2, BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt()));
            pstmt.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
