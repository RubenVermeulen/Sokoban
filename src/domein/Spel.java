/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import persistentie.SpelMapper;

/**
 *
 * @author Ebu Bekir
 */
public class Spel {

    private String naam;
    private Spelbord spelbord;
    private final SpelMapper spelmapper;
    private int aantalVoltooideSpelborden;

    
    /**
     * Constructor van Spel
     * Wijst meegegeven parameter toe aan het attribuut naam
     * Maakt een SpelMapperobject aan en wijst deze toe aan het attribuut spelMapper
     * Het attribuut aantalVoltooideSpelborden wordt geïnitialiseerd op 0;
     * @param naam naam van het Spel
     */
    
    public Spel(String naam) {
        this.naam = naam;
        this.spelmapper = new SpelMapper();
        aantalVoltooideSpelborden = 0;
    }
    
    
    /**
     * Constructor van Spel
     * Wijst meegegeven parameter naam toe aan het attribuut naam
     * De parameter nieuw geeft aan dat dit om een nieuw Spel gaat dat zich nog niet in de database bevindt,
     * en dat er een nieuw Spelbord geïnitialiseerd moet worden.
     * Initialiseerd nieuw Spelbord en wijst dit toe aan het attribuut spelbord
     * @param naam naam van het nieuwe Spel
     * @param nieuw geeft aan of het om een nieuw Spel gaat of of het Spel zich reeds in de database bevindt
     */

    //UC5 nieuw spel
    public Spel(String naam, boolean nieuw) {
        this(naam);
        spelbord = new Spelbord();
    }

    
    /**
     * Roept de methode plaatsItem van het attribuut spelbord op
     * Zal een item verplaatsen op het Spelbord
     * @param item int die een mannetje, veld of kist voorstelt
     * @param positie de coördinaten waar het item geplaatst moet worden
     */
    
    //UC6 plaatsItem
    public void plaatsItem(int item, int[] positie) {
        spelbord.plaatsItem(item, positie);
    }
    
//    public int[] kiesItem(int[] positie){
//        spelbord.kiesItems(item);
//    }

    
    /**
     * @return retourneert naam van het Spel
     */
    
    public String getNaam() {
        return naam;
    }

    
    /**
     * Wijst meegegeven parameter toe aan het attribuut naam
     * @param naam naam van het Spel
     */
    
    public void setNaam(String naam) {
        this.naam = naam;
    }
    
    
    /**
     * Wijst meegegeven parameter toe aan het attribuut spelbord
     * Op elk moment zal het Spel slechts één Spelbord kennen : 
     * Spelborden worden geladen op het moment dat de Speler het vorig Spelbord voltooid.
     * @param spelbord 
     */
    
    public void setSpelbord(Spelbord spelbord) {
        this.spelbord = spelbord;
    }

    
    /**
     * Haalt het huidig Spelbord op uit de database
     * Initialiseert dit Spelbord : laadt alle objecten op dit Spelbord in uit de database
     * Deze methode wordt opgeroepen wanneer de Speler net een nieuw Spelbord opstart,
     * of wanneer de Speler zijn Spelbord wenst te resetten.
     * @return huidig spelbord
     */
    
    public Spelbord geefSpelbord() {
        List<Spelbord> spelborden = spelmapper.geefSpelborden(this.naam);
        spelbord = spelborden.get(aantalVoltooideSpelborden);
        spelmapper.initialiseerSpelbord(spelbord);

        return spelbord;
    }

    
    /**
     * @return retourneert huidig Spelbord
     */
    
    public Spelbord getSpelbord() {
        return spelbord;
    }

    
    /**
     * Roept verplaatsMannetje van attribuut spelbord op
     * @param richting richting waarin het mannetje zich moet verplaatsen
     */
    
    public void verplaatsMannetje(int richting) {
        spelbord.verplaatsMannetje(richting);
    }
    
    
    /**
     * Retourneert boolean true indien alle spelborden van dit Spelobject voltooid zijn
     * @return boolean isEindeSpel
     */

    public boolean isEindeSpel() {
        List<Spelbord> spelborden = spelmapper.geefSpelborden(this.naam);

        return spelborden.size() == aantalVoltooideSpelborden;
    }

    
    /**
     * retourneert het aantal voltooide spelborden en het totaal aantal spelborden van dit Spelobject
     * @return twee int's die respectievelijk aantal voltooide en totaal aantal spelborden voorstellen
     */
    
    public int[] geefAantalVoltooideEnTotaalAantalSpelborden() {
        List<Spelbord> spelborden = spelmapper.geefSpelborden(this.naam);
        int[] aantalVoltooideEnTotaalAantalSpelborden = new int[2];

        aantalVoltooideEnTotaalAantalSpelborden[0] = aantalVoltooideSpelborden;
        aantalVoltooideEnTotaalAantalSpelborden[1] = spelborden.size();

        return aantalVoltooideEnTotaalAantalSpelborden;
    }

    
    /**
     * Roept de methode getAantalVerplaatsingen van spelbord op
     * @return aantalVerplaatsingen van spelbord
     */
    
    public int geefAantalVerplaatsingen() {
        return spelbord.getAantalVerplaatsingen();
    }

    
    /**
     * Roept methode isSpelbordVoltooid van spelbord op
     * Retourneert true indien het spelbord voltooid is
     * @return boolean die aangeeft of het huidige spelbord voltooid is
     */
    
    public boolean isSpelbordVoltooid() {
        if (spelbord.isSpelbordVoltooid()) {
            aantalVoltooideSpelborden++;
        }
        return spelbord.isSpelbordVoltooid();
    }
    
    
    /**
     * Retourneert een list met alle id's van de spelborden in de database die bij dit Spel horen
     * @param id id van Spel
     * @return list met alle spelbordid's horende bij dit Spel
     */

    //UC6 Id van spelbord halen om daarmee te werken
    public int[] geefSpelbordIds() {

        List<Spelbord> s = spelmapper.geefSpelborden(this.naam);
        int[] ids = new int[s.size()];

        for (int i = 0; i < s.size(); i++) {
            ids[i] = s.get(i).getId();
        }
        return ids;
    }

    
    /**
     * Haalt het Spelbord met meegegeven id op uit de database en wijst dit toe aan attribuut spelbord en
     * retourneert dit ook.
     * @param id id van het gewenste Spelbord
     * @return gewenste Spelbord
     */
    
    //UC6 wijzigSpelbord
    public Spelbord wijzigSpelbord(int id) {
        List<Spelbord> spelborden = spelmapper.geefSpelborden(this.naam);

        for (Spelbord s : spelborden) {
            if (s.getId() == id) {
                this.spelbord = s;
            }

        }
        spelmapper.initialiseerSpelbord(spelbord);
        
        return spelbord;
    }
    
    
    /**
     * Plaatst het item met meegegeven itemcode op meegegeven positie
     * @param item int dat een bepaald item voorstelt
     * @param positie positie waarop het item geplaatst moet worden
     */
    
    public void kiesItem(int item,int[] positie){
        spelbord.kiesItems(item, positie);
    }
    
    
    /**
     * Voegt het huidige Spelbord toe aan dit Spel in de database
     * @param naam 
     * @return 
     */
    
    public Spel configureerNieuwSpelbord(String naam){
        
        spelmapper.voegSpelbordToe(this);
//        int veldX,veldY;
//        
//        //spelbord ophalen
//        Spelbord nieuwBord = new Spelbord(this.getSpelbord().getId());
//        
        Veld[][] velden = this.spelbord.getVelden();
        
        spelmapper.voegVeldenToe(spelbord, velden);
        
//        Veld veld;

//        for (Veld[] v : velden) {
//            for (Veld veldje : v) {
//                spelmapper.voegVeldToe(this,this.spelbord, veldje);
//                
//                if( veldje.getMannetje() != null){
//                    spelmapper.voegMannetjeToe(this.spelbord, this);
//                }
//                
//                if (veldje.getKist() != null){
//                    spelmapper.voegKistToe(spelbord, veldje);
//                }
//                if (veldje.getDoel()== true){
//                    veldje.isDoel();
//                }
//                
//            }
//        }

        //velden van spelbord overlopen
//        for( int i = 0; i<10; i++){
//            for(int j = 0; j<10; j++){
//                veld = this.spelbord.getVelden();
//                spelmapper.voegVeldToe(this.getSpelbord(), veld);
//                spelmapper.voegKistToe(this.getSpelbord(), veld);
//                spelmapper.voegMannetjeToe(this.spelbord, this);
//
//            }
//        }
 
        return this;
    }

}
