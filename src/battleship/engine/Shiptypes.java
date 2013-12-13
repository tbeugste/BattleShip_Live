/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;
import java.io.*;
/**
 *
 * @author Andreas Eugster
 */
public enum Shiptypes implements Serializable{
    SUBMARINE(2,4), DESTROYER(3,3), CRUISER(4,2), BSHIP(5,1);
    
    private final int id;
    private final int count;
    
    Shiptypes(int id, int count) { this.id = id; this.count = count;}
    public int getValue() { return id; }
    public int getCount(){return count;}
    
}
