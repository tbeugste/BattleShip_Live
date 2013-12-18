/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;
import java.awt.Point;
import java.io.*;
/**
 *
 * @author Andreas Eugster
 * Defines all Shiptypes
 */
public enum Shiptypes implements Serializable{
    BSHIP(5,1, null), CRUISER(4,2, null), DESTROYER(3,3, null),SUBMARINE(2,4, null);
    
    private final int id;
    private final int count;
    private Point pos;
    Shiptypes(int id, int count, Point pos) { this.id = id; this.count = count; this.pos = pos;}
    public int getValue() { return id; }
    public int getCount(){return count;}
    public Point getPos(){return pos;}
    public void setPos(Point pos){this.pos = pos;}
    
}
