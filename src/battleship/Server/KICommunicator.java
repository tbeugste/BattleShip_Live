/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.Server;
import battleship.engine.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
/**
 * This class is responsible to interact with the player. It moves and acts like one.
 * @author Andy
 */
public class KICommunicator {
    /*
    Singleton Construct
    -----------------------------
    */
    private static KICommunicator _instance;
    private KICommunicator ()
    {
        //TODO Create Ships
    }
    public static KICommunicator getInstance()
    {
        if(_instance == null)
        {
            _instance = new KICommunicator();
        }
        return _instance;
    }
    
    /*
    ----------------------------
    */
    
    /**
     * local Variables ----------
     */
    private ArrayList<Ship> _fleet = new ArrayList<>();
    
    
    /**
    * -------------------------->
    */
    /**
    * Method to create Ships on the battlefield
    */
    private void setRandomShips()
    {
        _fleet.clear();
        Ship ship;
        ArrayList<Point> shipPos;
        boolean horizontal;
        Random rand = new Random();
        for(Shiptypes type: Shiptypes.values())
        {
            for(int i = 0;i<type.getCount();i++)
            {
                do{
                horizontal = rand.nextBoolean();
                shipPos = getShipPos(type, horizontal);
                ship = new Ship(shipPos, type);
                }
                while(!correctPlacement(ship));
                _fleet.add(ship);
            }
        }
    }
     
    /**
     * Method to create a new Shipposition
     * @param type
     * @param horizontal
     * @return 
     */
    private ArrayList<Point> getShipPos(Shiptypes type, boolean horizontal)
    {
        ArrayList<Point> allPos = new ArrayList<>();
        Point pos = new Point();
        Random rand = new Random();
        if(horizontal)
        {
            pos.x = rand.nextInt(10-type.getValue());
            pos.y = rand.nextInt(10);
            allPos.add(pos);
            for(int i =0;i<type.getCount();i++)
            {
                pos.x=allPos.get(i).x+i;
                pos.y=allPos.get(i).y;
                allPos.add(pos);
            }
        }
        else
        {
            pos.x = rand.nextInt(10);
            pos.y = rand.nextInt(10-type.getValue());
            allPos.add(pos);
            for(int i =0;i<type.getCount();i++)
            {
                pos.x=allPos.get(i).x;
                pos.y=allPos.get(i).y+i;
                allPos.add(pos);
            }
        }
        
        return allPos;
    }
    
    /**
     * Method to test if the placement is correct
     * @param tShip
     * @return 
     */
    private boolean correctPlacement(Ship tShip)
    {
        boolean correct = true;
        
        for(Ship ship: _fleet)
        {
            for(Point shipPos: ship.getCoordinates())
            {
                for(Point tShipPos: tShip.getCoordinates())
                {
                    if(tShipPos.distance(shipPos)<=1)
                    {
                        correct=false;
                        return correct;
                    }
                }
            }
        }
        
        return correct;
        
    }
    /**
      * Method to reset all local variables
      */
    private void resetLocalVariables()
    {
        _fleet.clear();
    }
    
    /**
     * Method to reset the game. Set new ships and reset all local variables
     */
    public void regame()
    {
        resetLocalVariables();
        setRandomShips();
    }
    
    /**
     * apply Message to the KI
     * @param message 
     */
    public CommunicationObject sendMessage(CommunicationObject message){return message;}
    
    private CommunicationObject shotMessage(CommunicationObject message){return message;}
    
    private CommunicationObject replyMessage(CommunicationObject message){return message;}
    
    
}
