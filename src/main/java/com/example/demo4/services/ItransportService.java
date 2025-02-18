/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temtransporte file, choose Tools | Temtransportes
 * and open the temtransporte in the editor.
 */
package com.example.demo4.services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author asus
 */
public interface ItransportService<T> {
    
       public void ajoutertransport(T t) throws SQLException;
    public void modifiertransport(T t) throws SQLException;
    public void supprimertransport(T t) throws SQLException;
    public List<T> recuperertransport() throws SQLException;
    
}
