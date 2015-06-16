/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import resources.Taal;

/**
 * FXML Controller class
 *
 * @author Ruben
 */
public class NieuwSpelPaneelController extends BorderPane
{
    @FXML
    private Label lblTitel;
    @FXML
    private GridPane gpSpelbord;
    @FXML
    private Button btnResetSpelbord;
    @FXML
    private Button btnAnnuleer;
    @FXML
    private Button btnAanmaken;
    @FXML
    private Button btnVeld;
    @FXML
    private Button btnMannetje;
    @FXML
    private Button btnKist;
    @FXML
    private Button btnDoel;
    @FXML
    private Label lblError;
    
    private DomeinController dc;
    private HoofdPaneelController hpc;
    private Button activeButton;

    NieuwSpelPaneelController(DomeinController dc, HoofdPaneelController hpc) {
        
        this.dc = dc;
        this.hpc = hpc;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NieuwSpelPaneel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        spelbordLaden(); // initialiseer spelbord
        setTaalLabels();
        spelbordMaken();
        
    }
    
    private void spelbordLaden() {
        String[][] bord = new String [10][10];
        
        int tellerRij = 0, tellerKolom = 0;
        Label lb;
        Image img;
        ImageView imgView;

        for (String[] rij : bord) {            
            for (String kolom : rij) {
                img = new Image("images/wall.jpg");
                
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
    
    private void spelbordMaken() {
         gpSpelbord.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                
                for(Node node: gpSpelbord.getChildren()) {
                    
                    if( node instanceof ImageView) {
                        Image img = new Image("images/wall.jpg");
                        
                        if(activeButton == btnVeld) {
                            img = new Image("images/walkable.png");
                        }
                        else if(activeButton == btnMannetje) {
                            img = new Image("images/player.png");
                        }
                        else if(activeButton == btnKist) {
                            img = new Image("images/chest.png");
                        }
                        else if(activeButton == btnDoel) {
                            img = new Image("images/target.png");
                        }
                        
                        ImageView iv = new ImageView(img);
                        iv.setFitWidth(32);
                        iv.setFitHeight(32);
                        
                        if(node.getBoundsInParent().contains(e.getX(),  e.getY())) {
                            gpSpelbord.add(iv, GridPane.getColumnIndex(node), GridPane.getRowIndex(node));
                        }
                    }
                }
            }
        });
    }

    @FXML
    private void btnResetSpelbordOnAction(ActionEvent event) {
        spelbordLaden();
    }

    @FXML
    private void btnAnnuleerOnAction(ActionEvent event) {
        this.hpc.initialiseerKiesActiviteitPaneelController();
    }

    @FXML
    private void btnVeldOnAction(ActionEvent event) {
        activeButton = btnVeld;
    }

    @FXML
    private void btnMannetjeOnAction(ActionEvent event) {
        activeButton = btnMannetje;
    }

    @FXML
    private void btnKistOnAction(ActionEvent event) {
        activeButton = btnKist;
    }

    @FXML
    private void btnDoelOnAction(ActionEvent event) {
        activeButton = btnDoel;
    }
    
    public void setTaalLabels() {
        
    }   
}