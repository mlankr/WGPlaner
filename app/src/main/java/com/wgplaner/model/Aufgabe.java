package com.wgplaner.model;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

/**
 * This class is a representation of a single Aufageb. It has an ID, a Name, may have an associated Termin
 */

@DatabaseTable(tableName = "aufgabe")
public class Aufgabe {

    @DatabaseField(canBeNull = false, generatedId = true, columnName = "id")
    private int aufgabeID;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "bezeichnung")
    private String bezeichnung;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "erledigteAufgabe")
    private boolean erledigteAufgabe;

    @DatabaseField(canBeNull = true, foreign = true, useGetSet = true, foreignAutoRefresh = true, columnName = "termin")
    private Termin aufgabeTermin;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "karmapunkte")
    private int karmapunkte;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "zugeordneteAufgabe")
    private boolean zugeordneteAufgaben;

    @DatabaseField(canBeNull = true, foreign = true, useGetSet = true, foreignAutoRefresh = true, columnName = "benutzer")
    private Benutzer benutzer;

    @DatabaseField(canBeNull = false, foreign = true, useGetSet = true, foreignAutoRefresh = true, columnName = "wg")
    private WG wg;

    public Aufgabe() {

    }

    // Constructor for Aufgaben without Termin without Wiederkehrend and not offeneAufgaben
    public Aufgabe(String bezeichnung, boolean erledigt, int punkte, boolean zugeordnet, Benutzer benutzer) {
        this.bezeichnung = bezeichnung;
        this.erledigteAufgabe = erledigt;
        this.karmapunkte = punkte;
        this.zugeordneteAufgaben = zugeordnet;
        this.benutzer = benutzer;
    }


    // Getter
    public Termin getAufgabeTermin() {
        return this.aufgabeTermin;
    }

    public int getKarmapunkte() {
        return this.karmapunkte;
    }

    public boolean getZugeordneteAufgaben() {
        return this.zugeordneteAufgaben;
    }

    public Benutzer getBenutzer() {
        return this.benutzer;
    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    public int getID() {
        return aufgabeID;
    }

    public boolean getErledigteAufgabe() {
        return erledigteAufgabe;
    }

    public WG getWg() {
        return wg;
    }

    //Setter
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public void setAufgabeTermin(Termin termin) {
        this.aufgabeTermin = termin;
    }

    public void setKarmapunkte(int punkte) {
        this.karmapunkte = punkte;
    }

    public void setZugeordneteAufgaben(boolean zugeordnet) {
        this.zugeordneteAufgaben = zugeordnet;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    public void setErledigteAufgabe(boolean erledigteAufgabe) {
        this.erledigteAufgabe = erledigteAufgabe;
    }

    public void setWg(WG wg) {
        this.wg = wg;
    }

    @Override
    public String toString() {
        return "Aufgabe{" +
                "aufgabeID=" + aufgabeID +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", erledigteAufgabe=" + erledigteAufgabe +
                ", aufgabeTermin=" + aufgabeTermin +
                ", karmapunkte=" + karmapunkte +
                ", zugeordneteAufgaben=" + zugeordneteAufgaben +
                ", benutzer=" + benutzer.getEmailAdresse() +
                ", wg=" + wg.getName() +
                '}';
    }
}
