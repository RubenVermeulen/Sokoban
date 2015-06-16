package gui;

import domein.DomeinController;
import java.util.InputMismatchException;
import java.util.Scanner;
import resources.Taal;

/**
 *
 * @author Martin
 */
public class NieuwSpelPaneel {

    private final DomeinController dc;
    private final KiesPaneel kp;

    NieuwSpelPaneel(DomeinController dc, KiesPaneel kp) {
        this.dc = dc;
        this.kp = kp;
    }

        public void spelMaken(){
//    System.out.println("Nieuw spel maken");
            Scanner input = new Scanner(System.in);
            String naam = "";
            String[][] bord = new String[10][10];
            int item = 0;
            int[] positie = new int[2];
            boolean invoerFout = true;
            boolean invoerFout2 = true;
            boolean invoerFout3 = true;
            int antwoord = 0;
            
            System.out.printf(Taal.getText("kies_spelnaam") + ": ");
            naam = input.next();
            dc.configureerNieuwSpel(naam);
                
            do{
                int positieMannetje = 1000;
                boolean eenMannetje = false;
                
                System.out.println("");
                System.out.println(Taal.getText("nieuw_spelbord_aanmaken"));
                // Spelbord genereren
                int grootte = bord.length;
                int k = 0;
                for (int i = 0; i < bord.length; i++){
                    for (int j = 0; j < bord[i].length; j++){
                        bord[i][j] = String.format("%02d", k);
                        k++;
                    }
                }
            
              do{
                  invoerFout = true;
                do{ 
                    try{
                        System.out.printf(Taal.getText("plaats_item_en_positie"));
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
                        
                        if(item == 2 && eenMannetje == true){
                                throw new IllegalArgumentException(Taal.getText("eenMannetje"));
                        }
                        
                        if(item == 2){
                            eenMannetje = true;
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
                    
                    String stringItem = "";
                    switch (item){
                        case 1:
                            stringItem = String.format("%2s", " ");
                            break;
                        case 2:
                            stringItem = String.format("%2s", "M");
                            break;
                        case 3:
                            stringItem = String.format("%2s", "K");
                            break;
                        case 4:
                            stringItem = String.format("%2s", "D");
                    }
                        
                    try{

                        // Spelbord printen
                        for (String[] rij : bord){
                            for (String kolom : rij){
                                System.out.print(kolom + " ");
                            }
                            System.out.println("");
                        }
                        
                        System.out.print(Taal.getText("keuze_positie"));
                        int geefPositie = input.nextInt();
                        
                        positie[0] = geefPositie/10;
                        positie[1] = geefPositie%10;
                        
                        if(geefPositie != positieMannetje){
                            bord[positie[0]][positie[1]] = stringItem;
                        }
                        
                        System.out.printf("%n%s%n",Taal.getText("item_geplaatst"));
                        
                        System.out.printf("%n%s%n",Taal.getText("huidig_spelbord"));
                        
                        //spelbord printen na geplaatste item
                        for (String[] rij : bord){
                            for (String kolom : rij){
                                System.out.print(kolom + " ");
                            }
                            System.out.println("");
                        }
                        
                        if(geefPositie == positieMannetje){
                            throw new IllegalArgumentException(Taal.getText("positie_mannetje"));
                        }
                        
                        if(item == 2){
                            positieMannetje = geefPositie;
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
                
                dc.plaatsItem(item, positie);
                
                
              }while(!(item == 9));
                
              invoerFout3 = true;
              do{
                try{
                    System.out.printf(Taal.getText("nieuw_spelbord"));
                    System.out.println("");
                    System.out.println("1: "+Taal.getText("ja"));
                    System.out.println("2: "+Taal.getText("nee"));
                    System.out.print(Taal.getText("keuze") + ": ");
                    antwoord = input.nextInt();
                    
                // Nieuw spelbord
                if (antwoord == 1) {
                    dc.configureerNieuwSpelbord(naam);
                    System.out.printf(Taal.getText("nieuw_spel_geregistreerd"), naam);
                    dc.maakNieuwSpelbord();
                }
                if (antwoord == 2) {
                    dc.configureerNieuwSpelbord(naam);
                    System.out.printf(Taal.getText("nieuw_spel_geregistreerd"), naam);
                    kp.kiesOfMaakSpel();
                }
                    
                    if(antwoord < 1 || antwoord > 2)
                        throw new IllegalArgumentException(Taal.getText("kies_tussen_1_en_2"));
                    
                    invoerFout3 = false;
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
              }while(invoerFout3);
            }while(antwoord == 1); 
    }
}
