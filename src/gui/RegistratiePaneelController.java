/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
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
public class RegistratiePaneelController extends BorderPane
{
    @FXML
    private Label lblRegistreren;
    @FXML
    private Button btnRegistreren;
    @FXML
    private Label lblVoornaam;
    @FXML
    private Label lblAchternaam;
    @FXML
    private Label lblGebruikersnaam;
    @FXML
    private Label lblWachtwoord;
    @FXML
    private Label lblHerhaalWachtwoord;
    @FXML
    private TextField txfVoornaam;
    @FXML
    private TextField txfAchternaam;
    @FXML
    private TextField txfGebruikersnaam;
    @FXML
    private TextField txfWachtwoord;
    @FXML
    private TextField txfHerhaalWachtwoord;

    private DomeinController dc;
    private HoofdPaneelController hpc;
    @FXML
    private PasswordField pfWachtwoord;
    @FXML
    private PasswordField pfHerhaalWachtwoord;
    @FXML
    private Hyperlink hlLogin;
    @FXML
    private Label lblAccount;
    @FXML
    private Hyperlink hlExit;
    @FXML
    private Button btnSlot;
    @FXML
    private Button btnSlot2;

    public RegistratiePaneelController(DomeinController dc, HoofdPaneelController hpc) {
        this.dc = dc;
        this.hpc = hpc;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistratiePaneel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        btnSlot2.setText("🔒");
        
        
        
        setTaalLabels();
        
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
            {
                registreren();
            }
            }
        });
        
    }

    @FXML
    private void btnRegistrerenOnAction(ActionEvent event) {
        registreren();
        
    }
    
    private void registreren() {
        try {
            String achternaam = txfAchternaam.getText().trim();
            String voornaam = txfVoornaam.getText().trim();
            String gebruikersnaam = txfGebruikersnaam.getText().trim();
            String wachtwoord = txfWachtwoord.getText().trim();
            String herhaalWachtwoord = txfHerhaalWachtwoord.getText().trim();

            if(!wachtwoord.equals(herhaalWachtwoord)) {
                throw new IllegalArgumentException(Taal.getText("error_overeenkomst"));
            }

            dc.registreer(
                    gebruikersnaam,
                    wachtwoord,
                    achternaam,
                    voornaam
            );
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(Taal.getText("succesTitel"));
            alert.setHeaderText("Information");
            alert.setContentText(Taal.getText("geregistreerd"));
            alert.showAndWait();
            
            if(dc.isAdmin()) {
                hpc.initialiseerKiesActiviteitPaneelController();
            }
            else {
                hpc.initialiseerKiesSpelPaneelController();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Taal.getText("errorTitel"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        
    }
    
    private void setTaalLabels() {
        String dubbelpunt = ":";
        
        lblRegistreren.setText(Taal.getText("registreer"));
        lblVoornaam.setText(Taal.getText("voornaam") + dubbelpunt);
        lblAchternaam.setText(Taal.getText("achternaam") + dubbelpunt);
        lblGebruikersnaam.setText(Taal.getText("gebruikersnaam") + dubbelpunt);
        lblWachtwoord.setText(Taal.getText("wachtwoord") + dubbelpunt);
        lblHerhaalWachtwoord.setText(Taal.getText("bevestig_pass") + dubbelpunt);
        btnRegistreren.setText(Taal.getText("registreer"));
        lblAccount.setText(Taal.getText("label.loginAccount"));
        hlLogin.setText(Taal.getText("hyperlink.login"));
        hlExit.setText(Taal.getText("exit"));
    }

    @FXML
    private void hlLoginOnAction(ActionEvent event) {
        hpc.initialiseerAanmeldPaneelController();
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

    @FXML
    private void btnSlot2OnAction(MouseEvent event) {
        
        txfHerhaalWachtwoord.setText(pfHerhaalWachtwoord.getText());
        txfHerhaalWachtwoord.setVisible(true);
        pfHerhaalWachtwoord.setVisible(false);
        btnSlot2.setText("🔓");
        
    }

    @FXML
    private void btnSlot2OnRelease(MouseEvent event) {
        
        pfHerhaalWachtwoord.setText(txfHerhaalWachtwoord.getText());
        txfHerhaalWachtwoord.setVisible(false);
        pfHerhaalWachtwoord.setVisible(true);
        btnSlot2.setText("🔒");
        
    }
    
}
