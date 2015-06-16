/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.application.Platform;
import resources.Taal;

/**
 *
 * @author Xander
 */
public class SpelbordPaneel {

    private final DomeinController dc;
    private final KiesPaneel kp;

    public SpelbordPaneel(DomeinController dc, KiesPaneel kp) {
        this.dc = dc;
        this.kp = kp;
    }

    public void speelSpelbord() {
        Scanner input = new Scanner(System.in);
        int richting = 0;
        int grootte = 12;
        // MAG NIET Spelbord spelbord;
        String[][] bord = new String[12][12];
        String[][] bordKlein;
        boolean invoerFout = true;

        do {
            do { // Controleer op invoer
                try {
                    
                    //Maak de outline van het bord en vul de rest met X (=muren)
                    for (int i = 0; i < grootte; i++) {
                        for (int j = 0; j < grootte; j++) {

                            if ((i == 0 || i == grootte - 1) && (j == 0 || j == grootte - 1)) {
                                bord[i][j] = "+";
                            } else if (i == 0 || i == grootte - 1) {
                                bord[i][j] = "-";
                            } else if (j == 0 || j == grootte - 1) {
                                bord[i][j] = "|";
                            } //else {
//                                bord[i][j] = "*";
//                            }
                        }
                    }
                    
                    // Spelbord genereren
                    bordKlein = dc.geefSpelbord();
                    
                    for(int i = 0; i < bordKlein.length; i++){
                        for(int j = 0; j < bordKlein[i].length; j++){
                            bord[i+1][j+1] = bordKlein[i][j];
                            
                        }
                    }

                    // Spelbord printen
                    for (String[] rij : bord) {
                        for (String kolom : rij) {
                            System.out.print(kolom + " ");
                        }

                        System.out.println("");
                    }

                    if (dc.isSpelbordVoltooid()) {
                        System.out.println("\n\n" + Taal.getText("spelbord_voltooid") + "\n\n");
                        if (!dc.isEindeSpel()) {
                            richting = 9;
                        } else {
                            richting = 0;
                            System.out.println(Taal.getText("spel_beeindigd") + "\n\n");
                        }
                    } else {

                        System.out.println(Taal.getText("aantal_verplaatsingen") + ": " + dc.geefAantalVerplaatsingen());
                        // Richting kiezen
                        System.out.println(Taal.getText("verplaats_man"));
                        System.out.println("5: " + Taal.getText("omhoog"));
                        System.out.println("2: " + Taal.getText("omlaag"));
                        System.out.println("1: " + Taal.getText("links"));
                        System.out.println("3: " + Taal.getText("rechts"));
                        System.out.println("0: " + Taal.getText("stoppen"));
                        System.out.println("9: " + Taal.getText("spelbord_opnieuw"));
                        System.out.print(Taal.getText("keuze") + ": ");
                        richting = input.nextInt();

                    }
                    if (richting == 0) {
                        Platform.exit();
                    }

                    if (!dc.isEindeSpel()) {
                        invoerFout = false;

                        //WORDT OPGEROEPEN VIA DOMEINCONTROLLER
                        dc.verplaatsMannetje(richting);
                    }

                } catch (InputMismatchException e) {
                    System.out.println(Taal.getText("geef_nummer_richting"));
                    input.nextLine();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(Taal.getText("probeer_opnieuw"));
                    input.nextLine();
                }
            } while (invoerFout);
        } while (richting != 0);
    }
    
    public void wijzigSpelbord(int id){
            Scanner input = new Scanner(System.in);
            String naam = "";
            String[][] spelbord = new String[10][10];
            int item = 0;
            int[] positie = new int[2];
            boolean invoerFout = true;
            boolean invoerFout2 = true;
            boolean invoerFout3 = true;
            int antwoord = 0;
                
            do{
                
                    // Spelbord genereren
                    spelbord = dc.wijzigSpelbord(id);

                    // Spelbord printen
                    for (String[] rij : spelbord) {
                        for (String kolom : rij) {
                            System.out.print(kolom + " ");
                        }
                        System.out.println("");
                    }
            
              do{
                  invoerFout = true;
                do{ 
                    try{
                        System.out.printf(Taal.getText("wijzig_item_met_positie"));
                        System.out.println("");
                        System.out.println("1: "+Taal.getText("veld"));
                        System.out.println("2: "+Taal.getText("mannetje"));
                        System.out.println("3: "+Taal.getText("kist"));
                        System.out.println("4: "+Taal.getText("doel"));
                        System.out.println("9: "+Taal.getText("voltooid"));
                        System.out.println("");
                        System.out.print(Taal.getText("keuze_item") + ": ");
                        item = input.nextInt();
                                                
                        if(item == 9){
                            break;
                        }
                        
                        if(item < 1 || item > 4){
                            throw new IllegalArgumentException(Taal.getText("kies_juiste_item"));
                        }
                        
                        invoerFout = false;
                    }catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        input.nextLine();
                    }
                    catch (InputMismatchException e) {
                        System.out.println(Taal.getText("numerieke_waarde"));
                        input.nextLine();
                    }
                    catch (Exception e) {
                        System.out.println(Taal.getText("probeer_opnieuw"));
                        input.nextLine();
                    }
                }while(invoerFout);
                
                    System.out.println("");
                    invoerFout2 = true;
                do{
                    if(item == 9){
                        break;
                    }
                      
                    try{
                        
                        System.out.print(Taal.getText("keuze_positie"));
                        int geefPositie = input.nextInt();
                        
                        positie[0] = geefPositie/10;
                        positie[1] = geefPositie%10;
                        
                        System.out.printf("%n%s%n",Taal.getText("item_geplaatst"));
                        
                        System.out.printf("%n%s%n",Taal.getText("huidig_spelbord"));
                        
                        //spelbord printen na gewijzigde item
                        for (String[] rij : spelbord){
                            for (String kolom : rij){
                                System.out.print(kolom + " ");
                            }
                            System.out.println("");
                        }
                        
                        if(geefPositie < 0 || geefPositie > 99){
                            throw new IllegalArgumentException(Taal.getText("kies_juiste_positie"));
                        }
                        
                        invoerFout2 = false;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        input.nextLine();
                    }
                    catch (InputMismatchException e) {
                        System.out.println(Taal.getText("numerieke_waarde"));
                        input.nextLine();
                    }
                    catch (Exception e) {
                        System.out.println(Taal.getText("probeer_opnieuw"));
                        input.nextLine();
                    }
                
                }while(invoerFout2);
                
                dc.kiesItem(item, positie);
                
                
              }while(!(item == 9));
                
//              invoerFout3 = true;
//              do{
//                try{
//                    System.out.printf(Taal.getText("ander_spelbord_wijzigen"));
//                    System.out.println("");
//                    System.out.println("1: "+Taal.getText("ja"));
//                    System.out.println("2: "+Taal.getText("nee"));
//                    System.out.print(Taal.getText("keuze") + ": ");
//                    antwoord = input.nextInt();
//                    
//                     // nieuw spelbord
//                if (antwoord == 1) {
//                    dc.configureerNieuwSpelbord(naam);
//                    kp.spelWijzigen();
//                }
//                if (antwoord == 2) {
//                    dc.configureerNieuwSpelbord(naam);
//                    kp.kiesOfMaakSpel();
//                }
//                    
//                    if(antwoord < 1 || antwoord > 2)
//                        throw new IllegalArgumentException(Taal.getText("kies_tussen_1_en_2"));
//                    
//                    invoerFout3 = false;
//                } catch (IllegalArgumentException e) {
//                    System.out.println(e.getMessage());
//                    input.nextLine();
//                }
//                catch (InputMismatchException e) {
//                    System.out.println(Taal.getText("numerieke_waarde"));
//                    input.nextLine();
//                }
//                catch (Exception e) {
//                    System.out.println(Taal.getText("probeer_opnieuw"));
//                    input.nextLine();
//                }
//              }while(invoerFout3);
            }while(antwoord == 1); 
    }
}

