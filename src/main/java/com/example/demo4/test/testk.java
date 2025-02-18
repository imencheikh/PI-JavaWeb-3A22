/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temtransporte file, choose Tools | Temtransportes
 * and open the temtransporte in the editor.
 */
package com.example.demo4.test;

import java.sql.Date;
import java.sql.SQLException;

import com.example.demo4.services.transportService;
import com.example.demo4.services.trajetService;


/**
 *
 * @author asus
 */
public class test {
    
      public static void main(String[] args) {   
          
          Date d=Date.valueOf("2022-06-11");
          Date d1=Date.valueOf("2020-04-12");
        try {
            //kifeh ya9ra el orde fel base de donnée , kifeh 3raf nom ev bch n3amarha f nom 

            
            


            trajetService ps=new trajetService();
            //ps.trajet(p);
          //  ps.trajet(p1);
           // ps.trajet(p2);

            //ps.trajet(p2);
            System.out.println("");
            transportService ab = new transportService();
            //ab.ajoutertransport(e1);
            //ab.ajoutertransport(e2);
           // ab.ajoutertransport(e3);
            //ab.ajouter(p);
            //ab.modifiertransport(e);
            //ab.supprimertransport(e3);
            System.out.println(ab.recuperertransport());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
