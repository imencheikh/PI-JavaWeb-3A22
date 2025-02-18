/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temtransporte file, choose Tools | Temtransportes
 * and open the temtransporte in the editor.
 */
package com.example.demo4.entities;

import java.sql.Date;

/**
 *
 * @author asus
 */
public class trajet extends transport {
    private int id;        
private Date created;    
private int id_user;
private String statut, departure, destination, schedule;
public int transports_id; 
public transport transport;

    public trajet() {
    }

    public trajet(int id, Date date_trajet, int id_user, int transports_id, String statut,String departure,String destination,String schedule) {
        this.id = id;
        this.created = date_trajet;
        this.id_user = id_user;
        this.transports_id = transports_id;
        this.statut = statut;
        this.departure = departure;
        this.destination = destination;
        this.schedule = schedule;
    }
    public trajet(Date date_trajet, int id_user, int transports_id) {
        this.created = date_trajet;
        this.id_user = id_user;
        this.transports_id = transports_id;

    }

    public trajet(int id, Date date_trajet, int id_user, int transports_id, transport transport, String statut,String departure,String destination,String schedule) {
        this.id = id;
        this.created = date_trajet;
        this.id_user = id_user;
        this.transports_id = transports_id;
        this.transport = transport;
        this.statut = statut;
        this.departure = departure;
        this.destination = destination;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public int getId_user() {
        return id_user;
    }

    public int getTransports_id() {
        return transports_id;
    }

    public transport gettransport() {
        return transport;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(Date date_trajet) {
        this.created = date_trajet;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setTransports_id(int transports_id) {
        this.transports_id = transports_id;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
    public String getStatut() {
        return statut;
    }
    public String getDestination() {
        return destination;
    }

    // Setter pour destination
    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Getter pour schedule
    public String getSchedule() {
        return schedule;
    }

    // Setter pour schedule
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public void setDeparture(String departure) {
        this.departure = departure;
    }
    public String getDeparture() {
        return departure;
    }

    public void setTransport(transport transport) {
        this.transport = transport;
    }

    @Override
    public String toString() {
        return "trajet{" + "id=" + id + ", date_trajet=" + created + ", id_user=" + id_user + ", transports_id=" + transports_id +  ", statut=" + statut +  ", departure=" + departure +  ", destination=" + destination +  ", schedule =" + schedule + '}';
    }
    
    
    




}
