package com.wgplaner.db;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DBHelper {

    private static DBService dbService;
    private static Context context;

    public static void initDBService(Context ctx) {
        DBHelper.closeDBService();
        context = ctx;
    }

    public static void closeDBService() {
        if(dbService != null) {
            OpenHelperManager.releaseHelper();
            dbService = null;
        }
    }

    public static DBService getDBService() {
        if (dbService == null) {
            //if there is no instance, create one
            dbService = OpenHelperManager.getHelper(context, DBService.class);
        }
        return dbService;
    }
}
