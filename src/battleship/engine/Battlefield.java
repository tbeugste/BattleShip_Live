/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;

import battleship.GUI.BattleGUI;
import battleship.GUI.MyButton;
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
    public Status status;
    public Shiptypes placedShiptype;
    public Ship placedShip = new Ship(null, null);
    public boolean horizontal = true;
    public MyButton[][] buttonArray;
    
    private TCPClient _client;
    private TCPServer _server;
    private KIServer _kiServer;
    public String serverIP;
    
    public Battlefield (BattleGUI bGUI, int height, int width) {
        _bGUI = bGUI;
        _height = height;
        _width = width;
        status = new Status();
        buttonArray = new MyButton[height][width];
    }
    
    /**
     * 
     * @param shipTypes
     * @param clickPosition
     * @param horizontal
     * @return 
     */
    public boolean enoughSpace(Point clickPosition){
        
        boolean validPos=false;
        Point endPosition=new Point();
        if(this.horizontal){
            endPosition.x=9;
            endPosition.y=clickPosition.y;
            }else{
            endPosition.x=clickPosition.x;
            endPosition.y=9;
            }
            if(clickPosition.distance(endPosition) > (placedShiptype.getValue()-2)){
                //check if a ship ist to close:    
                if(!shipToClose(clickPosition)){
                    validPos=true;
                }
            }
        return validPos;
    }
    
    /**
     * checks if a Ship in the fleet is to close to a point
     * @param point
     * @return 
     */
    public boolean shipToClose(Point point) {
        boolean result = false;

        for(Ship aShip : _fleet) {
            for(Point aPoint : aShip.getCoordinates()) {
                for(Point bPoint : createPlacedShip(point).getCoordinates()) {
                    if(aPoint.distance(bPoint) <= 1) {
                        result = true;
                    }
                }
            }
        }

        return result;
    }
         
    /**
     * creates a ship of a point and the selected shiptype in the GUI
     * @param point
     * @return 
     */
    public Ship createPlacedShip(Point point) {
        ArrayList<Point> al = new ArrayList<>();
        if(horizontal) {
            for(int i = point.x; i < (point.x + placedShiptype.getValue()); i++) {
                Point p = new Point(i, point.y);
                al.add(p);
            }
        } else {
            for(int i = point.y; i < (point.y + placedShiptype.getValue()); i++) {
                Point p = new Point(point.x, i);
                al.add(p);
            }
        }
        Ship ship = new Ship(al, placedShiptype);
        return ship;
    }
    
    public void joinGame() {
        
    }
    
    public void setShip(Shiptypes sTyp){
        _bGUI.removeFromCombobox(sTyp);
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
                _client.addActionListener(this);
                new Thread(_client);
                break;
            // Multiplayer Host
            case 2:
                _server = new TCPServer();
                _client = new TCPClient("127.0.0.1", 9999);
                _client.addActionListener(this);
                new Thread(_client);
                break;
            // Multiplayer Client
            case 3:
                _client = new TCPClient(serverIP, 9999);
                break;
        }
    }
    
    /**
     * PB
     * adds a ship to the fleet
     */
    public void addShip(Ship aShip) {
        this._fleet.add(aShip);
    }
    
//    //zum testen:
//    public void setShips() {
//        
//        ArrayList<Point> a1 = new ArrayList<>();
//        a1.add(new Point(1,1));
//        a1.add(new Point(1,2));
//        Ship s1 = new Ship(a1, Shiptypes.SUBMARINE);
//        _fleet.add(s1);
//        
//        ArrayList<Point> a2 = new ArrayList<>();
//        a2.add(new Point(3,6));
//        a2.add(new Point(3,7));
//        a2.add(new Point(3,8));
//        Ship s2 = new Ship(a2, Shiptypes.DESTROYER);
//        _fleet.add(s2);
//        
//    }
    
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
        if(message.getHit())
        {
            //Add Method that we can start to apply a shot
        }
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
       //Method to start the game and let the user do a shot 
    }
    
    /**
     * PB
     * receives the initialized Message from the server
     * @param message 
     */
    public void initializeMessage(CommunicationObject message){
        //_bGUI.createWindow();
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
     * Method to check all placed Ships and send message to server
     * @return 
     */
    public String getReadyResponse ()
    {
       String response ="";
       int bShipCount = 0;
       int cruiserCount = 0;
       int destroyerCount = 0;
       int submarineCount = 0;
       
       for(Ship ship: _fleet)
       {
           switch(ship.GetShipType())
           {
               case BSHIP:
                   bShipCount++;
                   break;
               case CRUISER:
                   cruiserCount++;
                   break;
               case DESTROYER:
                   destroyerCount++;
                   break;
               case SUBMARINE:
                   submarineCount++;
                   break;
               default:
                   break;
           }
       }
       
       if(Shiptypes.BSHIP.getCount()!=bShipCount)
       {
           response += "Es müssen noch "+String.valueOf(Shiptypes.BSHIP.getCount()-bShipCount)+" Schiffe vom Typ "+Shiptypes.BSHIP.name()+" gesetzt werden.<br>";
       }
       
       if(Shiptypes.CRUISER.getCount()!=cruiserCount)
       {
           response += "Es müssen noch "+String.valueOf(Shiptypes.CRUISER.getCount()-cruiserCount)+" Schiffe vom Typ "+Shiptypes.CRUISER.name()+" gesetzt werden.<br>";
       }
       
       if(Shiptypes.DESTROYER.getCount()!=destroyerCount)
       {
           response += "Es müssen noch "+String.valueOf(Shiptypes.DESTROYER.getCount()-destroyerCount)+" Schiffe vom Typ "+Shiptypes.DESTROYER.name()+" gesetzt werden.<br>";
       }
       
       if(Shiptypes.SUBMARINE.getCount()!=submarineCount)
       {
           response += "Es müssen noch "+String.valueOf(Shiptypes.SUBMARINE.getCount()-submarineCount)+" Schiffe vom Typ "+Shiptypes.SUBMARINE.name()+" gesetzt werden.<br>";
       }
       
       if(response.isEmpty())
       {
           CommunicationObject message = new CommunicationObject(CommunicationObjectType.INITIALIZE);
           message.setInitialized(true);
           _client.sendToServer(message);
           response ="Warte auf antwort des Servers.";
       }
       
       
       response = "<html>" + response +"</html>";
       return response;
    }
    
}
