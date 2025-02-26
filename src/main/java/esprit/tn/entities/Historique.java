package esprit.tn.entities;

import java.sql.Timestamp;

public class Historique {
    private int id;
    private String action;
    private Timestamp dateAction;
    private int idEvent;
    private String details;


    public Historique() {}


    public Historique(String action, int idEvent, String details, Timestamp dateAction) {
        this.action = action;
        this.idEvent = idEvent;
        this.details = details;
        this.dateAction = dateAction;
    }


    public Historique(String action, int idEvent, String details) {
        this.action = action;
        this.idEvent = idEvent;
        this.details = details;
    }

    // Getters et Setters
    public int getId() { return id; }
    public String getAction() { return action; }
    public Timestamp getDateAction() { return dateAction; }
    public int getidEvent() { return idEvent; }
    public String getDetails() { return details; }

    public void setId(int id) { this.id = id; }
    public void setAction(String action) { this.action = action; }
    public void setDateAction(Timestamp dateAction) { this.dateAction = dateAction; }
    public void setidEvent(int idEvent) { this.idEvent = idEvent; }
    public void setDetails(String details) { this.details = details; }
}
