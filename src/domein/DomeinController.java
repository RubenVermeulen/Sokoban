/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * test
 */
package domein;

import java.util.List;
import resources.Taal;

/**
 *
 * @author Ruben
 */
public class DomeinController {

    private final SpelerRepo spelerRepo;
    private final SpelRepo spelRepo;
    private Speler speler;
    private Spel spel;
    private Taal taal;
    
    /**
     * Maakt een spelerRepo object aan en wijst deze toe aan het attribuut spelerRepo.
     * Maakt een spelRepo object aan en wijst deze toe aan het attribuut spelRepo.
     */
    
    public DomeinController() {
        spelerRepo = new SpelerRepo();
        spelRepo = new SpelRepo();
    }
    
    /**
     * Geeft het spel object terug.
     * 
     * @return  spel object
     */

    public Spel getSpel() {
        return spel;
    }
    
    /**
     * Controleert of de speler aangemeld is of niet.
     * 
     * @return  retourneert een boolean
     */

    public boolean isAangemeld() {
        if (speler != null) {
            return true;
        }

        return false;
    }
    
    /**
     * Wijst een meegegeven speler object toe aan het attribuut speler.
     * 
     * @param speler    bevat een speler object
     */
    
    public void setSpeler(Speler speler) {
        this.speler = speler;
    }
    
    /**
     * Retourneert het attribuut speler dat een speler object bevat.
     * 
     * @return retourneert een speler object
     */

    public Speler getSpeler() {
        return speler;
    }
    
    /**
     * Controleert of de meegegeven waarden overeen komen met een speler in de database. Wanneer het speler object niet null is wordt de speler aangemeld.
     * De controle controleert ook of de parameters niet null zijn en dergelijke.
     * 
     * @param gebruikersnaam    gebruikersnaam van de speler
     * @param wachtwoord        wachtwoord van de speler
     */

    public void meldAan(String gebruikersnaam, String wachtwoord) {
        Speler speler = spelerRepo.geefSpeler(gebruikersnaam, wachtwoord);

        if (speler != null) {
            setSpeler(speler);
        }
    }
    
    /**
     * Registreert een speler. Tijdens de registratie worden de parameters gecontroleerd op mogelijke fouten.
     * Bij succes zal de speler weggeschreven worden naar de database en aangemeld worden.
     * 
     * @param gebruikersnaam    gebuikersnaam van de nieuwe speler
     * @param wachtwoord        wachtwoord van de nieuwe speler
     * @param naam              naam van de nieuwe speler
     * @param voornaam          voornaam van de nieuwe speler
     */
    
    public void registreer(String gebruikersnaam, String wachtwoord, String naam, String voornaam) {
        Speler nieuweSpeler = new Speler(gebruikersnaam, wachtwoord, false, naam, voornaam);
        
        spelerRepo.voegToe(nieuweSpeler);
        setSpeler(nieuweSpeler);
    }
    
    /**
     * Retourneert een lijst van alle spellen voorgesteld via hun naam.
     * 
     * @return geeft een lijst terug van namen van alle spellen
     */

    public String[] geefSpelnamen() {
        List<Spel> spelletjes = spelRepo.geefSpellen();

        String[] naam = new String[spelletjes.size()];

        for (int i = 0; i < spelletjes.size(); i++) {
            naam[i] = spelletjes.get(i).getNaam();
        }

        return naam;
    }
    
    /**
     * Het spel attribuut wordt geïnitialiseerd met een spel object. Dit spel object wordt aangemaakt via de meegegeven naam.
     * 
     * @param naam naam van gekozen spel
     */

    public void kiesSpel(String naam) {
        this.spel = spelRepo.kiesSpel(naam);
    }
    
    /**
     * Retourneert een array die het volledige spelbord voorstelt.
     * 
     * @return array die spelbord voorstelt
     */

    public String[][] geefSpelbord() {
        Spelbord spelbord;

        if (spel.getSpelbord() == null) {
            spelbord = spel.geefSpelbord();
        } else {
            spelbord = spel.getSpelbord();
        }

        String[][] bord = new String[10][10];
        int grootte = bord.length;

        // Verwijder de X op alle loopbare velden, plaats K bij kist en M bij mannetje
        List<Kist> kisten = spelbord.getKisten();
        Veld[][] velden = spelbord.getVelden();
        Mannetje mannetje = spelbord.getMannetje();
        Veld veld;
        
        for (int i = 0; i < velden.length; i++) {
            for(int j = 0; j < velden[i].length; j++) {
                veld = velden[i][j];
                if(veld != null) {
                    bord[veld.getX()][veld.getY()] = " "; // Loopvlak
                    
                    if (veld.getDoel()) {
                        bord[veld.getX()][veld.getY()] = "D"; // Doel
                    }
                }
                else 
                    bord[i][j] = "*"; // Muur
            }
            
        }

        for (Kist k : kisten) {
            Veld veldKist = k.getVeld();
            String waarde;
            
            if(veldKist.isDoel()) {
                waarde = "S"; // Kist staat op doel
            }
            else {
                waarde = "K"; // Kist
            }
            
            bord[veldKist.getX()][veldKist.getY()] = waarde;
        }

        bord[mannetje.getVeld().getX()][mannetje.getVeld().getY() ] = "M"; // Mannetje

        return bord;
    }
    
//    public String[][] geefNieuwSpelbord() {
//
//        String[][] bord = new String[10][10];
//        int grootte = bord.length;
//        int k = 0;
//
//        for (int i = 0; i < bord.length; i++){
//            for (int j = 0; j < bord[i].length; j++){
//                bord[i][j] = String.format("%02d", k);
//                k++;
//            }
//        }
//        return bord;
//    }

    /**
     * Verplaatst mannetje op het spelbord. De richting wordt bepaald door de parameter die wordt meegegeven.
     * 
     * @param richting de richting waar het mannetje naartoe zal bewegen
     */
    
    public void verplaatsMannetje(int richting) {
        // indien spel opnieuw starten -> haal spelbord opnieuw uit database
        if (richting == 9) {
            spel.geefSpelbord();
            this.geefSpelbord();
        } else {
            spel.verplaatsMannetje(richting);
        }
    }
    
    /**
     * Geeft het aantal voltooide en totaal aantal spelborden terug voor het huidige spel.
     * 
     * @return Een array met twee waarden (1. aantal voltooid, 2. totaal aantal)
     */

    public int[] geefAantalVoltooideEnTotaalAantalSpelborden() {
        return spel.geefAantalVoltooideEnTotaalAantalSpelborden();
    }
    
    /**
     * Retourneert of het spel al dan niet ten einde is door middel van een boolean
     * 
     * @return Een boolean die aangeeft of het spel ten einde is
     */

    public boolean isEindeSpel() {
        return spel.isEindeSpel();
    }
    
    /**
     * Geeft het aantal verplaatsingen voor het huidige spelbord
     * 
     * @return een getal die het aantal verplaatsingen voorstelt
     */

    public int geefAantalVerplaatsingen() {
        return spel.geefAantalVerplaatsingen();
    }

    /**
     * Initialiseert de correcte resource bundle zodat de juiste taal wordt weergegeven. De resource bundle wordt gekozen door middel van de meegegeven parameter.
     * Het object wordt opgeslagen in het attribuut taal.
     * 
     * @param l waarde van 2 karakters die de taal voorstellen (nl, fr, en)
     */
    
    public void setTaal(String l) {
        taal = new Taal(l);
    }
    
    /**
     * Retourneert het taal object door aanroep van het taal attribuut
     * 
     * @return Taal object
     */
    
    public Taal getTaal() {
        return taal;
    }
    
    /**
     * Bepaalt of het spelbord al dan niet voltooid is
     * 
     * @return boolean die aangeeft of het spelbord ten einde is
     */

    public boolean isSpelbordVoltooid() {
        return spel.isSpelbordVoltooid();
    }
    
    /**
     * Configureert een nieuw spel. Dit wordt weggeschreven naar de database. Er wordt ook een controle uitgevoerd om duplicaten te vermijden. 
     * De parameter is de waarde die word weggeschreven.
     * 
     * @param naam spelnaam
     */
    
    //UC5 configureerNieuwSpel
    public void configureerNieuwSpel(String naam) { 
        this.spel = spelRepo.configureerNieuwSpel(naam);
//        geefSpelbord(); => geefNieuwSpelbord();
    }
    
    /**
     * Configureert een nieuw spelbord. Dit wordt weggeschreven naar de database. 
     * De parameter is de naam van het spel waartoe het spelbord zal behoren.
     * 
     * @param naam 
     */
    
    public void configureerNieuwSpelbord(String naam){
        spel.configureerNieuwSpelbord(naam);
    }
    
    /**
     * Plaatst een nieuw item op het spelbord dat wordt geconfigureerd.
     * 
     * @param item int die bepaalt of het om een veld, mannetje of kist gaat
     * @param positie array die de coördinaten bevat waar het item moet geplaatst worden
     */
    
    public void plaatsItem(int item, int[] positie){
        spel.plaatsItem(item, positie);
    }
    
    /**
     * Geeft de naam terug van het huidige spel.
     */
    
    //UC5 geefNaamSpel
    public String geefNaamSpel() {
        return spel.getNaam();
    }
    
    /**
     * Haalt alle id's op van de spelborden voor het huidig spel
     * 
     * @return lijst van spelbord id's
     */
    
    //UC6 Id van spelbord halen om daarmee te werken
    public int[] geefSpelbordIds(){
        return spel.geefSpelbordIds();
    }
    
    /**
     * NOG NIET
     * 
     * @param id
     * @return 
     */
    
    //UC6 wijzigSpelbord
    public String[][] wijzigSpelbord(int id) {
        spel.wijzigSpelbord(id);
        String[][] spelbord = geefSpelbord();
        return spelbord;
    }
    
    /**
     * NOG NIET
     * 
     * @param item
     * @param positie 
     */
    
    public void kiesItem(int item,int[] positie){
        spel.kiesItem(item, positie);
    }
    
    /**
     * Maakt een nieuw spelbord object aan en wijst dit toe aan het attribuut spelbord
     */
    
    //spelbord maken
    public void maakNieuwSpelbord() {
        Spelbord spelbord;
        spelbord = new Spelbord();
    }
    
    /**
     * @return geeft weer of de speler een admin is of niet
     */
    
    public boolean isAdmin() {
        return speler.isAdmin();
    }
}
