/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;

import battleship.GUI.BattleGUI;
import battleship.GUI.MyButton;
import battleship.Server.TCPServer;
import battleship.Server.KIServer;
import static battleship.engine.Shiptypes.CRUISER;
import static battleship.engine.Shiptypes.DESTROYER;
import static battleship.engine.Shiptypes.SUBMARINE;
import javax.swing.JButton;
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
    public JButton[][] guiButtons;
    
    private TCPClient _client;
    private TCPServer _server;
    private KIServer _kiServer;
    private String serverIP;
    
    public Battlefield (int height, int width) {
        _bGUI = new BattleGUI();
        _height = height;
        _width = width;
        status = new Status();
        buttonArray = new MyButton[height][width];
        guiButtons = new JButton[3][2];
    }
    
    /**
     * checks if there is enough space between the clickposition and the border or any ship already placed
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
                break;
            // Multiplayer Host
            case 2:
                _server = new TCPServer();
                _client = new TCPClient("127.0.0.1", 9999);
                _client.addActionListener(this);
                break;
            // Multiplayer Client
            case 3:
                _client = new TCPClient(serverIP, 9999);
                _client.addActionListener(this);
                break;
        }
        _bGUI.setVisible(true);
    }
    
    /**
     * PB
     * adds a ship to the fleet
     */
    public void addShip(Ship aShip) {
        this._fleet.add(aShip);
    }

    /**
     * PB
     * decides if a shot is a hit or not and sends the answer to the oponent
     * @param Point pt
     * @return int 0 = no hit, 1 = hit, 2 = ship destroyed, 3 = gameover
     */
    public void receiveOponentsShot(CommunicationObject message) {
        boolean hit = false;
        for (Ship aShip: _fleet) {
            if(aShip.ApplyShot(message.getShot())) {
                if(aShip.IsDestroyed()) {
                    _fleet.remove(aShip);
                    if(_fleet.isEmpty()) {
                        message.shotAplyed(true, true, true);
                        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), message.getShot()) ,3,false);
                        hit = true;
                        break;
                    } else {
                        message.shotAplyed(true, true, false);
                        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), message.getShot()) ,2,false);
                        hit = true;
                        break;
                    }
                } else {
                    message.shotAplyed(true, false, false);
                    _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), message.getShot()) ,1,false);
                    hit = true;
                    break;
                }
            }
        } 
        if(!hit)
        {
            message.shotAplyed(false, false, false);
            _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelPlayer(), message.getShot()) ,0,false);
        }
        sendReply(message);
        if(!hit)
        {
            status.setStatus(true);
            _bGUI.setLabel("Dein Zug!");
            _bGUI.activatePanel(_bGUI.getPanelOponent());
        }
        else if(message.getGameover())
        {
            status.setStatus(false);
            _bGUI.setLabel("GAME OVER! YOU HAVE LOST!!!");
            _bGUI.deactivatePanel(_bGUI.getPanelOponent());
            _bGUI.deactivatePanel(_bGUI.getPanelPlayer());
        }
    }
    
    /**
     * receives the Result of the shot done before and makes it visible on the GUI
     * @param pt
     * @param status 
     */
    public void receivePlayersShotResult(Point pt, int status) {
        _bGUI.switchButton(_bGUI.getButton(_bGUI.getPanelOponent(), pt), status, true);
        if(status ==1 || status == 2)
        {
            this.status.setStatus(true);
            _bGUI.activatePanel(_bGUI.getPanelOponent());
        }
        else if(status ==3)
        {
            this.status.setStatus(false);
            _bGUI.deactivatePanel(_bGUI.getPanelOponent());
            _bGUI.deactivatePanel(_bGUI.getPanelPlayer());
            _bGUI.setLabel("GAME OVER! YOU WON!!!");
        }
        
    }
        
    /**
     * PB
     * receives the shotMessage of the Oponent
     * @param message 
     */
    @Override
    public void shotMessage(CommunicationObject message) {
        receiveOponentsShot(message);
    }
    
    /**
     * receives the answer of the shot done before
     * @param message 
     */
    @Override
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
    @Override
    public void startMessage(CommunicationObject message){
        if(message.getStarted())
        {
            status.setStatus(true);
           _bGUI.activatePanel(_bGUI.getPanelOponent());
           _bGUI.setLabel("Ihr Zug!");
        }
        else
        {
            status.setStatus(false);
           _bGUI.setLabel("Warte auf Server!");
        }
    }
    
    /**
     * PB
     * receives the initialized Message from the server
     * @param message 
     */
    @Override
    public void initializeMessage(CommunicationObject message){
        _bGUI.createWindow();
        _bGUI.setVisible(true);
        _bGUI.deactivatePanel(_bGUI.getPanelOponent());
    }
    
    /**
     * sends the shotmessage to the server
     * @param Point pt 
     */
    public void sendPlayerShot(Point pt) {
        status.setStatus(false);
        _bGUI.deactivatePanel(_bGUI.getPanelOponent());
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
    public String getReadyResponse (boolean pressedButton)
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
                   if(bShipCount==Shiptypes.BSHIP.getCount()) {
                       this.guiButtons[0][0].setEnabled(false);
                   }
                   break;
               case CRUISER:
                   cruiserCount++;
                   if(cruiserCount==Shiptypes.CRUISER.getCount()) {
                       this.guiButtons[0][1].setEnabled(false);
                   }
                   break;
               case DESTROYER:
                   destroyerCount++;
                   if(destroyerCount==Shiptypes.DESTROYER.getCount()) {
                       this.guiButtons[1][0].setEnabled(false);
                   }
                   break;
               case SUBMARINE:
                   submarineCount++;
                   if(submarineCount==Shiptypes.SUBMARINE.getCount()) {
                       this.guiButtons[1][1].setEnabled(false);
                   }
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
       
       if(response.isEmpty()&&pressedButton)
       {
           CommunicationObject message = new CommunicationObject(CommunicationObjectType.INITIALIZE);
           message.setInitialized(true);
           _client.sendToServer(message);
           response ="Warte auf antwort des Servers.";
           status.setShipPlacementactive(false);
       }
       
       
       response = "<html>" + response +"</html>";
       return response;
    }
    
    public BattleGUI getBGUI()
    {
        return _bGUI;
    }
    
    public void setServerIP(String IPAdress)
    {
        serverIP = IPAdress;
    }
}
