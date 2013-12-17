/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import battleship.Battleship;

/**
 *
 * @author Patrik Buholzer
 */
public class MyMouselistener implements MouseListener {
    private BattleGUI _bGUI;
    //private MyButton _originButton;
    
    public MyMouselistener(BattleGUI aBGUI) {
        _bGUI = aBGUI;
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if(Battleship.bField.status.getShipPlacementactive()) {
            _bGUI.switchButton((MyButton)e.getSource(), 4);
            _bGUI.setLabel("R" + e.getX());
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        if(Battleship.bField.status.getShipPlacementactive()) {
            _bGUI.switchButton((MyButton)e.getSource(), 6);
        }
    }
    
    
    @Override
    public void mousePressed(MouseEvent e) {
       
    }
    @Override
    public void mouseReleased(MouseEvent e) {
       
    }
    @Override
    public void mouseClicked(MouseEvent e) {
       
    }
}
