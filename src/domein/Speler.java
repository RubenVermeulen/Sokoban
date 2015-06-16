/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import resources.Taal;

/**
 *
 * @author Ruben
 */
public class Speler
{
    
    private String gebruikersnaam;
    private String wachtwoord;
    private boolean adminrechten;
    private String naam;
    private String voornaam;

    
    /**
     * Maakt een nieuw Spelerobject aan
     * Wijst de meegegeven paramaters toe aan de bijhorende attributen van Speler.
     * @param gebruikersnaam de gebruikersnaam van de nieuwe Speler
     * @param wachtwoord het wachtwoord van de nieuwe Speler
     * @param adminrechten geeft aan of de Speler over adminrechten beschikt of niet
     * @param naam de familienaam van de nieuwe Speler
     * @param voornaam de voornaam van de nieuwe Speler
     */
    
    public Speler(String gebruikersnaam, String wachtwoord, boolean adminrechten, String naam, String voornaam){
        setNaam(naam);
        setVoornaam(voornaam);
        setGebruikersnaam(gebruikersnaam);
        setWachtwoord(wachtwoord);
        setAdminrechten(adminrechten);
    }
    
    
    /**
     * @return retourneert gebruikersnaam
     */

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }
    
    
    /**
     * @return  retourneert wachtwoord
     */

    public String getWachtwoord() {
        return wachtwoord;
    }
    

    /**
     * Retourneert true als de Speler over adminrechten beschikt, false indien niet
     * @return boolean adminrechten
     */
    
    public boolean isAdmin() {
        return adminrechten;
    }
    
    
    /**
     * @return retourneert naam
     */
    
    public String getNaam()
    {
        return naam;
    }
    

    /**
     * @return retourneert voornaam
     */
    
    public String getVoornaam()
    {
        return voornaam;
    }
    
    
    /**
     * Wijst de meegegeven parameter toe aan het attribuut gebruikersnaam
     * Gebruikersnaam mag niet leeg zijn en moet minimum 8 karakters bevatten
     * @param gebruikersnaam gebruikersnaam van de Speler
     */

    public void setGebruikersnaam(String gebruikersnaam) {
        if (gebruikersnaam == null || gebruikersnaam.isEmpty()) {
            throw new IllegalArgumentException(Taal.getText("error_naam"));
        }
        else if (gebruikersnaam.length()<8) {
            throw new IllegalArgumentException(Taal.getText("error_naam_lengte"));
        }
        
        this.gebruikersnaam = gebruikersnaam;
    }
    
    
    /**
     * Wijst de meegegeven parameter toe aan het attribuut wachtwoord
     * Wachtwoord moet minimum 8 karakters lang zijn, moet een kleine letter, grote letter en nummer bevatten
     * @param wachtwoord van de Speler
     */

    public void setWachtwoord(String wachtwoord) {
        if (wachtwoord.length() < 8) {
            throw new IllegalArgumentException(Taal.getText("error_pass_lengte"));
        }
        else if (!wachtwoord.matches(".*[0-9]+.*") || !wachtwoord.matches(".*[A-Z]+.*") || !wachtwoord.matches(".*[a-z]+.*")) {
            throw new IllegalArgumentException(Taal.getText("error_pass"));
        }
        
        this.wachtwoord = wachtwoord;
    }
    
    
    /**
     * Wijst de meegegeven parameter toe aan het attribuut adminrechten
     * De waarde true geeft aan dat de Speler over adminrechten beschikt, false niet.
     * @param adminrechten adminrechten van de Speler
     */

    public void setAdminrechten(boolean adminrechten) {
        this.adminrechten = adminrechten;
    }
    
    
    /**
     * Wijst de meegegeven parameter toe aan het attribuut naam
     * Mag null zijn
     * @param naam familienaam van de Speler
     */
    
    public void setNaam(String naam){
        this.naam = naam;
    }
    
    
    /**
     * Wijst de meegegeven parameter toe aan het attribuut voornaam
     * Mag null zijn
     * @param voornaam voornaam van de Speler
     */
    
    public void setVoornaam(String voornaam){
        this.voornaam = voornaam;
    }
    
}
