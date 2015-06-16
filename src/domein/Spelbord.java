/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import resources.Taal;

/**
 *
 * @author Ebu Bekir
 */
public class Spelbord {

    private boolean isVoltooid;
    private int id;
    private Mannetje mannetje;
    private List<Kist> kisten;
    private Veld[][] velden;
    private int aantalVerplaatsingen;
    
    /**
     * Wijst de meegegeven parameter toe aan het id attribuut.
     * De Attributen isVoltooid en aantalVerplaatsingen worden geïnitialiseerd.
     * 
     * @param id id van spelbord
     */
    
    public Spelbord(int id) {
        this.id = id;
        isVoltooid = false;
        aantalVerplaatsingen = 0;
    }
    
    /**
     * Constructor zonder initialisatie.
     */
    //UC5 nieuw spel
    public Spelbord() {
    }
    
    /**
     * Verplaatst het item op het spelbord.
     * 
     * @param item int die een mannetje, veld of kist voorstelt
     * @param positie de coördinaten waar het item naartoe gaat
     */
    
    public void plaatsItem(int item, int[] positie) {
        //1: veld
        //2: mannetje
        //3: kist
        //4: doel
       

        //Velden ophalen voor Spelbord
        //Veld[][] velden = this.getVelden();
        if(velden == null){
            velden = new Veld[10][10];
        }
        
        Veld veld;
 
//        // Mannetje object ophalen voor Spelbord
//        Mannetje mannetje = this.getMannetje();

        // Kisten ophalen voor Spelbord
        List<Kist> kisten = this.getKisten();
        Kist kist;
 

//        for (int i = 0; i < velden.length; i++) {
//            for (int j = 0; j < velden[i].length; j++) {
//                    
//                if (i == positie[0] && j == positie[1]) {
//                    
                    
                    velden[positie[0]][positie[1]] = new Veld(positie[0],positie[1],false);
                    veld = velden[positie[0]][positie[1]];

                    switch (item) {

                        case 1:
                            veld.setX(positie[0]);
                            veld.setY(positie[1]);
                            break;

                        case 2:
                            if (veld.getX() == positie[0] && veld.getY() == positie[1]) {
                                mannetje = new Mannetje(veld);
                                veld.setMannetje(mannetje);
                                mannetje.setVeld(veld);
                                
                            }
                            break;
                        case 3:
                                kist = new Kist(veld);
                                if (veld.getX() == positie[0] && veld.getY() == positie[1]) {
                                    veld.setKist(kist);
                                    kist.setVeld(veld);
                                }
                            break;
                        case 4:
                            if (veld.getX() == positie[0] && veld.getY() == positie[1]) {
                                veld.setDoel(true);
                            }
                            break;
                    }
//                }
//            }
//        }
    }

    /**
     * 
     * @return retourneert mannetje attribuut
     */
    
    public Mannetje getMannetje() {
        return mannetje;
    }

    /**
     * Wijst de meegegeven parameter toe aan het mannetje attribuut
     * 
     * @param mannetje mannetje object
     */
    
    public void setMannetje(Mannetje mannetje) {
        this.mannetje = mannetje;
    }

    /**
     * 
     * @return retourneert kisten attribuut
     */
    
    public List<Kist> getKisten() {
        return kisten;
    }
    
    /**
     * Wijst de meegegeven parameter toe aan het kisten attribuut
     * 
     * @param kisten lijst van kist objecten
     */

    public void setKisten(List<Kist> kisten) {
        this.kisten = kisten;
    }
    
    /**
     * 
     * @return retourneert velden attribuut
     */

    public Veld[][] getVelden() {
        return velden;
    }
    
    /**
     * Wijst de meegegeven parameter toe aan het velden attribuut
     * 
     * @param velden lijst van veld objecten
     */

    public void setVelden(Veld[][] velden) {
        this.velden = velden;
    }
    
    /**
     * 
     * @return retourneert id attribuut
     */

    public int getId() {
        return id;
    }
    
    /**
     * Wijst de meegegeven parameter toe aan het id attribuut
     * 
     * @param id id van spelbord
     */

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * 
     * @return retourneert isVoltooid attribuut
     */

    public boolean getIsVoltooid() {
        return isVoltooid;
    }
    
    /**
     * Wijst de meegegeven parameter toe aan het isVoltooid attribuut
     * 
     * @param isVoltooid is het spelbord voltooid
     */

    public void setIsVoltooid(boolean isVoltooid) {
        this.isVoltooid = isVoltooid;
    }
    
    /**
     * 
     * @return retourneert aantalVerplaatsingen attribuut
     */

    public int getAantalVerplaatsingen() {
        return aantalVerplaatsingen;
    }
    
    /**
     * Verplaatst mannetje op het spelbord. De richting wordt verpaald door de parameter richting.
     * 
     * @param richting richting waarheen mannetje verplaatst
     */

    public void verplaatsMannetje(int richting) {
        // 5: omhoog
        // 2: omlaag
        // 1: links
        // 3: rechts

        // Velden ophalen voor Spelbord
        Veld[][] velden = this.getVelden();

        // Mannetje object ophalen voor Spelbord
        Mannetje mannetje = this.getMannetje();

        // Kisten ophalen voor Spelbord
        List<Kist> kisten = this.getKisten();
        Kist kistAangrenzend = null;
        int kistX, kistY;
        // Start verplaatsing
        int x = mannetje.getVeld().getX();
        int y = mannetje.getVeld().getY();
        boolean verplaatsingMannetje = false;

        // Controleer of er nog een veld naast het mannetje beschikbaar is voor de opgegeven richting
        if (richting == 5 && velden[x - 1][y] != null) {
            verplaatsingMannetje = true;
        } else if (richting == 2 && velden[x + 1][y] != null) {
            verplaatsingMannetje = true;
        } else if (richting == 1 && velden[x][y - 1] != null) {
            verplaatsingMannetje = true;
        } else if (richting == 3 && velden[x][y + 1] != null) {
            verplaatsingMannetje = true;
        }

        if (verplaatsingMannetje) {
            // Controleer of een kist naast het mannetje staat voor de opgegeven richting
            for (Kist kist : kisten) {
                kistX = kist.getVeld().getX();
                kistY = kist.getVeld().getY();
                if (richting == 5 && x - 1 == kistX && y == kistY) {
                    kistAangrenzend = kist;
                } else if (richting == 2 && x + 1 == kistX && y == kistY) {
                    kistAangrenzend = kist;
                } else if (richting == 1 && x == kistX && y - 1 == kistY) {
                    kistAangrenzend = kist;
                } else if (richting == 3 && x == kistX && y + 1 == kistY) {
                    kistAangrenzend = kist;
                }
            }

            if (kistAangrenzend != null) {
                verplaatsingMannetje = false;

                // Controleer of kist kan verschoven worden (is er een veld?)
                if (richting == 5 && velden[x - 2][y] != null) {
                    verplaatsingMannetje = true;
                } else if (richting == 2 && velden[x + 2][y] != null) {
                    verplaatsingMannetje = true;
                } else if (richting == 1 && velden[x][y - 2] != null) {
                    verplaatsingMannetje = true;
                } else if (richting == 3 && velden[x][y + 2] != null) {
                    verplaatsingMannetje = true;
                }
            }

            if (kistAangrenzend != null) {

                // Controleer of kist kan verschoven worden (staat er geen kist?)
                for (Kist kist : kisten) {
                    kistX = kist.getVeld().getX();
                    kistY = kist.getVeld().getY();
                    if (richting == 5 && x - 2 == kistX && y == kistY) {
                        verplaatsingMannetje = false;
                    } else if (richting == 2 && x + 2 == kistX && y == kistY) {
                        verplaatsingMannetje = false;
                    } else if (richting == 1 && x == kistX && y - 2 == kistY) {
                        verplaatsingMannetje = false;
                    } else if (richting == 3 && x == kistX && y + 2 == kistY) {
                        verplaatsingMannetje = false;
                    }
                }
            }
        }

        // Verplaats mannetje en mogelijke kisten
        switch (richting) {
            case 5:
                if (verplaatsingMannetje) {
                    aantalVerplaatsingen++;

                    Veld veld = velden[x - 1][y];
                         
                    veld.setMannetje(mannetje);
                    mannetje.setVeld(veld);

                    if (kistAangrenzend != null) {
                        Veld veldKist = velden[x - 2][y];
                        veldKist.setKist(kistAangrenzend);
                        kistAangrenzend.setVeld(veldKist);
                    }
                } else {
                    throw new IllegalArgumentException(Taal.getText("error_omhoog"));
                }

                break;
            case 2:
                if (verplaatsingMannetje) {
                    aantalVerplaatsingen++;

                    Veld veld = velden[x + 1][y];
                         
                    veld.setMannetje(mannetje);
                    mannetje.setVeld(veld);

                    if (kistAangrenzend != null) {
                        Veld veldKist = velden[x + 2][y];
                        veldKist.setKist(kistAangrenzend);
                        kistAangrenzend.setVeld(veldKist);
                    }
                } else {
                    throw new IllegalArgumentException(Taal.getText("error_omlaag"));
                }

                break;
            case 1:
                if (verplaatsingMannetje) {
                    aantalVerplaatsingen++;

                    Veld veld = velden[x][y - 1];
                      
                    veld.setMannetje(mannetje);
                    mannetje.setVeld(veld);

                    if (kistAangrenzend != null) {
                        Veld veldKist = velden[x][y - 2];
                        veldKist.setKist(kistAangrenzend);
                        kistAangrenzend.setVeld(veldKist);
                    }
                } else {
                    throw new IllegalArgumentException(Taal.getText("error_links"));
                }

                break;
            case 3:
                if (verplaatsingMannetje) {
                    aantalVerplaatsingen++;

                    Veld veld = velden[x][y + 1];
                         
                    veld.setMannetje(mannetje);
                    mannetje.setVeld(veld);

                    if (kistAangrenzend != null) {
                        Veld veldKist = velden[x][y + 2];
                        veldKist.setKist(kistAangrenzend);
                        kistAangrenzend.setVeld(veldKist);
                    }
                } else {
                    throw new IllegalArgumentException(Taal.getText("error_rechts"));
                }

                break;
            case 9:
                System.out.println("Spelbord wordt opnieuw geladen ...");
//                spel.geefSpelbord();
//                this.geefSpelbord();
                break;
            default: // Waarden wijzigen niet
                throw new IllegalArgumentException(Taal.getText("probeer_opnieuw"));
        }       
    }

    public void voltooiSpelbord(boolean isVoltooid) {
        this.isVoltooid = isVoltooid;
    }

    public boolean isSpelbordVoltooid() {
        int kistX, kistY;
        int aantalKistenOpDoel = 0;
        for (Kist kist : kisten) {
            kistX = kist.getVeld().getX();
            kistY = kist.getVeld().getY();

            if (velden[kistX][kistY].getDoel()) {
                aantalKistenOpDoel++;
            }
        }
        
        boolean res = aantalKistenOpDoel == kisten.size();
        
        setIsVoltooid(res);
        
        return res;
    }

    /**
     * Kies een item voor de gekozen coördinaten
     * 
     * @param item mannetje, kist of doel
     * @param positie coördinaten voor item
     */
    
    public void kiesItems(int item, int[] positie){
        //1: mannetje
        //2: kist
        //3: doel

        // Velden ophalen voor Spelbord
        if(velden == null){
            velden = new Veld[10][10];
        }
        Veld veld;

        // Mannetje object ophalen voor Spelbord
        Mannetje mannetje = this.getMannetje();
//        int manX = mannetje.getVeld().getX();
//        int manY = mannetje.getVeld().getY();

        // Kisten ophalen voor Spelbord
        List<Kist> kisten = this.getKisten();
//        int kistX, kistY;
        
         velden[positie[0]][positie[1]] = new Veld(positie[0],positie[1],false);
         veld = velden[positie[0]][positie[1]];
        


                    switch (item) {
                        
                        case 1:
                            veld.setX(positie[0]);
                            veld.setY(positie[1]);
                            break;

                        case 2:
                            if (veld.getX() == positie[0] && veld.getY() == positie[1]) {
                                veld.setMannetje(mannetje);
                                mannetje.setVeld(veld);
                            }
                            break;
                        case 3:
                            for (Kist kist : kisten) {
//                                kistX = kist.getVeld().getX();
//                                kistY = kist.getVeld().getY();
                                if (veld.getX() == positie[0] && veld.getY() == positie[1]) {
                                    veld.setKist(kist);
                                    kist.setVeld(veld);
                                }
                            }
                            break;
                        case 4:
                            if (veld.getX() == positie[0] && veld.getY() == positie[1]) {
                                veld.setDoel(true);
                            }
                            break;
                    }
                    
                    System.out.printf("%n%s%n%n","Item geplaatst!");

    }
}
