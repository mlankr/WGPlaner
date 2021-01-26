package com.wgplaner.dao;


import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;
import com.wgplaner.model.Aufgabe;
import com.wgplaner.model.Benutzer;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AufgabeDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    // Create new Aufgabe using AufgabeDao
    public int create(Aufgabe aufgabe) {
        try {
            return dbService.getAufgabeDao().create(aufgabe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Update the Aufgabe using AufgabeDao
    public int update(Aufgabe aufgabe) {
        try {
            return dbService.getAufgabeDao().update(aufgabe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Delete the Aufgabe using AufgabeDao
    public int delete(Aufgabe aufgabe) {
        try {
            return dbService.getAufgabeDao().delete(aufgabe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get the Aufgabe by ID using AufgabeDao
    public Aufgabe getAufgabe(int id) {
        try {
            return dbService.getAufgabeDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all Aufgaben using AufgabeDao
    public List<Aufgabe> allAufgabe() {
        try {
            return dbService.getAufgabeDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Find Aufgabe by Benutzer using AufgabeDao
    public List<Aufgabe> findByBenutzer(Benutzer benutzer) {
        List<Aufgabe> filteredAufgabe = new LinkedList<>();
        for (Aufgabe a : allAufgabe()) {
            if (a.getBenutzer().equals(benutzer)) {
                filteredAufgabe.add(a);
            }
        }
        return filteredAufgabe;
    }
}
