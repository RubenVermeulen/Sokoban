/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import resources.Taal;

/**
 * FXML Controller class
 *
 * @author Xander
 */
public class KiesActiviteitPaneelController extends BorderPane {
    @FXML
    private Label lblAdministratiemenu;
    @FXML
    private Label lblWelkom;
    @FXML
    private Button lblSpeelSpel;
    @FXML
    private Button lblMaakNieuwSpelAan;
    @FXML
    private Button lblWijzigSpel;
    @FXML
    private Hyperlink hlExit;


    @FXML
    private Label lblNieuwSpelNaam;
    @FXML
    private TextField txfNieuwSpelNaam;
    @FXML
    private GridPane gpMain;
    @FXML
    private Button btnConfirmMaakSpel;
    @FXML
    private Button btnCancelMaakSpel;
    
    private DomeinController dc;
    private HoofdPaneelController hpc;
    @FXML
    private Hyperlink hlUitloggen;
    @FXML
    private Label lblAccountWisselen;
    
    public KiesActiviteitPaneelController(DomeinController dc, HoofdPaneelController hpc){
        this.dc = dc;
        this.hpc = hpc;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KiesActiviteitPaneel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        setTaalLabels();
        
        lblNieuwSpelNaam.setVisible(false);
        txfNieuwSpelNaam.setVisible(false);
        btnCancelMaakSpel.setVisible(false);
        btnConfirmMaakSpel.setVisible(false);
        
    }
    

    @FXML
    private void lblSpeelSpelOnAction(ActionEvent event) {
        hpc.initialiseerKiesSpelPaneelController();
    }

    @FXML
    private void lblMaakNieuwSpelAanOnAction(ActionEvent event) {
        
        
        // FADE IN VAN NIEUW SPEL KIEZEN
        lblNieuwSpelNaam.setVisible(true);
        txfNieuwSpelNaam.setVisible(true);
        btnCancelMaakSpel.setVisible(true);
        btnConfirmMaakSpel.setVisible(true);
        
        disableButtons();
        
        FadeTransition lblFade = new FadeTransition(Duration.millis(1000), lblNieuwSpelNaam);
        lblFade.setFromValue(0);
        lblFade.setToValue(1.0);
        
        FadeTransition txfFade = new FadeTransition(Duration.millis(1000), txfNieuwSpelNaam);
        txfFade.setFromValue(0);
        txfFade.setToValue(1.0);
        
        FadeTransition btnJaFade = new FadeTransition(Duration.millis(1000), btnConfirmMaakSpel);
        btnJaFade.setFromValue(0);
        btnJaFade.setToValue(1.0);
        
        FadeTransition btnNeeFade = new FadeTransition(Duration.millis(1000), btnCancelMaakSpel);
        btnNeeFade.setFromValue(0);
        btnNeeFade.setToValue(1.0);
        
        TranslateTransition lblTrans = new TranslateTransition(Duration.millis(1000), lblNieuwSpelNaam);
        lblTrans.setFromY(0);
        lblTrans.setToY(-115);
        
        TranslateTransition txfTrans = new TranslateTransition(Duration.millis(1000), txfNieuwSpelNaam);
        txfTrans.setFromY(0);
        txfTrans.setToY(-115);
        
        TranslateTransition btnJaTrans = new TranslateTransition(Duration.millis(1000), btnConfirmMaakSpel);
        btnJaTrans.setFromY(0);
        btnJaTrans.setToY(-115);
        
        TranslateTransition btnNeeTrans = new TranslateTransition(Duration.millis(1000), btnCancelMaakSpel);
        btnNeeTrans.setFromY(0);
        btnNeeTrans.setToY(-115);
        
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                lblFade,
                txfFade,
                btnJaFade,
                btnNeeFade,
                lblTrans,
                txfTrans,
                btnJaTrans,
                btnNeeTrans
        );
        
            // hier zetten we een kleine delay op, zodat de fade in pas begint
            // als de fade out al bijna klaar is
        
        parallelTransition.setDelay(Duration.millis(350));
        
        
        // FADE OUT VAN ACTIVITEIT KIEZEN
        
        FadeTransition btn1Fade = new FadeTransition(Duration.millis(1000), lblSpeelSpel);
        btn1Fade.setFromValue(1.0);
        btn1Fade.setToValue(0);
        
        FadeTransition btn2Fade = new FadeTransition(Duration.millis(1000), lblWijzigSpel);
        btn2Fade.setFromValue(1.0);
        btn2Fade.setToValue(0);
        
        FadeTransition btn3Fade = new FadeTransition(Duration.millis(1000), lblMaakNieuwSpelAan);
        btn3Fade.setFromValue(1.0);
        btn3Fade.setToValue(0);
        
        FadeTransition WelkomFade = new FadeTransition(Duration.millis(1000), lblWelkom);
        WelkomFade.setFromValue(1.0);
        WelkomFade.setToValue(0);
        
        ParallelTransition parallelTransitionOut = new ParallelTransition();
        parallelTransitionOut.getChildren().addAll(
                btn1Fade, btn2Fade, btn3Fade, WelkomFade
        );
        
        
        // SPEEL BEIDE ANIMATIES NA ELKAAR AF
        
        ParallelTransition sequentialTrans = new ParallelTransition();
        sequentialTrans.getChildren().addAll(
                parallelTransitionOut,
                parallelTransition
        );
        
        // visibility uit
        // indien we niet doen kan de gebruiker nog steeds op de knoppen drukken
        // ondanks dat ze onzichtbaar zijn
        
        sequentialTrans.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                lblSpeelSpel.setVisible(false);
                lblMaakNieuwSpelAan.setVisible(false);
                lblWijzigSpel.setVisible(false);
                lblWelkom.setVisible(false);
                
                enableButtons();
            }
        });
        
        
        // PLAY
        
        sequentialTrans.play();
        
            
    }
    
    private void disableButtons(){
        btnCancelMaakSpel.setDisable(true);
        btnConfirmMaakSpel.setDisable(true);
        lblMaakNieuwSpelAan.setDisable(true);
        lblWijzigSpel.setDisable(true);
        lblSpeelSpel.setDisable(true);
        hlExit.setDisable(true);
        hlExit.setOpacity(1.0);
    }
    
    private void enableButtons(){
        btnCancelMaakSpel.setDisable(false);
        btnConfirmMaakSpel.setDisable(false);
        lblMaakNieuwSpelAan.setDisable(false);
        lblWijzigSpel.setDisable(false);
        lblSpeelSpel.setDisable(false);
        hlExit.setDisable(false);
    }

    @FXML
    private void lblWijzigSpelOnAction(ActionEvent event) {
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
    private void btnConfirmMaakSpelOnAction(ActionEvent event) {
        dc.configureerNieuwSpel(txfNieuwSpelNaam.getText());
        hpc.initialiseerNieuwSpelPaneelController();
    }

    @FXML
    private void btnCancelMaakSpelOnAction(ActionEvent event) {
        
        disableButtons();
        
        // FADE OUT VAN NIEUW SPEL KIEZEN
        
        FadeTransition lblFade = new FadeTransition(Duration.millis(1000), lblNieuwSpelNaam);
        lblFade.setFromValue(1.0);
        lblFade.setToValue(0);
        
        FadeTransition txfFade = new FadeTransition(Duration.millis(1000), txfNieuwSpelNaam);
        txfFade.setFromValue(1.0);
        txfFade.setToValue(0);
        
        FadeTransition btnJaFade = new FadeTransition(Duration.millis(1000), btnConfirmMaakSpel);
        btnJaFade.setFromValue(1.0);
        btnJaFade.setToValue(0);
        
        FadeTransition btnNeeFade = new FadeTransition(Duration.millis(1000), btnCancelMaakSpel);
        btnNeeFade.setFromValue(1.0);
        btnNeeFade.setToValue(0);
        
        TranslateTransition lblTrans = new TranslateTransition(Duration.millis(1000), lblNieuwSpelNaam);
        lblTrans.setFromY(-115);
        lblTrans.setToY(0);
        
        TranslateTransition txfTrans = new TranslateTransition(Duration.millis(1000), txfNieuwSpelNaam);
        txfTrans.setFromY(-115);
        txfTrans.setToY(0);
        
        TranslateTransition btnJaTrans = new TranslateTransition(Duration.millis(1000), btnConfirmMaakSpel);
        btnJaTrans.setFromY(-115);
        btnJaTrans.setToY(-0);
        
        TranslateTransition btnNeeTrans = new TranslateTransition(Duration.millis(1000), btnCancelMaakSpel);
        btnNeeTrans.setFromY(-115);
        btnNeeTrans.setToY(-0);
        
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                lblFade,
                txfFade,
                btnJaFade,
                btnNeeFade,
                lblTrans,
                txfTrans,
                btnJaTrans,
                btnNeeTrans
        );
        
        
        
        // FADE IN VAN ACTIVITEIT KIEZEN
        
        lblSpeelSpel.setVisible(true);
        lblMaakNieuwSpelAan.setVisible(true);
        lblWijzigSpel.setVisible(true);
        lblWelkom.setVisible(true);
        
        FadeTransition btn1Fade = new FadeTransition(Duration.millis(1000), lblSpeelSpel);
        btn1Fade.setFromValue(0);
        btn1Fade.setToValue(1.0);
        
        FadeTransition btn2Fade = new FadeTransition(Duration.millis(1000), lblWijzigSpel);
        btn2Fade.setFromValue(0);
        btn2Fade.setToValue(1.0);
        
        FadeTransition btn3Fade = new FadeTransition(Duration.millis(1000), lblMaakNieuwSpelAan);
        btn3Fade.setFromValue(0);
        btn3Fade.setToValue(1.0);
        
        FadeTransition WelkomFade = new FadeTransition(Duration.millis(1000), lblWelkom);
        WelkomFade.setFromValue(0);
        WelkomFade.setToValue(1.0);
        
        ParallelTransition parallelTransitionIn = new ParallelTransition();
        parallelTransitionIn.getChildren().addAll(
                btn1Fade, btn2Fade, btn3Fade, WelkomFade
        );
        
        // hier zetten we een kleine delay op, zodat de fade in pas begint
            // als de fade out al bijna klaar is
        
        parallelTransitionIn.setDelay(Duration.millis(350));
        
        // SPEEL BEIDE ANIMATIES NA ELKAAR AF
        
        ParallelTransition sequentialTrans = new ParallelTransition();
        sequentialTrans.getChildren().addAll(
                parallelTransitionIn,
                parallelTransition
        );
        
        // visibility uit
        // indien we niet doen kan de gebruiker nog steeds op de knoppen drukken
        // ondanks dat ze onzichtbaar zijn
        
        sequentialTrans.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                lblNieuwSpelNaam.setVisible(false);
                txfNieuwSpelNaam.setVisible(false);
                btnCancelMaakSpel.setVisible(false);
                btnConfirmMaakSpel.setVisible(false);
                
                enableButtons();
            }
        });
        
        
        // PLAY
        
        sequentialTrans.play();
    }
    
    
    private void setTaalLabels() {
        
        String welkom = String.format(Taal.getText("welkomadministratie"), dc.getSpeler().getGebruikersnaam());
        
        lblAdministratiemenu.setText(Taal.getText("administratiemenu"));
        lblWelkom.setText(welkom);
        lblSpeelSpel.setText(Taal.getText("speel_spel"));
        lblMaakNieuwSpelAan.setText(Taal.getText("nieuw_spel"));
        lblWijzigSpel.setText(Taal.getText("wijzig_spel"));
        hlExit.setText(Taal.getText("exit"));
        lblNieuwSpelNaam.setText(Taal.getText("label.nieuwSpelNaam"));
        btnConfirmMaakSpel.setText(Taal.getText("button.maakSpel"));
        btnCancelMaakSpel.setText(Taal.getText("annuleer"));
        lblAccountWisselen.setText(Taal.getText("label.uitloggen"));
        hlUitloggen.setText(Taal.getText("hyperlink.uitloggen"));
        
    }

    @FXML
    private void hlUitloggenOnAction(ActionEvent event) {
        dc.setSpeler(null);
        hpc.initialiseerAanmeldPaneelController();
    }

}
