/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.Scanner;
import resources.Taal;

/**
 *
 * @author Xander
 */
public class RegistratiePaneel
{
    private final DomeinController dc;
    private final KiesPaneel kp;

    public RegistratiePaneel(DomeinController dc) {
        this.dc = dc;
        this.kp = new KiesPaneel(dc);
    }
    
    @SuppressWarnings("empty-statement")
    public void registreren(){
        Scanner input = new Scanner(System.in);
        String naam, voornaam, gebruikersnaam, wachtwoord, bevestiging;
        
        boolean controleBestaat; // Controlevariabele van doWhile 1
        
        do {        // doWhile-lus 1 : blijft herhalen tot een nietbestaande gebruikersnaam wordt ingevoerd
        
            controleBestaat = false; // Controlevariabele van doWhile 2
        
            System.out.print(Taal.getText("naam")+": ");
            naam = input.nextLine();

            System.out.print(Taal.getText("voornaam")+": ");
            voornaam = input.nextLine();

            boolean controle;
                    
            do {    // doWhile-lus 1.1 : blijft herhalen totdat gebruikersnaam correct is
                controle = true;
                System.out.print(Taal.getText("gebruikersnaam")+": ");
                gebruikersnaam = input.nextLine();
                if (gebruikersnaam.isEmpty()){
                    controle = false;
                    System.out.print(Taal.getText("error_naam")+"\n");
                }
                else if (gebruikersnaam.length()<8){
                    controle = false;
                    System.out.print(Taal.getText("error_naam_lengte")+"\n");
                }
            } while (!controle);


            do {    // doWhile-lus 1.2 : blijft herhalen totdat wachtwoorden overeenkomen

                do {// doWhile-lus 1.2.1 : blijft herhalen totdat wachtwoord aan vereisten voldoet
                    controle = true;

                    System.out.print(Taal.getText("wachtwoord")+": ");
                    wachtwoord = input.nextLine();

                    if(!wachtwoord.matches(".*[0-9]+.*") || !wachtwoord.matches(".*[A-Z]+.*") || !wachtwoord.matches(".*[a-z]+.*")){
                        controle = false;
                        System.out.print(Taal.getText("error_pass")+"\n");
                    }
                    
                    else if (wachtwoord.length()<8){
                    controle = false;
                    System.out.print(Taal.getText("error_pass_lengte")+"\n");
                }

                } while(!controle);

                controle = true;

                System.out.print(Taal.getText("bevestig_pass")+": ");
                bevestiging = input.nextLine();

                if(!wachtwoord.equals(bevestiging)){
                    controle = false;
                    System.out.print(Taal.getText("error_overeenkomst")+ "\n" + Taal.getText("opnieuw")+"\n");
                }


            } while (!controle);

            
            // probeert te registeren, indien dit niet lukt zal een Exception ontvangen worden
            // en zal de whilelus opnieuw beginnen.
            try
            {
            dc.registreer(
                    gebruikersnaam.trim(),
                    wachtwoord.trim(),
                    naam.trim(),
                    voornaam.trim());
            }
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                controleBestaat = true;
            }
            
        } while(controleBestaat);
            
            
        System.out.printf(Taal.getText("geregistreerd"));
        System.out.printf(Taal.getText("succes"), gebruikersnaam);
        kp.kiesOfMaakSpel();
    }
    
}
