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
 * @author Ruben
 */
public class AanmeldPaneel
{

    private final DomeinController dc;
    private final KiesPaneel kp;

    public AanmeldPaneel(DomeinController dc) {
        this.dc = dc;
        this.kp = new KiesPaneel(dc);
        
        
    }

    public void aanmelden() {
        Scanner input = new Scanner(System.in);
        String gebruikersnaam, wachtwoord;

        System.out.print(Taal.getText("gebruikersnaam") + ": ");
        gebruikersnaam = input.next();

        System.out.print(Taal.getText("wachtwoord") + ": ");
        wachtwoord = input.next();
        
        dc.meldAan(gebruikersnaam, wachtwoord);
                
        if (!dc.isAangemeld()) {
            System.out.print(Taal.getText("error_aanmelden")+"\n");
        }
        else {
            System.out.printf(Taal.getText("succes"),gebruikersnaam);
            kp.kiesOfMaakSpel();
        }
    }
}
