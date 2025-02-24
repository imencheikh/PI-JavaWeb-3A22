package esprit.tn.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StatistiqueService {

    public ObservableList<PieChart.Data> getStatistiquesParCategorie() {
        Map<String, Integer> stats = new HashMap<>();
        String query = "SELECT categorie, COUNT(*) FROM reclamation GROUP BY categorie";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/omayma", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                stats.put(rs.getString(1), rs.getInt(2)); // Récupère les données
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calculer le total des réclamations
        int total = stats.values().stream().mapToInt(Integer::intValue).sum();

        // Convertir les données en format PieChart avec des pourcentages
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / total; // Calcul du pourcentage
            PieChart.Data data = new PieChart.Data(entry.getKey() + " (" + String.format("%.2f", percentage) + "%)", entry.getValue());
            pieChartData.add(data);
        }
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setPrefWidth(400);  // Adjust width
        pieChart.setPrefHeight(300);

        return pieChartData;
    }
}
