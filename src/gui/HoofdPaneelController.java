/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 * FXML Controller class
 *
 * @author Ruben
 */
public class HoofdPaneelController extends BorderPane
{
    private DomeinController dc;
    private TaalPaneelController tpc;
    private AanmeldPaneelController apc;
    private RegistratiePaneelController rpc;
    private KiesActiviteitPaneelController kapc;
    private KiesSpelPaneelController kspc;
    private SpelbordPaneelController spc;
    private NieuwSpelPaneelController nspc;

    public HoofdPaneelController(DomeinController dc) {
        this.dc = dc;
        this.tpc = new TaalPaneelController(this.dc, this);
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HoofdPaneel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        initialiseerTaalPaneelController();
    }
    
    private void initialiseerTaalPaneelController() {
        setCenter(tpc);
    }
    
    protected void initialiseerAanmeldPaneelController() {
        this.apc = new AanmeldPaneelController(this.dc, this);
        
        setCenter(apc);
    }
    
    protected void initialiseerRegistratiePaneelController() {
        this.rpc = new RegistratiePaneelController(this.dc, this);
        
        setCenter(rpc);
    }
    
    protected void initialiseerKiesActiviteitPaneelController(){
        
        this.kapc = new KiesActiviteitPaneelController(this.dc, this); 
             
        setCenter(kapc);
    }
    
    protected void initialiseerKiesSpelPaneelController(){
        this.kspc = new KiesSpelPaneelController(this.dc, this); 
        
        setCenter(kspc);
    }
    
    protected void initialiseerSpelbordPaneel() {
        this.spc = new SpelbordPaneelController(this.dc, this);
        
        setCenter(spc);
    }
    
    protected void initialiseerNieuwSpelPaneelController() {
        this.nspc = new NieuwSpelPaneelController(this.dc, this);
        
        setCenter(nspc);
    }
}
