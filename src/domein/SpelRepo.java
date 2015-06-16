/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domein;
import java.util.List;
import persistentie.SpelMapper;
import resources.Taal;
/**
 *
 * @author Ebu Bekir
 */
public class SpelRepo
{
    private final SpelMapper spelMapper;
    List<Spel> spelletjes;
    
    /**
     * Maakt een SpelMapper object aan en wijst deze toe aan het spelMapper atrribuut
     */
    
    public SpelRepo() {
        spelMapper = new SpelMapper();
    }
    
    /**
     * 
     * @return retourneert een lijst van spel objecten
     */
    
    public List<Spel> geefSpellen()
    {
        spelletjes = spelMapper.geefSpellen();
        
        return spelletjes;
    }
    
    /**
     * Retourneert het spel object dat de naam heeft die overeenkomt met de meegegeven parameter.
     * 
     * @param naam naam van spel dat je het object van wilt
     * @return retourneert een spel object
     */
    
    public Spel geefSpel(String naam)
    {
        Spel spel = spelMapper.geefSpel(naam);

        return spel;
    }
    
    /**
     * Retourneert het spel object dat de naam heeft die overeenkomt met de meegegeven parameter.
     * 
     * @param naam naam van spel dat je het object van wilt
     * @return retourneert een spel object
     */
    
    public Spel kiesSpel(String naam)
    {
        Spel spel = spelMapper.geefSpel(naam);

        return spel;
    }
    
    /**
     * Retourneert een spel object dat is opgebouwd uit de gegevens die zijn meegegeven voor de creatie van een nieuw spel
     * 
     * @param naam naam nieuw spel
     * @return nieuw spel object
     */
    
    //UC5 configureerNieuwSpel
    public Spel configureerNieuwSpel(String naam){
        boolean nieuw = true;
        Spel nieuwSpel = new Spel(naam, nieuw);
        
        if (bestaatSpel(nieuwSpel.getNaam()))
            throw new IllegalArgumentException(Taal.getText("error_bestaand_spelnaam"));
        
        spelMapper.voegSpelToe(nieuwSpel);
        return nieuwSpel;
        
    }
        
    /**
     * Retourneert of het spel al dan niet bestaat
     * 
     * @param spelNaam naam die wordt gecontroleerd of deze al bestaat
     * @return bestaat spel
     */

    private boolean bestaatSpel(String spelNaam){
        return spelMapper.geefSpel(spelNaam)!=null;
    }
 
}
