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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import resources.Taal;

/**
 * FXML Controller class
 *
 * @author Xander
 */
public class KiesSpelPaneelController extends BorderPane {
    @FXML
    private Label lblKiesSpel;
    @FXML
    private Hyperlink hlExit;
    private Label lblExit;
    @FXML
    private ListView<String> lvSpellen;
    @FXML
    private Label lblKiesSpelHeader;
    @FXML
    private Button btnSpeel;
    
    private DomeinController dc;
    private HoofdPaneelController hpc;
    
    private ObservableList<String> spellen = FXCollections.observableArrayList();
    @FXML
    private Hyperlink hlVorige;
    @FXML
    private Label lblVorige;
    
    

    KiesSpelPaneelController(DomeinController dc, HoofdPaneelController hpc) {
        
        this.dc = dc;
        this.hpc = hpc;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KiesSpelPaneel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        addSpellen();
        setTaalLabels();
        
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
    private void btnSpeelOnAction(ActionEvent event) {
        
        String selectedItem = lvSpellen.getSelectionModel().getSelectedItem();
        if(selectedItem != null && !selectedItem.isEmpty()){
            dc.kiesSpel(selectedItem);
            // roep SpelbordPaneel Op
            hpc.initialiseerSpelbordPaneel();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(Taal.getText("dialoogheader.geenSpel"));
            alert.setHeaderText(null);
            alert.setContentText(Taal.getText("dialoog.geenSpel"));
        }
        
                
    }
    
    
    private void addSpellen(){
        
        String[] spelnamen = dc.geefSpelnamen();
        
        for (String s : spelnamen) {
            spellen.add(s);
        }
        
        lvSpellen.setItems(spellen);
     
    }
    
    private void setTaalLabels() {
        
        lblKiesSpelHeader.setText(Taal.getText("label.kiesSpelHeader"));
        lblKiesSpel.setText(Taal.getText("label.kiesSpel"));
        btnSpeel.setText(Taal.getText("button.speel"));
        hlExit.setText(Taal.getText("exit"));
        if(dc.isAdmin()){
             hlVorige.setText(Taal.getText("hyperlink.naarAdmin"));
             lblVorige.setText(Taal.getText("label.naarAdmin"));
        }
        else {
            hlVorige.setText(Taal.getText("hyperlink.uitloggen"));
            lblVorige.setText(Taal.getText("label.uitloggen"));
        }
        
    }

    @FXML
    private void hlVorigeOnAction(ActionEvent event) {
        if(dc.isAdmin())
        {
            hpc.initialiseerKiesActiviteitPaneelController();
        }
        else{
            dc.setSpeler(null);
            hpc.initialiseerAanmeldPaneelController();
        }
    }
    
}
