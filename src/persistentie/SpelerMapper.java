/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Speler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ruben
 */
public class SpelerMapper
{

    public List<Speler> geefSpelers() {
        List<Speler> spelers = new ArrayList<>(); //lijst die speler objecten zal bevatten

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM spelers");

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    String gebruikersnaam = rs.getString("gebruikersnaam");
                    String wachtwoord = rs.getString("wachtwoord");
                    boolean adminrechten = rs.getBoolean("adminrechten");
                    String naam = rs.getString("naam");
                    String voornaam = rs.getString("voornaam");

                    spelers.add(new Speler(gebruikersnaam, wachtwoord, adminrechten, naam, voornaam));
                }
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return spelers;
    }

    public Speler geefSpeler(String gebruiker) {
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM spelers WHERE gebruikersnaam LIKE BINARY ?");
            query.setString(1, gebruiker); //plaatst gebruikersnaam op de plaats van ? in de query

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    String gebruikersnaam = rs.getString("gebruikersnaam");
                    String wachtwoord = rs.getString("wachtwoord");
                    boolean adminrechten = rs.getBoolean("adminrechten");
                    String naam = rs.getString("naam");
                    String voornaam = rs.getString("voornaam");

                    speler = new Speler(gebruikersnaam, wachtwoord, adminrechten, naam, voornaam);
                }
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return speler;
    }
    
    public void voegToe(Speler speler) {

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("INSERT INTO spelers (gebruikersnaam, wachtwoord, adminrechten, naam, voornaam)"
                    + "VALUES (?, ?, ?, ?, ?)");
            query.setString(1, speler.getGebruikersnaam());
            query.setString(2, speler.getWachtwoord());
            query.setBoolean(3, speler.isAdmin());
            query.setString(4, speler.getNaam());
            query.setString(5, speler.getVoornaam());
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
