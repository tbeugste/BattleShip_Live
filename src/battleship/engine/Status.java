/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;

/**
 * controls the playerstatus
 * @author Patrik Buholzer
 */
public class Status {
    private boolean _active;
    private boolean _shipPlacementactive;
    
    public Status() {
        _active = false;
        _shipPlacementactive = false;
    }
    
    public boolean getStatus(){
        return this._active;
    }
    
    public void setStatus(boolean active) {
        this._active = active;
    }
    
    public boolean getShipPlacementactive() {
        return this._shipPlacementactive;
    }
    
    public void setShipPlacementactive(boolean shipPlacementactive) {
        this._shipPlacementactive = shipPlacementactive;
    }
    
}
