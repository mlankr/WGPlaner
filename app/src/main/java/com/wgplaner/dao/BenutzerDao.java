package com.wgplaner.dao;


import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;
import com.wgplaner.model.Benutzer;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BenutzerDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    // Create new Benutzer using BenutzerDao
    public int create(Benutzer benutzer) {
        try {
            return dbService.getBenutzerDao().create(benutzer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Update Benutzer using BenutzerDao
    public int update(Benutzer benutzer) {
        try {
            return dbService.getBenutzerDao().update(benutzer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get Benutzer by ID using BenutzerDao
    public Benutzer get(int id) {
        try {
            return dbService.getBenutzerDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get Benutzer by Email using BenutzerDao
    public Benutzer getByEmail(String email) {
        List<Benutzer> benutzerList = allBenutzer();
        for (Benutzer b : benutzerList) {
            if (email.toLowerCase().equals(b.getEmailAdresse().toLowerCase())) {
                return b;
            }
        }
        return null;
    }

    // Get All Benutzer using BenutzerDao
    public List<Benutzer> allBenutzer() {
        try {
            return dbService.getBenutzerDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
