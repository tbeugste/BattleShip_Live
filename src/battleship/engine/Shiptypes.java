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
    SUBMARINE(2), DESTROYER(3), CRUISER(4), BSHIP(5);
    
    private final int id;
    Shiptypes(int id) { this.id = id; }
    public int getValue() { return id; }
}
