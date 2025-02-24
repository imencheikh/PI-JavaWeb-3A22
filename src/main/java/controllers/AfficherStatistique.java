package controllers;

import esprit.tn.services.StatistiqueService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AfficherStatistique {
    @FXML
    private PieChart pieChartCategorie; // Lien avec Scene Builder

    public void initialize() {
        StatistiqueService service = new StatistiqueService();
        pieChartCategorie.setData(service.getStatistiquesParCategorie()); // Chargement des stats

    }


}

