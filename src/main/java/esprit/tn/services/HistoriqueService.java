package esprit.tn.services;

import esprit.tn.main.DatabaseConnection;
import esprit.tn.entities.Historique;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoriqueService {
    private Connection conn;

    public HistoriqueService() {
        conn = DatabaseConnection.getInstance().getCnx();
    }

    public void enregistrerAction(String action, int idEvent, String details) {
        String query = "INSERT INTO historique (action, idEvent, details) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, action);
            pstmt.setInt(2, idEvent);
            pstmt.setString(3, details);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
