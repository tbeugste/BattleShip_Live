/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;

import battleship.GUI.BattleGUITest;
import battleship.CommunicationObject;
import battleship.CommunicationObjectType;
import battleship.IListener;
import battleship.Battleship;
import java.awt.*;
import java.util.*;

/**
 *
 * @author Patrik Buholzer
 */
public class Battlefield extends Battleship implements IListener {
    
    private int height;
    private int width;
    private ArrayList<Ship> fleet = new ArrayList<>();
    private BattleGUITest bGUI = super.bGUI;
    
    public Battlefield (int height, int width) {
        this.height = height;
        this.width = width;
        this.bGUI = bGUI;
        setShips();
    }
    
    /**
     * PB
     * Sets some ships for testing
     */
    public void setShips() {
        ArrayList<Point> a1 = new ArrayList<>();
        a1.add(new Point(1,1));
        a1.add(new Point(1,2));
        Ship s1 = new Ship(a1, Shiptypes.SUBMARINE);
        fleet.add(s1);
        
        ArrayList<Point> a2 = new ArrayList<>();
        a2.add(new Point(3,6));
        a2.add(new Point(3,7));
        a2.add(new Point(3,8));
        Ship s2 = new Ship(a2, Shiptypes.DESTROYER);
        fleet.add(s2);
    }
    
    /**
     * PB
     * decides if a shot is a hit or not
     * @param p
     * @return int 0 = no hit, 1 = hit, 2 = ship destroyed, 3 = gameover
     */
    public int receivedShot(Point p) {
        
        for (Ship aShip: fleet) {
            if(aShip.ApplyShot(p)) {
                if(aShip.IsDestroyed()) {
                    fleet.remove(aShip);
                    if(fleet.isEmpty()) {
                        sendReply(true, true);
                        return 3;
                    } else {
                        sendReply(true, true);
                        return 2;
                    }
                } else {
                    sendReply(true, false);
                    return 1;
                }
            }
        } 
        sendReply(false, false);
        return 0;
        
        // Meldung über Netzwerk senden.
    }
    
    public void shot(Point pt) {
        sendShot(pt);
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void shotMessage(CommunicationObject message) {
        receivedShot(message.getShot());
        bGUI.receivedShot(message.getShot());
    }
    
    public void replyMessage(CommunicationObject message){
        if (message.getHit()) {
            if (message.getDestroyed()) {
                
            }
        }
    }
    
    public void startMessage(CommunicationObject message){
        
    }
    
    public void initializeMessage(CommunicationObject message){
        
    }
    
    public void sendShot(Point pt) {
        CommunicationObject cobj = new CommunicationObject(CommunicationObjectType.SHOT);
        cobj.setShot(pt);
        //send cobj
    }
    
    public void sendReply(boolean isHit, boolean isDestroyed) {
        CommunicationObject cobj = new CommunicationObject(CommunicationObjectType.REPLY);
        cobj.shotAplyed(isHit, isDestroyed);
        //send cobj
    }
    
}
