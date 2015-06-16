/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.InputMismatchException;
import java.util.Scanner;
import resources.Taal;

/**
 *
 * @author Xander
 */
public class KiesPaneel {
    
    private final DomeinController dc;
    private final SpelbordPaneel sp;
    private final NieuwSpelPaneel nsp;

    KiesPaneel(DomeinController dc) {
        this.dc = dc;
        this.sp = new SpelbordPaneel(dc, this);
        this.nsp = new NieuwSpelPaneel(dc, this);
    }
    
        public void kiesOfMaakSpel() {
            if(dc.isAdmin()) {
                Scanner input = new Scanner(System.in);
                //boolean check = false;
                int keuze;

                System.out.println("\n");
                System.out.printf("%s%n1: %s %n2: %s %n3: %s %n%n0: %s %n%n%s: ",
                                Taal.getText("doen"),
                                Taal.getText("speel_spel"),
                                Taal.getText("nieuw_spel"),
                                Taal.getText("wijzig_spel"),
                                Taal.getText("stoppen"),
                                Taal.getText("keuze"));

                keuze = input.nextInt();

                switch (keuze) {
                    case 0:
                        System.exit(0);
                    case 1:
                    default:
                        do {
                            try {
                                spelKiezen();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!dc.isAangemeld());
                        break;
                    case 2:
                        nsp.spelMaken();
                        break;
                    case 3:
                        spelWijzigen();
                        break;
                }
            }
            else {
                try {
                    spelKiezen();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        
    }
    
    public void spelKiezen(){
        Scanner input = new Scanner(System.in);
        String spel;
        int keuze = 9;
        String[] spelnamen;
        boolean invoerFout = true;
        spelnamen = dc.geefSpelnamen();
        
        do {
            try {
                System.out.println("\n");
                System.out.printf(Taal.getText("kies_spel")+"%n");
                for(int i = 0; i < spelnamen.length ; i++){
                    System.out.println((i+1) + ": " + spelnamen[i]);
                }
                
                System.out.println("-1: "+Taal.getText("naar_administratiemenu"));
                System.out.println("0: "+Taal.getText("stoppen"));

                System.out.printf(Taal.getText("keuze")+ ": ");

                keuze = input.nextInt();
                
                if(keuze < -1 || keuze > spelnamen.length) {
                    throw new IllegalArgumentException(Taal.getText("niet_in_lijst"));
                }
                
                invoerFout = false;
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
        } while (invoerFout);
        
        
        
        if (keuze == 0){
            System.exit(0);
        }
        else if(keuze == -1) {
            kiesOfMaakSpel();
        }
        
        spel = spelnamen[keuze-1];
        
        dc.kiesSpel(spel);
        
        sp.speelSpelbord();
        this.spelKiezen();
    }
    
    public void spelWijzigen(){
        Scanner input = new Scanner(System.in);
        String spel;
        int keuze = 9;
        String[] spelnamen;
        boolean invoerFout = true;
        spelnamen = dc.geefSpelnamen();
        
        do {
            try {
                System.out.println("\n");
                System.out.printf(Taal.getText("kies_spel")+"%n");
                for(int i = 0; i < spelnamen.length ; i++){
                    System.out.println((i+1) + ": " + spelnamen[i] + "\n");
                }
                
                System.out.println("-1: "+Taal.getText("naar_administratiemenu"));
                System.out.println("0: "+Taal.getText("stoppen"));

                System.out.printf(Taal.getText("keuze")+ ": ");

                keuze = input.nextInt();
                
                if(keuze < -1 || keuze > spelnamen.length) {
                    throw new IllegalArgumentException(Taal.getText("niet_in_lijst"));
                }
                
                invoerFout = false;
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
        } while (invoerFout);
        
        
        
        if (keuze == 0){
            System.exit(0);
        }
        else if(keuze == -1) {
            kiesOfMaakSpel();
        }
        
        spel = spelnamen[keuze-1];
        
        dc.kiesSpel(spel);

       
        this.spelbordWijzigen();
        
    }
    
        public void spelbordWijzigen(){
        Scanner input = new Scanner(System.in);
        int id;
        int keuze = 9;
        int[] spelbordIds;
        boolean invoerFout = true;
        spelbordIds = dc.geefSpelbordIds();
        
        do {
            try {
                System.out.println("\n");
                System.out.printf(Taal.getText("kies_spelbord")+"%n");
                for(int i = 0; i < spelbordIds.length ; i++){
                    System.out.println((i+1) + ": " + spelbordIds[i] + "\n");
                }
                
                System.out.println("-1: "+Taal.getText("naar_administratiemenu"));
                System.out.println("0: "+Taal.getText("stoppen"));

                System.out.printf(Taal.getText("keuze")+ ": ");

                keuze = input.nextInt();
                
                if(keuze < -1 || keuze > spelbordIds.length) {
                    throw new IllegalArgumentException(Taal.getText("niet_in_lijst"));
                }
                
                invoerFout = false;
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
        } while (invoerFout);
        
        
        
        if (keuze == 0){
            System.exit(0);
        }
        else if(keuze == -1) {
            kiesOfMaakSpel();
        }
        
        id = spelbordIds[keuze-1];
        
        dc.geefSpelbordIds();

        sp.wijzigSpelbord(id);
        
        this.spelKiezen();
        
    }
    
//    public void spelbordSpelenWijzigen(int id) {
//        Scanner input = new Scanner(System.in);
//        //boolean check = false;
//        int keuze;
//        
//        System.out.println("\n");
//        System.out.printf("%s%n 1:%s %n 2:%s %n%n %s: ",
//                        Taal.getText("doen"),
//                        Taal.getText("speel_spelbord"),
//                        Taal.getText("wijzig_spelbord"),
//                        Taal.getText("keuze"));
//        
//        keuze = input.nextInt();
//        
//        switch (keuze) {
//            case 1:
//            default:
//                do {
//                    try {
//                        sp.speelSpelbord();
//                    } catch (Exception e) {
//                        System.out.println(e);
//                    }
//                } while (!dc.isAangemeld());
//                break;
//            case 2:
//                sp.wijzigSpelbord(id);
//                break;
//
//        }
//    }
    
//    
//    public void spelSpelen(){
//        sp.speelSpelbord();
//        this.spelKiezen();
//    }
    
}
