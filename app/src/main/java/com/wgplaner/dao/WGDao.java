package com.wgplaner.dao;

import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;
import com.wgplaner.model.WG;

import java.sql.SQLException;
import java.util.List;

public class WGDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    public WG erstellen(WG wg) {
        try {
            return dbService.getWGDao().createIfNotExists(wg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WG getByName(String name) {
        List<WG> wgList = null;
        try {
            wgList = dbService.getWGDao().queryForAll();
            for (WG wg : wgList) {
                if (name.toLowerCase().equals(wg.getName().toLowerCase())) {
                    return wg;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
