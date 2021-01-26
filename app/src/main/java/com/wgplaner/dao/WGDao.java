package com.wgplaner.dao;

import com.wgplaner.db.DBHelper;
import com.wgplaner.db.DBService;
import com.wgplaner.model.WG;

import java.sql.SQLException;
import java.util.List;

public class WGDao {

    // Database helper class object to get the dao
    private final DBService dbService = DBHelper.getDBService();

    // Create new WG using WGDao
    public int create(WG wg) {
        try {
            if (getByName(wg.getName()) == null) {
                return dbService.getWGDao().create(wg);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Update WG using WGDao
    public int update(String name) {
        try {
            WG wg = get();
            wg.setName(name);
            return dbService.getWGDao().update(wg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get WG by name using WGDao
    public WG getByName(String name) {
        try {
            List<WG> wgList = dbService.getWGDao().queryForAll();
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

    // Get first created WG using WGDao
    public WG get() {
        try {
            List<WG> wgList = dbService.getWGDao().queryForAll();
            if (!wgList.isEmpty()) {
                return wgList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
