/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.DomeinController;
import gui.HoofdPaneel;
import gui.HoofdPaneelController;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistentie.SpelMapper;

/**
 *
 * @author Ruben
 */
public class StartUp extends Application
{    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        System.out.println("1. Console ----- 2. Mooie schermpjes");
        
        Scanner input = new Scanner(System.in);
        boolean invoerFout = true;
        int keuze = 0;
        
        do {
            try {
                System.out.print("Keuze: ");
                
                keuze = input.nextInt();
                
                if(keuze < 1 || keuze > 2)
                    throw new IllegalArgumentException();
                
                invoerFout = false;
            } catch(java.util.InputMismatchException e) {
                System.out.println("Gelieve een getal in te geven!");
                input.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Enkel 1 en 2 zijn toegelaten!");
            } catch(Exception e) {
                System.out.println("Probeer opnieuw");
            }  
        } while(invoerFout);
        
        
        if(keuze == 1){ 
            DomeinController dc = new DomeinController();
            new HoofdPaneel(dc);
        }
        
        else {
            DomeinController dc = new DomeinController();
            Scene scene = new Scene(new HoofdPaneelController(dc));

            primaryStage.setScene(scene);
            primaryStage.setTitle("Sokoban");

            primaryStage.setWidth(650);
            primaryStage.show();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
