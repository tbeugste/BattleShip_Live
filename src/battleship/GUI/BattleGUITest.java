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
public class BattleGUITest extends javax.swing.JFrame {
    
    //private Buttonlistener bl = new Buttonlistener(this);
    private javax.swing.JPanel panelPlayer;
    private javax.swing.JPanel panelOponent;
    private javax.swing.JLabel label;
    private Buttonlistener bl;
    
    public BattleGUITest () {
        super("Battleship");
        bl = new Buttonlistener(this);
        createWindow();
        
        // darstellen:
        super.setVisible(true);
    }
    
    /**
     * PB
     * Creates the MainWindow
     */
    public void createWindow() {
        super.setSize(1200,600);
        super.setLocation(200,100);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setJMenuBar(createMenuBar());
    }
    
    /**
     * PB
     * Creates the Menubar
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar(); 
        // Neuer Menupunkt:
        JMenu game = new JMenu("Game");
        // Neue Untermenus zu Game:
            // New Game:
            JMenuItem newGame = new JMenuItem("New Game");
            newGame.addActionListener(bl);
            newGame.setActionCommand("newGame");
            game.add(newGame);
            // Exit:
            JMenuItem exit = new JMenuItem("Exit");
            exit.addActionListener(bl);
            exit.setActionCommand("exit");
            game.add(exit);
        // Menupunkt zu Menubar hinzufügen:
        menuBar.add(game);
        
        // Die Menubar zurück geben:
        return menuBar;
    }
    
    /**
     * PB
     * Creates the gamewindow with 2 panels and 1 label.
     */
    public void createPlayGUI() {
        JPanel mainGrid = new JPanel(new GridLayout(1,3));
        mainGrid.add(panelOponent);
        label = new JLabel("Start");
        mainGrid.add(label);
        label.setHorizontalAlignment(JLabel.CENTER);
        mainGrid.add(panelPlayer);
        this.setContentPane(mainGrid);
    }
        
    /**
     * PB 
     * Creates the buttons
     * @param row
     * @param column
     * @return MyButton
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
     * @return JPanel
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
        Battleship.bField.setShips();
        panelOponent = createPanel(Battleship.bField.getWidth(), Battleship.bField.getHeight());
        panelPlayer = createPanel(Battleship.bField.getWidth(), Battleship.bField.getHeight());
        createPlayGUI();
        
        //test:
        Battleship.bField.initializeGame();
        Battleship.bField.setShips();
        
        super.setVisible(true);
    }
        
    
    /**
     * PB
     * Gets the button and the point from the Buttonlistener and checks the shot
     * @param MyButton mb
     */
    public void shot(MyButton mb) {
        Battleship.bField.sendPlayerShot(mb.getPoint());
        /*
        // test ob auf Button zugegriffen werden kann:
        for(Component c : panelPlayer.getComponents()) {
            if (c instanceof MyButton) {
                MyButton button = (MyButton)c;
                if (button.getPoint().distance(mb.getPoint()) <= 1) {
                    button.setBackground(Color.red);
                }                          
            }
        }
        */
    }
        
    /**
     * PB
     * Changes the button to disabled and prints the status in the label
     * @param button
     * @param status 
     */
    public void switchButton(MyButton button, int status) {
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
    
    /**
     * PB
     * finds a Button and gives it back
     * @param panel
     * @param pt
     * @return 
     */
    public MyButton getButton(JPanel panel, Point pt) {
        for (Component c : panel.getComponents()) {
            if (c instanceof MyButton) {
                MyButton button = (MyButton)c;
                if (button.getPoint() == pt) {
                    return button;
                }
            }
        }
        return null;
    }
    
    /**
     * PB
     * returns the JPanel of the Player
     * @return JPanel
     */
    public JPanel getPanelPlayer() {
        return this.panelPlayer;
    }
    
    /**
     * PB
     * returns the JPanel of the Oponent
     * @return JPanel
     */
    public JPanel getPanelOponent() {
        return this.panelOponent;
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
