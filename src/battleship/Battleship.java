/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.engine.Battlefield;
import battleship.GUI.BattleGUI;
import battleship.GUI.StartScreen;

/**
 *
 * @author Andy, Patrik
 */
public class Battleship {
    public static Battlefield bField = new Battlefield(Battleship.bGUI,10,10);;
    public static BattleGUI bGUI = new BattleGUI();;
    public StartScreen startScreen;

        
    public Battleship() {
        startScreen = new StartScreen();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Battleship bs = new Battleship();
    }
}
