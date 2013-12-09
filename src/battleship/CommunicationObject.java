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
    private boolean _connected;
    private boolean _initialized;
    private boolean _hited;
    private boolean _destroyed;
    private String _Message;
    
    /**
     * Constructor to create a shot - Message
     * @param shot 
     */
    public CommunicationObject(Point shot)
    {
        _type = CommunicationObjectType.SHOT;
        _shot = shot;
    }
    /**
     * Constructor to create a reply Message from a shot
     * @param type
     * @param shot
     * @param hited
     * @param destroyed 
     */
    public CommunicationObject(CommunicationObjectType type, Point shot, boolean hited, boolean destroyed)
    {
        _type = type;
        _hited = hited;
        _destroyed = destroyed;
        _shot = shot;
    }
    
    /**
     * Constructor to create a initialize object
     * @param type
     * @param initialized 
     */
    public CommunicationObject(CommunicationObjectType type, boolean initialized)
    {
        _type = type;
        _initialized = initialized;
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
    
    /**
     * method to return Initialized Object
     * @return 
     */
    public boolean getInitialized()
    {
        return _initialized;
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
     * method to return Message
     * @return 
     */
    public String getMessage()
    {
        return _Message;
    }
    
    public void setMessage(String Message)
    {
        _Message = Message;
    }
}
