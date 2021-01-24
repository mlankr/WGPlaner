package com.wgplaner.dao;

import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;
import com.wgplaner.model.Aufgabe;
import com.wgplaner.model.ErledigteAufgabe;

import java.sql.SQLException;
import java.util.List;

public class ErledigteAufgabeDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    public int create(ErledigteAufgabe aufgabe) {
        try {
            return dbService.getErledigteAufgabeDao().create(aufgabe);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public List<ErledigteAufgabe> getErledigteAufgabe() {
        try {
            return dbService.getErledigteAufgabeDao().queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
