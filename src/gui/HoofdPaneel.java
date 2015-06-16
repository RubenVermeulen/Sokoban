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
public class HoofdPaneel {

    private final DomeinController dc;
    private final RegistratiePaneel registratie;
    private final AanmeldPaneel aanmelden;


    public HoofdPaneel(DomeinController dc) {
        this.dc = dc;
        this.registratie = new RegistratiePaneel(dc);
        this.aanmelden = new AanmeldPaneel(dc);
        
        
        this.vraagTaal();
        this.kiesActie();

    }

    public void vraagTaal() {
        Scanner input = new Scanner(System.in);
        int keuze = 1;
        boolean invoerFout = true;
        String[] talen = {
            "nl",
            "fr",
            "en"
        };

        System.out.println("Sokoban:");

        do {
            try {
                System.out.println("1: Voor Nederlands");
                System.out.println("2: Pour Français");
                System.out.println("3: For English");
                System.out.println("");
                System.out.print("Keuze - Choix - Choice: ");
                keuze = input.nextInt();

                if (keuze < 1 || keuze > 3) {
                    String nl = String.format("%-10s: De opgegeven keuze staat niet in de lijst!", "OPGELET");
                    String fr = String.format("%-10s: Votre choix est incorrect!", "ATTENTION");
                    String en = String.format("%-10s: Your choice is not in the list!", "ATTENTION");
                    String exceptionMessage = String.format("%s%n%s%n%s", nl, fr, en);

                    throw new IllegalArgumentException(exceptionMessage);
                }

                System.out.println("");

                invoerFout = false;
            } catch (InputMismatchException e) {
                System.out.printf("%n%-10s: De invoer moet een numerieke waarde zijn!%n", "OPGELET");
                System.out.printf("%-10s: Le saisie doit être un nombre!%n", "ATTENTION");
                System.out.printf("%-10s: The input should be a number!%n%n", "ATTENTION");

                input.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("\n" + e.getMessage() + "\n");
            } catch (Exception e) {
                System.out.printf("%n%-10s: Probeer opnieuw!%n", "OPGELET");
                System.out.printf("%-10s: Réessayer!%n", "ATTENTION");
                System.out.printf("%-10s: Try again!%n%n", "ATTENTION");

                input.nextLine();
            }
        } while (invoerFout);

        dc.setTaal(talen[keuze - 1]);
    }

    public void kiesActie() {
        Scanner input = new Scanner(System.in);
        boolean check = false;
        int keuze = 0;
        do {
            try {
                System.out.printf("%s%n1: %s%n2: %s%n%n%s: ",
                        Taal.getText("doen"),
                        Taal.getText("login"),
                        Taal.getText("registreer"),
                        Taal.getText("keuze")
                );
                keuze = input.nextInt();

                System.out.println("");

                if (keuze == 1 || keuze == 2) {
                    break;
                }
            } catch (InputMismatchException | NumberFormatException r) {
                System.out.printf("%n%s%n%n", Taal.getText("nieuw_nr"));
               

                input.nextLine();
            } catch (Exception e) {
                System.out.printf("%n%s%n%n", Taal.getText("nieuw_nr"));
                

                input.nextLine();
            }
        } while (!check);

        switch (keuze) {
            case 1:
            default:
                do {
                    try {
                        aanmelden.aanmelden();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } while (!dc.isAangemeld());
                break;

            case 2:
                registratie.registreren();
                break;
        }
    }
}
