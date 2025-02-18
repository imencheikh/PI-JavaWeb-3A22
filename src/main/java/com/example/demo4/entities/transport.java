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
public class transport extends categorie {

   
        private int id, capacite;



    private String name,image,statut;
    private Date updated;

    public categorie categorie;
    public int categories_id;
    public transport() {
    }

    public transport(int id, String name, String image, String statut, Date updated, int capacite, int categories_id) {
        this.id = id;



        this.name = name;

        this.image = image;
        this.statut = statut;
        this.updated = updated;
        this.capacite = capacite;
        this.categories_id = categories_id;
    }
    public transport(String name, String image, String statut, Date updated, int capacite, int categories_id) {



        this.name = name;

        this.image = image;
        this.statut = statut;
        this.updated = updated;
        this.capacite = capacite;
        this.categories_id = categories_id;
    }
    
    
     public transport(int id, String name, String image, String statut, int capacite, int categories_id) {
        this.id = id;



        this.name = name;

        this.image = image;
        this.statut = statut;
        this.capacite = capacite;
         this.categories_id = categories_id;
        
    }
    
    
     //****************** getters ****************

    public int getId() {
        return id;
    }





    public String getImage() {
        return image;
    }

    public String getStatut() {
        return statut;
    }

    public Date getUpdated() {
        return updated;
    }

    
    //****************** setters ****************

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }



    public void setImage(String image) {
        this.image = image;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }





    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    public int getCapacite() {
        return capacite;
    }


    public int getCategories_id() {
        return categories_id;
    }
    public void setCategories_id(int categories_id) {
        this.categories_id = categories_id;
    }

    public categorie getcategorie() {
        return categorie;
    }
    public void setCategorie(categorie categorie) {
        this.categorie = categorie;
    }


    @Override
    public String toString() {
        return "transport{" + "id=" + id+  ", name=" + name + ", image=" + image + ", statut=" + statut + ", updated=" + updated+  ", capacite=" + capacite +  ", categories_id=" + categories_id +  '}';
    }
    
    
    
    
    
    
}
