/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import resources.Taal;

/**
 * FXML Controller class
 *
 * @author Ruben
 */
public class SpelbordPaneelController extends BorderPane
{
    @FXML
    private Label lblTitel;
    @FXML
    private Label lblAantalZetten;
    @FXML
    private Button btnResetSpelbord;
    @FXML
    private Button btnStopSpel;
    @FXML
    private GridPane gpSpelbord;
    @FXML
    private Label lblVoltooid;
    @FXML
    private Label lblNogXAantalSpelborden;
    @FXML
    private Label lblError;

    private DomeinController dc;
    private HoofdPaneelController hpc;
    
    public SpelbordPaneelController(DomeinController dc, HoofdPaneelController hpc){
        this.dc = dc;
        this.hpc = hpc;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SpelbordPaneel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        // In eerste instantie mag de error niet getoond worden
        lblError.setVisible(false);
        
        spelbordLaden();
        setTaalLabels();
        spelbordSpelen();        
    }  

    @FXML
    private void btnResetSpelbordOnAction(ActionEvent event) {
        dc.verplaatsMannetje(9);
        spelbordLaden();
    }

    @FXML
    private void btnStopSpelOnAction(ActionEvent event) {
        hpc.initialiseerKiesSpelPaneelController();
    }
    
    private void spelbordLaden() {
        String[][] bord = dc.geefSpelbord();
        String aantalVoltooid = Integer.toString(dc.geefAantalVoltooideEnTotaalAantalSpelborden()[0]);
        String aantalSpelborden = Integer.toString(dc.geefAantalVoltooideEnTotaalAantalSpelborden()[1]);
        
        lblAantalZetten.setText(Taal.getText("label.aantalVerplaatsingen") + ": " + dc.geefAantalVerplaatsingen());
        lblVoltooid.setText(Taal.getText("label.aantalVoltooid") + ": " + aantalVoltooid);
        lblNogXAantalSpelborden.setText(Taal.getText("label.aantalSpelborden") + ": " + aantalSpelborden);
        
        int tellerRij = 0, tellerKolom = 0;
        Label lb;
        Image img;
        ImageView imgView;
        
        for (String[] rij : bord) {            
            for (String kolom : rij) {
                switch(kolom) {
                    case "*" : img = new Image("images/wall.jpg");
                        break;
                    case "" : default: img = new Image("images/walkable.png");
                        break;
                    case "M" : img = new Image("images/player.png");
                        break;
                    case "K" : img = new Image("images/chest.png");
                        break;
                    case "D" : img = new Image("images/target.png");
                        break;
                    case "S" : img = new Image("images/chestOnGoal.png");
                        break;
                }
                
                imgView = new ImageView(img);
                imgView.setFitWidth(32);
                imgView.setFitHeight(32);
                
                gpSpelbord.add(imgView, tellerKolom, tellerRij);
                tellerKolom++;
            }

            tellerRij++;
            
            tellerKolom = 0; // Teller weer op nul zetten zodat voor elke nieuwe rij weer de juiste kolom index heeft
        }
    }
    
    private void spelbordSpelen() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() { // Registreert toetsaanslagen
            @Override
            public void handle(KeyEvent ke) {
                try {
                    if (ke.getCode().equals(KeyCode.UP)) {
                        dc.verplaatsMannetje(5);
                    }
                    else if(ke.getCode().equals(KeyCode.DOWN)) {
                        dc.verplaatsMannetje(2);
                    }
                    else if(ke.getCode().equals(KeyCode.LEFT)) {
                        dc.verplaatsMannetje(1);
                    }
                    else if(ke.getCode().equals(KeyCode.RIGHT)) {
                        dc.verplaatsMannetje(3);
                    }

                    if (dc.isSpelbordVoltooid()) {
                        if (!dc.isEindeSpel()) {
                            // Spelbord nog eenmaal laden zodat de laatste zet nog zichtbaar is alvorens volgend spel te laden
                            spelbordLaden();
                            
                            // Succes bericht wanneer spelbord compleet is
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Conformation dialog");
                            alert.setContentText(Taal.getText("spelbordVoltooid"));
                            alert.showAndWait();
                            
                            // Volgend speldbord laden
                            dc.verplaatsMannetje(9);
                        } else {
                            // Spelbord nog eenmaal laden zodat de laatste zet nog zichtbaar is alvorens terug te gaan naar het spel overzicht
                            spelbordLaden();

                            // Succes bericht wanneer spel volledig is uitgespeeld
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Conformation dialog");
                            alert.setContentText(Taal.getText("eindeSpel"));
                            alert.showAndWait();
                            
                            hpc.initialiseerKiesSpelPaneelController();
                        }
                    }
                    
                    lblError.setVisible(false); // Verbergt error bericht weer mocht het true zijn
                } catch(IllegalArgumentException e) {
                   // Error bericht wanneer speler er richting kiest die niet mogelijk is
                    lblError.setText(e.getMessage());
                    lblError.setVisible(true);
                }catch(Exception e) {
                    System.out.println(e.getStackTrace()[0] + " " + e.getMessage());
                }
                
                    
               spelbordLaden();
               
            }
        });
    }
    
    private void setTaalLabels() {       
        lblTitel.setText(dc.getSpel().getNaam());
        btnResetSpelbord.setText(Taal.getText("btn.resetSpelbord"));
        btnStopSpel.setText(Taal.getText("btn.stopSpel"));        
    }
}
