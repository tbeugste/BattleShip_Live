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
    private TCPServer server;// = new TCPServer();
    private TCPClient client;// = new TCPClient("127.0.0.1", 9999);
        
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
