/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.GUI.MyButton;
import battleship.GUI.BattleGUITest;
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
        
        switch (e.getActionCommand()) {
            case "Shot":
                MyButton mb = (MyButton)e.getSource();
                bGUI.shot(mb);
                break;
            case "newGame":
                bGUI.newGame();
                break;
            case "exit":
                System.exit(0);
                break;
           default:
               break;
        }    
         
    }  
}
     
