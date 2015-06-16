/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import javafx.fxml.FXMLLoader;


/**
 * FXML Controller class
 *
 * @author Ruben
 */
public class TaalPaneelController extends GridPane
{
    @FXML
    private Button btnNederlands;
    @FXML
    private Button btnFrans;
    @FXML
    private Button btnEngels;

    private DomeinController dc;
    private HoofdPaneelController hpc;

    public TaalPaneelController(DomeinController dc, HoofdPaneelController hpc) {
        this.dc = dc;
        this.hpc = hpc;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaalPaneel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnNederlandsOnAction(ActionEvent event) {
        dc.setTaal("nl");
        hpc.initialiseerAanmeldPaneelController();
    }

    @FXML
    private void btnFransOnAction(ActionEvent event) {
        dc.setTaal("fr");
        hpc.initialiseerAanmeldPaneelController();
    }

    @FXML
    private void btnEngelsOnAction(ActionEvent event) {
        dc.setTaal("en");
        hpc.initialiseerAanmeldPaneelController();
    }
    
}
