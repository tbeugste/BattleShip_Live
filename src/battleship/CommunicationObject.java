/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;
import java.awt.*;
import java.io.*;
import java.lang.String;
/**
 *
 * @author Andy
 * Object to Handle Communicationinformations between Clients <-> Server
 */
public class CommunicationObject implements Serializable {
    private CommunicationObjectType _type;
    private Point _shot = null;
    private boolean _connected = false;
    private boolean _initialized = false;
    private boolean _hited = false;
    private boolean _destroyed = false;
    private boolean _start = false;
    private String _Message = null;
    
    
    /**
     * Constructor to create a shot - Message
     * @param type 
     */
    public CommunicationObject(CommunicationObjectType type)
    {
       _type = type;
    }
    
    /**
     * Mehtod to return shot coordinates
     * @return _shot
     */
    public Point getShot()
    {
       return _shot;
    }
    
    /**
     * method to set Shot property
     * @param shot 
     */
    public void setShot(Point shot)
    {
        _shot = shot;
    }
    
    /**
     * Method to return hit boolean
     * @return 
     */
    public boolean getHit()
    {
        return _hited;
    }
    
    /**
     * method to return destroyed boolean
     * @return 
     */
    public boolean getDestroyed()
    {
        return _destroyed;
    }
    
    /**
     * Method to return Object-Type
     * @return 
     */
    public CommunicationObjectType getType()
    {
        return _type;
    }
        
    public void setType(CommunicationObjectType type)
    {
        _type = type;
    }
    
    /**
     * method to return Initialized Object
     * @return 
     */
    public boolean getInitialized()
    {
        return _initialized;
    }
    
    public void setInitialized(boolean initialized)
    {
        _initialized = initialized;
    }
    
    /**
     * method to return Connected object
     * @return 
     */
    public boolean getConnected()
    {
        return _connected;
    }
    
     /**
      * method to set connectedProperty
      * @param connected 
      */
    public void setConnected(boolean connected)
    {
        _connected = connected;
    }
    
    /**
     * method to return Message
     * @return 
     */
    public String getMessage()
    {
        return _Message;
    }
    
    /**
     * Method to return Message
     * @param Message 
     */
    public void setMessage(String Message)
    {
        _Message = Message;
    }
    
     /**
     * Method to set variables if a shot hit or not
     * @param hited
     * @param destroyed 
     */
    public void shotAplyed(boolean hited, boolean destroyed)
    {
        _type = CommunicationObjectType.REPLY;
        _hited = hited;
        _destroyed = destroyed;
    }
    
    public void setStarted(boolean started)
    {
        _start = started;
    }
    
    public boolean getStarted()
    {
        return _start;
    }
}
