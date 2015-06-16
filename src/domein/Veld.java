/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Martin
 */
public class Veld {
    private int x;
    private int y;
    private boolean doel;
    private Mannetje mannetje;
    private Kist kist;
    
    /**
     * Wijst de meegegeven parameters toe aan de corresponderende attributen
     * 
     * @param x x coördinaat
     * @param y y coördinaat
     * @param doel boolean voor doel of niet
     */
    
    public Veld(int x, int y, boolean doel) {
        this.x = x;
        this.y = y;
        this.doel = doel;
    }
    
    /**
     * 
     * @return retourneert x attribuut
     */
    
    public int getX() {
        return x;
    }
    
    /**
     * Wijst de meegegeven parameter toe aan het x attribuut
     * 
     * @param x x coördinaat 
     */
    
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * 
     * @return retourneert y attribuut
     */
    
    public int getY() {
        return y;
    }

    /**
     * Wijst de meegegeven parameter toe aan het x attribuut
     * 
     * @param y y coördinaat 
     */
    
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * 
     * @return retourneert doel attribuut
     */
    
    public boolean getDoel(){
        return doel;
    }
    
    /**
     * 
     * @return is huidig veld object een doel
     */
    
    public boolean isDoel() {
        return doel;
    }
    
    /**
     * Wijst de meegegeven parameter toe aan het doel attribuut
     * 
     * @param doel doel of niet
     */
    
    public void setDoel(boolean doel) {
        this.doel = doel;
    }
    
    /**
     * 
     * @return retourneert mannetje attribuut
     */
    
    public Mannetje getMannetje() {
        return mannetje;
    }
    
    /**
     * Wijst de meegegeven parameter toe aan het mannetje attribuut
     * 
     * @param mannetje mannetje object
     */

    public void setMannetje(Mannetje mannetje) {
        this.mannetje = mannetje;
    }
    
    /**
     * 
     * @return retourneert kist attribuut
     */

    public Kist getKist() {
        return kist;
    }
    
    /**
     * Wijst de meegegeven parameter toe aan het kist attribuut
     * 
     * @param kist mannetje object
     */

    public void setKist(Kist kist) {
        this.kist = kist;
    }
    
}
