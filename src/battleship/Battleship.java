/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.engine.Battlefield;
import battleship.GUI.BattleGUITest;

/**
 *
 * @author Andy, Patrik
 */
public class Battleship {
    public Battlefield bField;
    public BattleGUITest bGUI;
    public TCPServer server;
    public TCPClient client;
        
    public Battleship() {
        this.bField = new Battlefield(10,10);
        this.bGUI = new BattleGUITest();
       
        bGUI.createPlayGUI();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Battleship bs = new Battleship();
    }
}
