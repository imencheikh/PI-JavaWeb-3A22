

package esprit.tn.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LocalisationEvent {

    @FXML
    private WebView webView;

    private String location;

    public void setLocation(String location) {
        this.location = location;
        loadMap();
    }

    private void loadMap() {
        WebEngine webEngine = webView.getEngine();
        String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + location.replace(" ", "+");
        webEngine.load(mapUrl);
    }
}
