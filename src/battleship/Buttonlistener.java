/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;
import javax.swing.JButton;

/**
 *
 * @author Patrik Buholzer
 */
public class Buttonlistener implements ActionListener {
    
    private BattleGUITest bGUI;
    
    /**
     * Constructor
     * @param BattleGUITest aBf 
     */
    
    public Buttonlistener(BattleGUITest aBGUI) {
        this.bGUI = aBGUI;
    }
        
    /**
     * PB
     * Gets the command and switch the action
     * @param e 
     */
    
     public void actionPerformed(ActionEvent e){
        
         if (e.getActionCommand().contains("Shot")) {
                
                String s = e.getActionCommand();
                Point p = new Point();
                
                // hier wird der String in einen Point umgewandelt
                int x = Integer.parseInt(s.substring(6,7));
                int y = Integer.parseInt(s.substring(8,9));
                p.setLocation(x, y);
                
                bGUI.shot(p, (JButton)e.getSource());
                
         } else {
                switch (e.getActionCommand()) {
                    case "newGame":
                        // Neues Spiel starten:
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                   default:
                       break;
                }    
         }
    }  
}
     
