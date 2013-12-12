/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author Andreas Eugster
 * Class to create an Object to send Data from a Player to another
 */
public class PlayerTurn implements Serializable{
    
    private Point m_PlayerShot;
    private boolean m_Striked;
    private InetAddress m_SourceIP;
    private InetAddress m_DestinationIP;
    /**
     * Constructor to initialize the object
     * @param SourceIP
     * @param DestinationIP
     * @param PlayerShot
     * @param striked 
     */
    public PlayerTurn(InetAddress SourceIP, InetAddress DestinationIP,
            Point PlayerShot, boolean striked)
    {
        m_PlayerShot = PlayerShot;
        m_Striked = striked;
        m_SourceIP = SourceIP;
        m_DestinationIP = DestinationIP;
    }
    
    /**
     * Method to return Coordinates from PlayerShot
     * @return PlayerShot
     */
    public Point getPlayerShot()
    {
        return m_PlayerShot;
    }
    
    /**
     * Method to return Striked property
     * @return m_striked
     */
    public boolean getStriked()
    {
        return m_Striked;
    }
    
    /**
     * Method to set Striked Property
     * @param Striked 
     */
    public void setStriked(boolean Striked)
    {
        m_Striked = Striked;
    }
    
    /**
     * Method to return SourceAdress
     * @return m_SourceIP
     */
    public InetAddress getSourceAdress()
    {
        return m_SourceIP;
    }
    
    /**
     * Method to return DestinationAdress
     * @return m_SourceIP
     */
    public InetAddress getDestinationAdress()
    {
        return m_DestinationIP;
    }
}
