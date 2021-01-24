package com.wgplaner.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    public Benutzer() {

    }

    public Benutzer(String firstName, String lastName, String email, String password, boolean loggedIn, WG wg, int points) {
        this.vorname = firstName;
        this.nachname = lastName;
        this.emailAdresse = email;
        this.passwort = password;
        this.angemeldet = loggedIn;
        this.wg = wg;
        this.karmapunkte = points;
    }

    public Benutzer(String firstName, String lastName, String email, WG wg) {
        this.vorname = firstName;
        this.nachname = lastName;
        this.emailAdresse = email;
        this.angemeldet = false;
        this.wg = wg;
        this.karmapunkte = 0;
    }

    public boolean anmelden(String passwort) {
        if (passwort.equals(this.passwort)) {
            this.angemeldet = true;
            return true;
        } else {
            return false;
        }
    }

    public void abmelden() {
        this.angemeldet = false;
    }

    public void loeschen() {
        // To be implemented
    }

    public void passwortWiederherstellen() {
        // To be implemented
    }

    public int getID() {
        return benutzerID;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String firstName) {
        this.vorname = firstName;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String lastName) {
        this.nachname = lastName;
    }

    public String getEmailAdresse() {
        return emailAdresse;
    }

    public void setEmailAdresse(String email) {
        this.emailAdresse = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String pass) {
        this.passwort = pass;
    }

    public WG getWg() {
        return wg;
    }

    public void setWg(WG wg) {
        this.wg = wg;
    }

    public int getKarmapunkte() {
        return this.karmapunkte;
    }

    public void setKarmapunkte(int points) {
        this.karmapunkte = points;
    }

    public boolean getAngemeldet() {
        return angemeldet;
    }

    public void setAngemeldet(boolean angemeldet) {
        this.angemeldet = angemeldet;
    }

    public ForeignCollection<ErledigteAufgabe> getErledigteAufgabeList() {
        return erledigteAufgabeList;
    }

    public void setErledigteAufgabeList(ForeignCollection<ErledigteAufgabe> erledigteAufgabeList) {
        this.erledigteAufgabeList = erledigteAufgabeList;
    }

    public void karmapunkteVerbuchen(Aufgabe aufgabe) {
    }

    public boolean checkKarmeScore() {
        return true;
    }

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

    @Override
    public String toString() {
        return getVorname() + " " + getNachname();
    }

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
