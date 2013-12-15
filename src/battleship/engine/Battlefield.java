/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;

import battleship.GUI.BattleGUI;
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
    
    private BattleGUI _bGUI;
    private int _height = 10;
    private int _width = 10;
    private ArrayList<Ship> _fleet = new ArrayList<>();
    private Status _status;
    
    private TCPClient _client;
    private TCPServer _server;
    private KIServer _kiServer;
    
    public Battlefield (BattleGUI bGUI, int height, int width, int gametype) {
        _bGUI = bGUI;
        _height = height;
        _width = width;
        _status = new Status();
        initializeServer(gametype);
    }
    
    /**
     * 
     * @param shipTypes
     * @param clickPosition
     * @param horizontal
     * @return 
     */
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
     * this method initialize the server and client
     * @param gametype 
     */
    public void initializeServer(int gametype) {
        switch (gametype) {
            // Singleplayer
            case 1:
                _kiServer = new KIServer();
                _client = new TCPClient("127.0.0.1", 9999);
                break;
            // Multiplayer Host
            case 2:
                _server = new TCPServer();
                _client = new TCPClient("127.0.0.1", 9999);
                break;
            // Multiplayer Client
            case 3:
                _client = new TCPClient("127.0.0.1", 9999);
                break;
        }
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
    public void receiveOponentsShot(CommunicationObject message) {
        for (Ship aShip: _fleet) {
            if(aShip.ApplyShot(message.getShot())) {
                if(aShip.IsDestroyed()) {
                    _fleet.remove(aShip);
                    if(_fleet.isEmpty()) {
                        message.shotAplyed(true, true, true);
                        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), message.getShot()) ,3);
                    } else {
                        message.shotAplyed(true, true, false);
                        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), message.getShot()) ,2);
                    }
                } else {
                    message.shotAplyed(true, false, false);
                    _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), message.getShot()) ,1);
                }
            }
        } 
        message.shotAplyed(false, false, false);
        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), message.getShot()) ,0);
        sendReply(message);
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
        receiveOponentsShot(message);
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
        _client.sendToServer(cobj);
    }
    
    /**
     * sends the resultmessage to the server
     * @param boolean isHit, boolean isDestroyed, boolean isGameover
     */
    public void sendReply(CommunicationObject cobj) {
        _client.sendToServer(cobj);
    }
    
}
