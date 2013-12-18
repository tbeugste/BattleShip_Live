/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;
import java.awt.*;
import java.util.*;
import java.io.*;


/**
 *
 * @author Andreas Eugster
 * Shipclass to store informations about each ship
 */
public class Ship implements Serializable{
    
    /**
     * MemberRegion to define Variables
     * 
     */
    private ArrayList<Point> m_coordinates = new ArrayList<>(); 
    private Shiptypes m_type;
    
    /**
     * 
     * @param Coordinates
     * @param Stype 
     */
    public Ship(ArrayList<Point> Coordinates, Shiptypes Stype)
    {
        m_coordinates = Coordinates;
        m_type = Stype;
    }
     
    /**
     * Methods
     */
    
    /**
     * Method to check if Ship is destroyed
     * @return true if destroyed
     */
    public boolean IsDestroyed()
    {
        return m_coordinates.isEmpty();
    }
    
    /**
     * Method to apply a shot to the ship and test if it was a success
     * @param shot 
     * @return true if successfully hit
     */
    public boolean ApplyShot(Point shot)
    {
        return m_coordinates.remove(shot);
    }
    
    /**
     * Method to return the amount of health points
     * @return 
     */
    public int GetFieldsLeft()
    {
        return m_coordinates.size();
    }
    
    /**
     *  Method to return type of ship
     * @return type of Ship
     */
    public Shiptypes GetShipType()
    {
        return m_type;
    }
    
    public ArrayList<Point> getCoordinates()
    {
        return m_coordinates;
    }
}
