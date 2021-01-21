package com.wgplaner.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * This class is a representation of a single done job with an name, it's Karma score and a timestamp.
 */

@DatabaseTable(tableName = "erledigteAufgabe")
public class ErledigteAufgabe {

    @DatabaseField(canBeNull = false, generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "aufgabeName")
    private String name;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "punkte")
    private int punkte;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "date")
    private Date date;

    @DatabaseField(canBeNull = false, foreign = true, useGetSet = true, foreignAutoRefresh = true, columnName = "benutzer")
    private Benutzer benutzer;

    public ErledigteAufgabe() {

    }

    //Constructor
    public ErledigteAufgabe(String name, int punkte, Benutzer benutzer) {
        this.name = name;
        this.punkte = punkte;
        this.date = new Date();
        this.benutzer = benutzer;
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public int getPunkte() {
        return punkte;
    }

    public Date getDate() {
        return date;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    @Override
    public String toString() {
        return "ErledigteAufgabe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", punkte=" + punkte +
                ", date=" + date +
                ", benutzer=" + benutzer.getEmailAdresse() +
                '}';
    }
}
