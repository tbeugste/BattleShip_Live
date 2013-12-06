/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;
import java.awt.*;
import java.util.*;
import java.io.*;
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
    
    public void shotAplyed(boolean hited, boolean destroyed)
    {
        _type = CommunicationObjectType.REPLY;
        _hited = hited;
        _destroyed = destroyed;
    }
}
