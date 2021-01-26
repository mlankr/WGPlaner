package com.wgplaner.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class is a representation of a single Benutzer. He/She has an ID, Firstname, Lastname, E-mail, associated WG and Karmapunktestand
 */
@DatabaseTable(tableName = "benutzer")
public class Benutzer {

    @DatabaseField(canBeNull = false, generatedId = true, columnName = "id")
    private int benutzerID;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "vorname")
    private String vorname;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "nachname")
    private String nachname;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "emailAdresse")
    private String emailAdresse;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "passwort", defaultValue = "*****")
    private String passwort;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "angemeldet")
    private boolean angemeldet;

    @DatabaseField(canBeNull = false, foreign = true, useGetSet = true, foreignAutoRefresh = true, columnName = "wg")
    private WG wg;

    @DatabaseField(canBeNull = false, useGetSet = true, columnName = "karmapunkte")
    private int karmapunkte;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<ErledigteAufgabe> erledigteAufgabeList;

    // Default Empty Constructor for Benutzer
    public Benutzer() {

    }

    // Constructor for Benutzer with all details
    public Benutzer(String firstName, String lastName, String email, String password, boolean loggedIn, WG wg, int points) {
        this.vorname = firstName;
        this.nachname = lastName;
        this.emailAdresse = email;
        this.passwort = password;
        this.angemeldet = loggedIn;
        this.wg = wg;
        this.karmapunkte = points;
    }

    // Constructor for Benutzer with Firstname, Lastname, Email and WG
    public Benutzer(String firstName, String lastName, String email, WG wg) {
        this.vorname = firstName;
        this.nachname = lastName;
        this.emailAdresse = email;
        this.angemeldet = false;
        this.wg = wg;
        this.karmapunkte = 0;
    }

    // Login method for Benutzer
    public boolean anmelden(String passwort) {
        if (passwort.equals(this.passwort)) {
            this.angemeldet = true;
            return true;
        } else {
            return false;
        }
    }

    // Logout method for Benutzer
    public void abmelden() {
        this.angemeldet = false;
    }

    // Delete Benutzer
    public void loeschen() {
        // To be implemented
    }

    // Reset password for Benutzer
    public void passwortWiederherstellen() {
        // To be implemented
    }

    //Getter
    public int getID() {
        return benutzerID;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getEmailAdresse() {
        return emailAdresse;
    }

    public String getPasswort() {
        return passwort;
    }

    public WG getWg() {
        return wg;
    }

    public int getKarmapunkte() {
        return this.karmapunkte;
    }

    public boolean getAngemeldet() {
        return angemeldet;
    }

    public ForeignCollection<ErledigteAufgabe> getErledigteAufgabeList() {
        return erledigteAufgabeList;
    }

    //Setter
    public void setVorname(String firstName) {
        this.vorname = firstName;
    }


    public void setNachname(String lastName) {
        this.nachname = lastName;
    }


    public void setEmailAdresse(String email) {
        this.emailAdresse = email;
    }


    public void setPasswort(String pass) {
        this.passwort = pass;
    }


    public void setWg(WG wg) {
        this.wg = wg;
    }


    public void setKarmapunkte(int points) {
        this.karmapunkte = points;
    }


    public void setAngemeldet(boolean angemeldet) {
        this.angemeldet = angemeldet;
    }


    public void setErledigteAufgabeList(ForeignCollection<ErledigteAufgabe> erledigteAufgabeList) {
        this.erledigteAufgabeList = erledigteAufgabeList;
    }

    // Book Karma points method
    public void karmapunkteVerbuchen(Aufgabe aufgabe) {
    }

    // Check karma points
    public boolean checkKarmeScore() {
        return true;
    }

    // Equals method to compare two Benutzer
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Benutzer)) {
            return false;
        }
        Benutzer that = (Benutzer) o;
        return this.getID() == that.getID() &&
                this.getVorname().equals(that.getVorname()) &&
                this.getNachname().equals(that.getNachname()) &&
                this.getEmailAdresse().equals(that.getEmailAdresse());
    }

    // toString Method to print Firstname and Lastname
    @Override
    public String toString() {
        return getVorname() + " " + getNachname();
    }

    // toString Method to print in Details
    public String toStringDetails() {
        return "Benutzer{" +
                "benutzerID=" + benutzerID +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", emailAdresse='" + emailAdresse + '\'' +
                ", angemeldet=" + angemeldet +
                ", wg=" + wg.getName() +
                ", karmapunkte=" + karmapunkte +
                '}';
    }

}
