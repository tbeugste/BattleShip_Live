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
    private ArrayList<Ship> _fleetOri = new ArrayList<>();
    private ArrayList<Point> _kiShots = new ArrayList<>();
    private ArrayList<Point> _latestHIT = new ArrayList<>();
    private ArrayList<Point> _kiShotsLeft = new ArrayList<>();
    
    
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
        _fleetOri = _fleet;
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
        _fleetOri.clear();
        _kiShots.clear();
        _latestHIT.clear();
    }
    
    /**
     * Method to reset the game. Set new ships and reset all local variables
     */
    public void regame()
    {
        resetLocalVariables();
        refillArrays();
        setRandomShips();
    }
    
    private void refillArrays()
    {
        Point hit = new Point();
        for(int x =0;x<10;x++)
        {
            for( int y=0;y<10;y++)
            {
                hit = new Point();
                hit.x = x;
                hit.y = y;
                _kiShotsLeft.add(hit);
            }
        }
    }
    
    /**
     * apply Message to the KI
     * @param message 
     */
    public CommunicationObject sendMessage(CommunicationObject message)
    {
        switch(message.getType())
        {
            case SHOT:
                message = shotMessage(message);
                break;
            case REPLY:
                message= replyMessage(message);
                break;
        }
        return message;
    }
    
    /**
     * Reply to a Shotmessage
     * @param message
     * @return 
     */
    private CommunicationObject shotMessage(CommunicationObject message)
    {
        boolean hit = false;
        for(Ship ship:_fleet)
        {
            if(ship.ApplyShot(message.getShot()))
            {
                hit = true;
                message.shotAplyed(hit, ship.IsDestroyed(), _fleet.isEmpty());
                break;
            }
        }
        if(!hit)
        {
            message.shotAplyed(hit, hit, hit);
        }
        return message;
    }
    
    /**
     * Reply to a replyMessage
     * @param message
     * @return 
     */
    private CommunicationObject replyMessage(CommunicationObject message)
    {
        CommunicationObject answer ;
        if(message.getHit() && !message.getGameover())
        {
            answer = new CommunicationObject(CommunicationObjectType.SHOT);
            _latestHIT.add(message.getShot());
            answer.setShot(calculateNextShot(message));
            
                    
        }
        else
        {
            answer = null;
        }
        return answer;
    }
    
    public CommunicationObject createShot()
    {
        CommunicationObject message = new CommunicationObject(CommunicationObjectType.SHOT);
        message.setShot(calculateNextShot(message));
        return message;
    }
    
    private Point calculateNextShot(CommunicationObject message)
    {
        /*
        //improve KI with some intelligence
        Random rand = new Random();
        Point target = message.getShot();
        Point temp = new Point();
        if(message.getHit() && !message.getDestroyed())
        {
            _latestHIT.add(message.getShot());
            if(_latestHIT.size()>1)
            {
                target.x = target.x + (_latestHIT.get(_latestHIT.size()-2).x-_latestHIT.get(_latestHIT.size()-1).x);
                target.y = target.y + (_latestHIT.get(_latestHIT.size()-2).y- _latestHIT.get(_latestHIT.size()-1).y);
            }
            else
            {
                temp = _latestHIT.get(0);
                if(temp.x<9)
                {
                    target.x = temp.x+1;
                    target.y = temp.y;
                }
                else
                {
                    target.x = temp.x-1;
                    target.y = temp.y;
                }
            }
        }
        else if(message.getHit() && message.getDestroyed())
        {
            _latestHIT.clear();
        }
        else if(!_latestHIT.isEmpty())
        {
            
        }
        else
        {
            
        }*/
        //Simple way to go...
        Random rand = new Random();
        Point target = message.getShot();
        
        target = _kiShotsLeft.get(rand.nextInt(_kiShotsLeft.size()));
        _kiShotsLeft.remove(target);
        _kiShots.add(target);
        return target;
    }
}
