/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.*;
import java.util.*;

/**
 *
 * @author Patrik Buholzer
 */
public class Battlefield {
    
    private int height;
    private int width;
    private ArrayList<Ship> fleet = new ArrayList<>();
    
    public Battlefield (int height, int width) {
        this.height = height;
        this.width = width;
        setShips();
    }
    
    public void TestAE()
    {
        //Andy
    }
            

    /**
     * PB
     * Sets some ships for testing
     */
    private void setShips() {
        ArrayList<Point> a1 = new ArrayList<>();
        a1.add(new Point(1,1));
        a1.add(new Point(1,2));
        Ship s1 = new Ship(a1, Shiptypes.SUBMARINE);
        fleet.add(s1);
        
        ArrayList<Point> a2 = new ArrayList<>();
        a2.add(new Point(3,6));
        a2.add(new Point(3,7));
        a2.add(new Point(3,8));
        Ship s2 = new Ship(a2, Shiptypes.DESTROYER);
        fleet.add(s2);
    }
    
    /**
     * PB
     * decides if a shot is a hit or not
     * @param p
     * @return int 0 = no hit, 1 = hit, 2 = ship destroyed, 3 = gameover
     */
    public int shot(Point p) {
        
        for (Ship aShip: fleet) {
            if(aShip.ApplyShot(p)) {
                if(aShip.IsDestroyed()) {
                    fleet.remove(aShip);
                    if(fleet.isEmpty()) {
                        return 3;
                    } else {
                        return 2;
                    }
                } else {
                    return 1;
                }
            }
        } return 0;
        
        // Meldung über Netzwerk senden.
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
   
}
