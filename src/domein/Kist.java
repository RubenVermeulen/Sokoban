/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Ruben
 */
public class Kist {
    private Veld veld;
    
    /**
     * Wijst de meegegeven parameter toe aan het veld attribuut
     * 
     * @param veld veld object
     */
    
    public Kist(Veld veld) {
        this.veld = veld;
    }
    
    
    /**
     * @return Geeft het veld attribuut terug 
     */
    
    public Veld getVeld() {
        return veld;
    }

    /**
     * Wijst de meegegeven parameter toe aan het veld attribuut
     * 
     * @param veld veld object
     */
    
    public void setVeld(Veld veld) {
        this.veld = veld;
    }

}
