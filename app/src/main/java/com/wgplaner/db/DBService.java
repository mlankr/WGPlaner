package com.wgplaner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import com.wgplaner.model.Aufgabe;
import com.wgplaner.model.Benutzer;
import com.wgplaner.model.ErledigteAufgabe;
import com.wgplaner.model.Termin;
import com.wgplaner.model.WG;

import java.sql.SQLException;


public class DBService extends OrmLiteSqliteOpenHelper {

    // name of the database file for an application
    private static final String DATABASE_NAME = "wgPlaner.db";

    // any time changes are made to the database objects, database version has to be increased
    private static final int DATABASE_VERSION = 1;

    // DAO objects to access the specific database table
    private Dao<Aufgabe, Integer> aufgabeDao = null;
    private Dao<Benutzer, Integer> benutzerDao = null;
    private Dao<ErledigteAufgabe, Integer> erledigteAufgabeDao = null;
    private Dao<Termin, Integer> terminDao = null;
    private Dao<WG, Integer> wgDao = null;


    // initialize the database helper class
    public DBService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created.
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Aufgabe.class);
            TableUtils.createTable(connectionSource, Benutzer.class);
            TableUtils.createTable(connectionSource, ErledigteAufgabe.class);
            TableUtils.createTable(connectionSource, Termin.class);
            TableUtils.createTable(connectionSource, WG.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when an application is upgraded and it has a higher version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            // to upgrade the database, first drop all tables
            TableUtils.dropTable(connectionSource, Aufgabe.class, true);
            TableUtils.dropTable(connectionSource, Benutzer.class, true);
            TableUtils.dropTable(connectionSource, ErledigteAufgabe.class, true);
            TableUtils.dropTable(connectionSource, Termin.class, true);
            TableUtils.dropTable(connectionSource, WG.class, true);

            // after we drop the old databases, we create the new one
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for the Aufgabe class (Singleton).
     */
    public Dao<Aufgabe, Integer> getAufgabeDao() throws SQLException {
        if (aufgabeDao == null) {
            aufgabeDao = getDao(Aufgabe.class);
        }
        return aufgabeDao;
    }

    /**
     * Returns the Database Access Object (DAO) for the Benutzer class (Singleton).
     */
    public Dao<Benutzer, Integer> getBenutzerDao() throws SQLException {
        if (benutzerDao == null) {
            benutzerDao = getDao(Benutzer.class);
        }
        return benutzerDao;
    }

    /**
     * Returns the Database Access Object (DAO) for the Benutzer class (Singleton).
     */
    public Dao<ErledigteAufgabe, Integer> getErledigteAufgabeDao() throws SQLException {
        if (erledigteAufgabeDao == null) {
            erledigteAufgabeDao = getDao(ErledigteAufgabe.class);
        }
        return erledigteAufgabeDao;
    }

    /**
     * Returns the Database Access Object (DAO) for the Termin class (Singleton).
     */
    public Dao<Termin, Integer> getTerminDao() throws SQLException {
        if (terminDao == null) {
            terminDao = getDao(Termin.class);
        }
        return terminDao;
    }

    /**
     * Returns the Database Access Object (DAO) for the WGDao class (Singleton).
     */
    public Dao<WG, Integer> getWGDao() throws SQLException {
        if (wgDao == null) {
            wgDao = getDao(WG.class);
        }
        return wgDao;
    }


    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        aufgabeDao = null;
        benutzerDao = null;
        erledigteAufgabeDao = null;
        terminDao = null;
        wgDao = null;
    }
}
