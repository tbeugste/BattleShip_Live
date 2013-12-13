/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Patrik Buholzer
 */
public class Buttonlistener implements ActionListener {
    
    private BattleGUITest _bGUI;
    private StartScreen _sGUI;

    
    /**
     * Constructor
     * @param BattleGUITest aBf 
     */
    
    public Buttonlistener(BattleGUITest aBGUI) {
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
                
                //_bGUI.setLabel("" + (int)mb.getPoint().getX() + " " +(int)mb.getPoint().getY());
                break;
            case "newGame":
                _bGUI.newGame();
                break;
            case "exit":
                System.exit(0);
                break;
            case "help":
        try {
            _bGUI.help();
        }
        catch (Exception ex) {
            Logger.getLogger(Buttonlistener.class.getName()).log(Level.SEVERE, null, ex);
        }
                break;
                
           default:
               break;
        }    
    }  
}
     
