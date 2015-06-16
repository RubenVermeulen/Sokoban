/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import resources.Taal;

/**
 * FXML Controller class
 *
 * @author Ruben
 */
public class AanmeldPaneelController extends BorderPane
{
    @FXML
    private Label lblInloggen;
    @FXML
    private Button btnInloggen;
    @FXML
    private Label lblGebruikersnaam;
    @FXML
    private Label lblWachtwoord;
    @FXML
    private TextField txfGebruikersnaam;
    @FXML
    private PasswordField pfWachtwoord;
    @FXML
    private Hyperlink hlRegistreer;
    @FXML
    private Label lblAccount;

    private DomeinController dc;
    private HoofdPaneelController hpc;
    @FXML
    private Hyperlink hlExit;
    @FXML
    private Button btnSlot;
    @FXML
    private TextField txfWachtwoord;

    public AanmeldPaneelController(DomeinController dc, HoofdPaneelController hpc) {
        this.dc = dc;
        this.hpc = hpc;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AanmeldPaneel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        setTaalLabels();
        
        txfWachtwoord.setVisible(false);
        
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    meldAan();
                }
            }
        });        
    }

    @FXML
    private void btnInloggenOnAction(ActionEvent event) {
        meldAan();
        
    }
    
    private void meldAan() {
        try {
            String gebruikersnaam = txfGebruikersnaam.getText().trim();
            String wachtwoord = pfWachtwoord.getText().trim();

            dc.meldAan(gebruikersnaam, wachtwoord);

            if (!dc.isAangemeld()) {
                throw new IllegalArgumentException(Taal.getText("error_aanmelden"));
            }
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(Taal.getText("succesTitel"));
            alert.setContentText(Taal.getText("ingelogd"));
            alert.showAndWait();
            
            if(dc.isAdmin()) {
                hpc.initialiseerKiesActiviteitPaneelController();
            }
            else {
                hpc.initialiseerKiesSpelPaneelController();
            }
        } catch (Exception e) {
            pfWachtwoord.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Taal.getText("errorTitel"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    private void hlRegistreerOnAction(ActionEvent event) {
        hpc.initialiseerRegistratiePaneelController();
    }
   
    private void setTaalLabels() {
        String dubbelpunt = ":";
        
        lblInloggen.setText(Taal.getText("login"));
        btnInloggen.setText(Taal.getText("login"));
        lblGebruikersnaam.setText(Taal.getText("gebruikersnaam") + dubbelpunt);
        lblWachtwoord.setText(Taal.getText("wachtwoord") + dubbelpunt);
        lblAccount.setText(Taal.getText("label.account"));
        hlRegistreer.setText(Taal.getText("registreer") + "!");
        hlExit.setText(Taal.getText("exit"));
    }

    @FXML
    private void hlExitOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Taal.getText("exitdialoog"));
        alert.setHeaderText(Taal.getText("exitdialoog"));
        alert.setContentText(Taal.getText("exitbevestiging"));
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Platform.exit();
        }
        else {
            
        }
    }

    @FXML
    private void btnSlotOnAction(MouseEvent event) {
        
        txfWachtwoord.setText(pfWachtwoord.getText());
        txfWachtwoord.setVisible(true);
        pfWachtwoord.setVisible(false);
        btnSlot.setText("🔓");
        
    }

    @FXML
    private void btnSlotOnRelease(MouseEvent event) {
        
        pfWachtwoord.setText(txfWachtwoord.getText());
        txfWachtwoord.setVisible(false);
        pfWachtwoord.setVisible(true);
        btnSlot.setText("🔒");
        
    }
    
}
