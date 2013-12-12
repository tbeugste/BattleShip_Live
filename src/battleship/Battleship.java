/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Andy, Patrik
 */
public class Battleship {
    private static final Battlefield bField = new Battlefield(10,10);
    private static final BattleGUITest bGUI = new BattleGUITest(bField);
        
    public Battleship() {
        bGUI.createGUI();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Battleship bs = new Battleship();
    }
}
