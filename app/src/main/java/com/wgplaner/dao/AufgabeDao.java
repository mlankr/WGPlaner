package com.wgplaner.dao;


import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;

import com.wgplaner.model.Aufgabe;
import java.sql.SQLException;
import java.util.List;

public class AufgabeDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    public int create(Aufgabe aufgabe) {
        try {
            return dbService.getAufgabeDao().create(aufgabe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int edit(Aufgabe aufgabe) {
        try {
            return dbService.getAufgabeDao().update(aufgabe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Aufgabe aufgabe) {
        try {
            return dbService.getAufgabeDao().delete(aufgabe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Aufgabe getAufgabe(int id) {
        try {
            return dbService.getAufgabeDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Aufgabe> allAufgabe() {
        try {
            return dbService.getAufgabeDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Aufgabe findAufgabe(String bezeichnung) {
        List<Aufgabe> all = allAufgabe();
        if(!all.isEmpty()) {
            for (Aufgabe a : all) {
                if (a.getBezeichnung().equals(bezeichnung)) {
                    return a;
                }
            }
        }
        return null;
    }
}
