/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 *
 * @author Patrik Buholzer
 */
public class Buttonlistener implements ActionListener {
    
    private BattleGUI _bGUI;
    
    /**
     * Constructor
     * @param BattleGUITest aBf 
     */
    
    public Buttonlistener(BattleGUI aBGUI) {
        this._bGUI = aBGUI;
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
                _bGUI.shot(mb);
                break;
            case "newGame":
                _bGUI.newGame();
                break;
            case "exit":
                System.exit(0);
                break;
           default:
               break;
        }    
    }  
}
     
