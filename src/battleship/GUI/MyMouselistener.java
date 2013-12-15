/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;

/**
 *
 * @author Patrik Buholzer
 */
public class MyMouselistener extends MouseAdapter {
    private BattleGUI _bGUI;
    
    public MyMouselistener(BattleGUI aBGUI) {
        _bGUI = aBGUI;
    }
    
    public void MouseEntered(MouseEvent e) {
        MyButton button = (MyButton)e.getSource();
        button.setBackground(Color.red);
        _bGUI.setLabel("R" + e.getX());
        //button.setIcon(new ImageIcon(getClass().getResource("fire.jpg")));
        //_bGUI.checkPlaceShip();
    }
}
