/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;

/**
 *
 * @author Patrik
 */
public class Status {
    private boolean _aktiv;
    
    public Status() {
        _aktiv = false;
    }
    
    public boolean getStatus(){
        return this._aktiv;
    }
    
    public void setStatus(boolean aktiv) {
        this._aktiv = aktiv;
    }
    
}
