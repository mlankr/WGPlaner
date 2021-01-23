package com.wgplaner.dao;


import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;
import com.wgplaner.model.Benutzer;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BenutzerDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    public int create(Benutzer benutzer) {
        try {
            return dbService.getBenutzerDao().create(benutzer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Benutzer get(int id) {
        try {
            return dbService.getBenutzerDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean existsByName(String vorname) {
        List<Benutzer> benutzerList = null;
        try {
            benutzerList = dbService.getBenutzerDao().queryForAll();
            for (Benutzer b : benutzerList) {
                if (vorname.toLowerCase() == b.getVorname().toLowerCase()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Benutzer getByEmail(String email) {
        List<Benutzer> benutzerList = allBenutzer();
        for (Benutzer b : benutzerList) {
            if (email.toLowerCase().equals(b.getEmailAdresse().toLowerCase())) {
                return b;
            }
        }
        return null;
    }


    public List<Benutzer> allBenutzer() {
        try {
            return dbService.getBenutzerDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<String> allBenutzerName() {
        List<Benutzer> liste = allBenutzer();
        List<String> names = new LinkedList<>();
        String mitglied = "";
        for (Benutzer b : liste) {
            mitglied = b.getVorname() + " " + b.getNachname();
            names.add(mitglied.trim());
        }
        return names;
    }


    public Benutzer findBenutzer(String name) {
        List<Benutzer> allBenutzerList = allBenutzer();
        for (Benutzer b : allBenutzerList) {
            String fullName = b.getVorname() + " " + b.getNachname();
            if (fullName.trim().equals(name)) {
                return b;
            }
        }
        return null;
    }

}
