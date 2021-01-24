package com.wgplaner.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


/**
 * This class is a representation of a single Termin. It has an ID, a Date, may have an repetition
 */

@DatabaseTable(tableName = "termin")
public class Termin {

    @DatabaseField(canBeNull = false, generatedId = true, columnName = "id")
    private int ID;

    @DatabaseField(canBeNull = true, useGetSet = true, columnName = "wiederkehrend")
    private boolean wiederkehrend;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "date")
    private Date date;

    public Termin() {

    }

    // Constructor for Termin with repetition
    public Termin(boolean wiederkehrend, Date date) {
        this.wiederkehrend = wiederkehrend;
        this.date = date;
    }

    // Constructor for Termin without repetition
    public Termin(Date date) {
        this.date = date;
    }

    // Getter
    public int getID() {
        return ID;
    }

    public boolean getWiederkehrend() {
        return wiederkehrend;
    }

    public Date getDate() {
        return date;
    }

    // Setter
    public void setWiederkehrend(boolean wiederkehrend) {
        this.wiederkehrend = wiederkehrend;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Termin{" +
                "ID=" + ID +
                ", wiederkehrend=" + wiederkehrend +
                ", date=" + date +
                '}';
    }
}
