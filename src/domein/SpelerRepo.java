/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import persistentie.SpelerMapper;
import resources.Taal;

/**
 *
 * @author Ruben
 */
public class SpelerRepo
{
    private final SpelerMapper spelerMapper;
//    private List<Speler> spelers; // deze lijst wordt eigenlijk niet gebruikt 

    
    /**
     * Constructor van SpelerRepo
     * Maakt een nieuw SpelerMapperobject aan en wijst dit toe aan het attribuut spelerMapper
     */
    
    public SpelerRepo() {
        spelerMapper = new SpelerMapper();
    }
    
    
    /**
     * Geeft het Spelerobject terug uit de database dat voldoet aan de meegegeven parameters
     * Werpt een IllegalArgumentException indien één van de parameters null of leeg is.
     * Indien de Speler niet gevonden werd, of indien het wachtwoord niet overeenkwam met het wachtwoord in de database,
     * wordt null geretourneerd
     * @param gebruikersnaam gebruikersnaam van de opgevraagde Speler
     * @param wachtwoord wachtwoord van de opgevraagde Speler
     * @return gevraagde Speler indien deze voldoet aan meegegeven parameters
     */

    public Speler geefSpeler(String gebruikersnaam, String wachtwoord) {
        if(gebruikersnaam == null || gebruikersnaam.isEmpty()) {
            throw new IllegalArgumentException(Taal.getText("error_naam"));
        }
        else if(wachtwoord == null || wachtwoord.isEmpty()) {
            throw new IllegalArgumentException(Taal.getText("passwordField.wachtwoord.empty"));
        }
        
        Speler speler = spelerMapper.geefSpeler(gebruikersnaam); //var speler krijgt een Speler object terug van SpelerMapper

        if (speler != null && speler.getWachtwoord().equals(wachtwoord)) { //controleer of wachtwoord klopt
            return speler;
        }

        return null;
    }
    
    
    /**
     * Voegt het meegegeven Spelerobject toe in de database
     * Werpt een IlegalArgumentException indien een er zich reeds een Speler in de database
     * bevindt met dezelfde gebruikersnaam
     * @param speler 
     */
    
    public void voegToe(Speler speler) {
       if (bestaatSpeler(speler.getGebruikersnaam()))
            throw new IllegalArgumentException(Taal.getText("error_bestaand_username"));
       
       spelerMapper.voegToe(speler);
    }
    
    
    /**
     * Gaat na of er al zich een Speler in de database bevindt met deze gebruikersnaam
     * @param gebruikersnaam gebruikersnaam van de Speler
     * @return boolean die aangeeft of deze Speler zich in de database bevindt
     */
    
    private boolean bestaatSpeler(String gebruikersnaam){
        return spelerMapper.geefSpeler(gebruikersnaam)!= null;
    }
}
