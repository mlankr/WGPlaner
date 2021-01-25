package com.wgplaner.dao;

import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;
import com.wgplaner.model.Termin;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class TerminDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    public Termin getByDate(Date date) {
        List<Termin> alleTermin = null;
        try {
            alleTermin = dbService.getTerminDao().queryForAll();
            for (Termin t : alleTermin) {
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
