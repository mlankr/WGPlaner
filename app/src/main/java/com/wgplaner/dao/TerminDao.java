package com.wgplaner.dao;

import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;
import com.wgplaner.model.Termin;

import java.sql.Date;
import java.sql.SQLException;

public class TerminDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    // Get Termin by Date
    public Termin getByDate(Date date) {
        try {
            for (Termin t : dbService.getTerminDao().queryForAll()) {
                if (date.toString().equals(t.getDate().toString())) {
                    return t;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
