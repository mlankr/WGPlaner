package com.wgplaner.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class is a representation of a WG. It has an ID, a Name and stores all Mitglieder and Aufgaben associated with this WG.
 */

@DatabaseTable(tableName = "wg")
public class WG {

    @DatabaseField(canBeNull = false, generatedId = true, columnName = "id")
    private int wgID;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "name")
    private String name;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Benutzer> wgMitglieder;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Aufgabe> aufgaben;

    public WG() {

    }

    // Constructor for Existing WG
    public WG(String name, ForeignCollection<Benutzer> mitglieder, ForeignCollection<Aufgabe> aufgaben) {
        this.name = name;
        this.wgMitglieder = mitglieder;
        this.aufgaben = aufgaben;
    }

    //Constructor for new WG
    public WG(String name) {
        this.name = name;
    }

    // Getter
    public int getID() {
        return this.wgID;
    }

    public String getName() {
        return this.name;
    }

    public ForeignCollection<Benutzer> getWgMitglieder() {
        return wgMitglieder;
    }

    public ForeignCollection<Aufgabe> getAufgaben() {
        return aufgaben;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setWgMitglieder(ForeignCollection<Benutzer> wgMitglieder) {
        this.wgMitglieder = wgMitglieder;
    }

    public void setAufgaben(ForeignCollection<Aufgabe> aufgaben) {
        this.aufgaben = aufgaben;
    }

    @Override
    public String toString() {
        return "WG{" +
                "wgID=" + wgID +
                ", name='" + name + '\'' +
                '}';
    }
}
