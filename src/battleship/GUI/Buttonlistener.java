/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import battleship.Battleship;
import battleship.engine.Shiptypes;
import battleship.engine.Ship;
import java.awt.Color;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


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
                if (Battleship.bField.status.getStatus()) {
                    _bGUI.shot((MyButton)e.getSource());
                } else if (Battleship.bField.status.getShipPlacementactive()){
                    MyButton but = (MyButton)e.getSource();
                    if(Battleship.bField.enoughSpace(but.getPoint()))
                    {
                        Ship ship = Battleship.bField.createPlacedShip(but.getPoint());
                        Battleship.bField.addShip(ship);
                        _bGUI.ship = ship.GetShipType();
                        for(Point point : ship.getCoordinates()) {
                            _bGUI.switchButton(Battleship.bField.buttonArray[point.x][point.y], 2, true);
                            _bGUI.switchButton(Battleship.bField.buttonArray[point.x][point.y], 6, true);
                        }
                        Battleship.bField.status.setShipPlacementactive(false);
                        Battleship.bField.getReadyResponse (false);
                    }
                }
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
            case "Ship selected bship":
                Battleship.bField.placedShiptype = Shiptypes.BSHIP;
                Battleship.bField.status.setShipPlacementactive(true);
                _bGUI.setLabel("Schiff plazieren!");
                break;
            case "Ship selected cruiser":
                Battleship.bField.placedShiptype = Shiptypes.CRUISER;
                Battleship.bField.status.setShipPlacementactive(true);
                _bGUI.setLabel("Schiff plazieren!");
                break;
             case "Ship selected destroyer":
                Battleship.bField.placedShiptype = Shiptypes.DESTROYER;
                Battleship.bField.status.setShipPlacementactive(true);
                _bGUI.setLabel("Schiff plazieren!");
                break;
             case "Ship selected submarine":
                Battleship.bField.placedShiptype = Shiptypes.SUBMARINE;
                Battleship.bField.status.setShipPlacementactive(true);
                _bGUI.setLabel("Schiff plazieren!");
                break;
             case "GameReady":
                 String message = Battleship.bField.getReadyResponse(true);
                 
                 break;
             case "horizontal":
                     Battleship.bField.horizontal = true;
                     Battleship.bField.guiButtons[2][0].setBorder(BorderFactory.createLineBorder(Color.green, 2));
                     Battleship.bField.guiButtons[2][1].setBorder(null);
                 break;
             case "vertical":
                     Battleship.bField.horizontal = false;
                     Battleship.bField.guiButtons[2][0].setBorder(null);
                     Battleship.bField.guiButtons[2][1].setBorder(BorderFactory.createLineBorder(Color.green, 2));
                 break;
            default:
               break;
        }   
    }  
}
     
