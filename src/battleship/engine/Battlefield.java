/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;

import battleship.GUI.BattleGUITest;
import battleship.Battleship;
import battleship.Server.TCPServer;
import battleship.Server.KIServer;
import java.awt.*;
import java.util.*;

/**
 *
 * @author Patrik Buholzer
 */
public class Battlefield implements IListener {
    
    private BattleGUITest _bGUI;
    private int _height = 10;
    private int _width = 10;
    private ArrayList<Ship> _fleet = new ArrayList<>();
    private Status _status = new Status();
    
    public Battlefield (BattleGUITest bGUI, int height, int width) {
        _bGUI = bGUI;
        _height = height;
        _width = width;
        _status = new Status();
    }
    
    public boolean enoughSpace(Shiptypes shipTypes,Point clickPosition, boolean horizontal){
        
        boolean validPos=false;
        Point endPosition=new Point();
        if(horizontal){
            endPosition.x=9;
            endPosition.y=clickPosition.y;
            }else{
            endPosition.x=clickPosition.x;
            endPosition.y=9;
            }
            if(clickPosition.distance(endPosition)>=shipTypes.getValue()){
                    validPos=true;
            }
        return validPos;
    }
    
    public void setShip(Shiptypes sTyp){
        _bGUI.removeFromCombobox(sTyp);
    }
    
    public void initializeGame() {
        
    }
    
    /**
     * PB
     * Sets some ships for testing
     */
    public void setShips(ArrayList<Ship> aFleet) {
        this._fleet = aFleet;
    }
    
    //zum testen:
    public void setShips() {
        
        ArrayList<Point> a1 = new ArrayList<>();
        a1.add(new Point(1,1));
        a1.add(new Point(1,2));
        Ship s1 = new Ship(a1, Shiptypes.SUBMARINE);
        _fleet.add(s1);
        
        ArrayList<Point> a2 = new ArrayList<>();
        a2.add(new Point(3,6));
        a2.add(new Point(3,7));
        a2.add(new Point(3,8));
        Ship s2 = new Ship(a2, Shiptypes.DESTROYER);
        _fleet.add(s2);
        
    }
    
    /**
     * PB
     * decides if a shot is a hit or not and sends the answer to the oponent
     * @param Point pt
     * @return int 0 = no hit, 1 = hit, 2 = ship destroyed, 3 = gameover
     */
    public void receiveOponentsShot(Point pt) {
        for (Ship aShip: _fleet) {
            if(aShip.ApplyShot(pt)) {
                if(aShip.IsDestroyed()) {
                    _fleet.remove(aShip);
                    if(_fleet.isEmpty()) {
                        sendReply(true, true, true);
                        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), pt) ,3);
                    } else {
                        sendReply(true, true, false);
                        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), pt) ,2);
                    }
                } else {
                    sendReply(true, false, false);
                    _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), pt) ,1);
                }
            }
        } 
        sendReply(false, false, false);
        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), pt) ,0);
    }
    
    /**
     * receives the Result of the shot done before and makes it visible on the GUI
     * @param pt
     * @param status 
     */
    public void receivePlayersShotResult(Point pt, int status) {
        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelOponent(), pt), status);
    }
    
    /**
     * returns the width of the Battlefield
     * @return 
     */
    public int getWidth() {
        return this._width;
    }
    
    /**
     * returns the height of the Battlefield
     * @return 
     */
    public int getHeight() {
        return this._height;
    }
    
    /**
     * PB
     * receives the shotMessage of the Oponent
     * @param message 
     */
    public void shotMessage(CommunicationObject message) {
        receiveOponentsShot(message.getShot());
    }
    
    /**
     * receives the answer of the shot done before
     * @param message 
     */
    public void replyMessage(CommunicationObject message){
        if (message.getHit()) {
            if (message.getDestroyed()) {
                if (message.getGameover()) {
                    receivePlayersShotResult(message.getShot(), 3);
                } else {
                    receivePlayersShotResult(message.getShot(), 2);
                }                
            } else {
                receivePlayersShotResult(message.getShot(), 1);
            }
        } else {
            receivePlayersShotResult(message.getShot(), 0);
        }
    }
    
    /**
     * PB
     * receives a startmessage after generating the Server
     * @param message 
     */
    public void startMessage(CommunicationObject message){
        
    }
    
    /**
     * PB
     * receives the initialized Message from the server
     * @param message 
     */
    public void initializeMessage(CommunicationObject message){
        initializeGame();
    }
    
    /**
     * sends the shotmessage to the server
     * @param Point pt 
     */
    public void sendPlayerShot(Point pt) {
        CommunicationObject cobj = new CommunicationObject(CommunicationObjectType.SHOT);
        cobj.setShot(pt);
        //send cobj
    }
    
    /**
     * sends the resultmessage to the server
     * @param boolean isHit, boolean isDestroyed, boolean isGameover
     */
    public void sendReply(boolean isHit, boolean isDestroyed, boolean isGameover) {
        CommunicationObject cobj = new CommunicationObject(CommunicationObjectType.REPLY);
        cobj.shotAplyed(isHit, isDestroyed, isGameover);
        //send cobj
    }
    
}
