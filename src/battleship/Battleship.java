/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.engine.Battlefield;
import battleship.GUI.BattleGUITest;
import battleship.GUI.StartScreen;

/**
 *
 * @author Andy, Patrik
 */
public class Battleship {
    public static Battlefield bField;
    public BattleGUITest bGUI;
    public StartScreen sGUI;
    public static TCPServer server;
    public static TCPClient client;
    public static KIServer kiServer;
        
    public Battleship() {
       //bField = new Battlefield(bGUI, 10, 10);
       //bGUI = new BattleGUITest();
       sGUI = new StartScreen();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Battleship bs = new Battleship();
    }
}
