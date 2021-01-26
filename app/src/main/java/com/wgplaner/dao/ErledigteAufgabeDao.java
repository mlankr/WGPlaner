package com.wgplaner.dao;

import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;
import com.wgplaner.model.ErledigteAufgabe;

import java.sql.SQLException;
import java.util.List;

public class ErledigteAufgabeDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    // Create new ErledigteAufgabe using ErledigteAufgabeDao
    public int create(ErledigteAufgabe erledigteAufgabe) {
        try {
            return dbService.getErledigteAufgabeDao().create(erledigteAufgabe);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    // Get all ErledigteAufgaben using ErledigteAufgabeDao
    public List<ErledigteAufgabe> getAlle() {
        try {
            return dbService.getErledigteAufgabeDao().queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
