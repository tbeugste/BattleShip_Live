/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import javax.swing.JFrame;

/**
 *
 * @author Patrik
 */
public class MainWindow extends javax.swing.JFrame {
    JFrame frame;
    
    public MainWindow() {
        super("Battleship");
        frame = new JFrame();
        frame.setSize(800,400);
        frame.setLocation(50,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
