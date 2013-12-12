/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Battleship;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

/**
 *
 * @author Patrik Buholzer
 */
public class BattleGUITest extends Battleship {
    
    //private Buttonlistener bl = new Buttonlistener(this);
    private javax.swing.JMenuBar menuBar;
    private static javax.swing.JPanel panelPlayer;
    private static javax.swing.JPanel panelOponent;
    private javax.swing.JLabel label = new JLabel("Start");
    public Buttonlistener bl = new Buttonlistener(this);
    private MainWindow window;
    
    public BattleGUITest () {
         
    }
    
    /**
     * PB
     * Creates the MainWindow
     */
    public void createWindow() {
        window = new MainWindow();
        window.setVisible(true);
    }
    
    /**
     * PB
     * Creates the Menubar
     */
    public void createMenuBar() {
        menuBar = new JMenuBar(); 
        JMenu game = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(bl);
        newGame.setActionCommand("newGame");
        game.add(newGame);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(bl);
        exit.setActionCommand("exit");
        game.add(exit);
        menuBar.add(game);
        
        window.setJMenuBar(menuBar);
    }
    
    /**
     * PB
     * Creates the gamewindow with 2 panels and 1 label.
     */
    public void createPlay() {
        JPanel mainGrid = new JPanel(new GridLayout(1,3));
        mainGrid.add(panelOponent);
        mainGrid.add(label);
        label.setHorizontalAlignment(JLabel.CENTER);
        mainGrid.add(panelPlayer);
        window.setContentPane(mainGrid);
    }
    
    /**
     * PB
     * Creates the GUI and set it visible
     */
    public void createPlayGUI() {
        createWindow();
        createMenuBar();
    }
    
    /**
     * PB 
     * Creates the buttons
     * @param row
     * @param column
     * @return 
     */
    public MyButton createButton(int row, int column) {
        MyButton myButton = new MyButton(row, column);
        myButton.addActionListener(bl);
        myButton.setActionCommand("Shot");
        return myButton;
    }
    
    /**
     * PB
     * Fills the panel witch created buttons.
     * @param height
     * @param width
     * @return 
     */
    private JPanel createPanel(int height, int width) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(height,width));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                panel.add(createButton(i,j));
            }
        }
        return panel;
    }
            
    public void newGame() {
        super.bField.setShips();
        panelOponent = createPanel(super.bField.getWidth(), super.bField.getHeight());
        panelPlayer = createPanel(super.bField.getWidth(), super.bField.getHeight());
        createPlay();
        createPlayGUI();
    }
        
    
    /**
     * PB
     * Gets the button and the point from the Buttonlistener and checks the shot
     * @param pt
     * @param jb 
     */
    public void shot(MyButton mb) {
        int i = 1;
        bField.shot(mb.getPoint());
        switchButton(mb, i);
        
        // test ob auf Button zugegriffen werden kann:
        for(Component c : panelPlayer.getComponents()) {
            if (c instanceof MyButton) {
                MyButton button = (MyButton)c;
                if (button.getPoint().distance(mb.getPoint()) <= 1) {
                    button.setBackground(Color.red);
                }                          
            }
        }
    }
    
    public void receivedShot(Point pt) {
        
    }
    
    /**
     * PB
     * Changes the button to disabled and prints the status in the label
     * @param button
     * @param status 
     */
    private void switchButton(MyButton button, int status) {
        switch (status) {
            // Daneben:
            case 0:
                button.setEnabled(false);
                button.setBackground(new Color(0,0,255));
                setLabel("Daneben!");
                break;
            // Treffer:
            case 1:
                button.setEnabled(false);
                button.setBackground(new Color(0,255,0));
                setLabel("Treffer!");
                break;
            // Versenkt:
            case 2:
                button.setEnabled(false);
                button.setBackground(new Color(255,0,0));
                setLabel("Versenkt!");
                break;
            // Gameover:
            case 3:
                button.setEnabled(false);
                button.setBackground(new Color(255,0,0));
                for(Component c : panelOponent.getComponents()) {
                    if (c instanceof MyButton) {
                        if (c.isEnabled()) {
                            switchButton((MyButton)c, 0);
                        }                            
                    }
                }
                setLabel("Game Over!");
                break;
            default:
                break;
        }
    }
    
    private MyButton getButton(JPanel panel, Point pt) {
        MyButton returnButton = new MyButton((int)pt.getX(), (int)pt.getY());        
        for (Component c : panel.getComponents()) {
            if (c instanceof MyButton) {
                MyButton button = (MyButton)c;
                if (button.getPoint() == pt) {
                    returnButton = button;
                }
            }
        }
        return returnButton;
    }
       
    /**
     * PB
     * Sets the text in the label of the GUI
     * @param s 
     */
    public void setLabel(String s) {
        label.setText(s);
    }
        
}
