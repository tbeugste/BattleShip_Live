/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;
import javax.swing.*;
import java.awt.Point;

/**
 *
 * @author Patrik
 */
public class MyButton extends JButton {
    private Point battlePoint;
    
    public MyButton(int height, int width) {
        super();
        battlePoint = new Point(width, height);
    }
        
    public Point getPoint() {
        return this.battlePoint;
    }
}
