/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

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
    private javax.swing.JMenuBar menuBar;
    private static javax.swing.JPanel panelPlayer;
    private static javax.swing.JPanel panelOponent;
    private javax.swing.JLabel label = new JLabel("Start");
    public Buttonlistener bl = new Buttonlistener(this);
    private Battlefield bField;
    
    public BattleGUITest (Battlefield bField) {
        super("Battleship");
        this.bField = bField;
        setPanelOponent(bField.getWidth(), bField.getHeight());
        setPanelPlayer(bField.getWidth(), bField.getHeight());   
    }
    
    /**
     * PB
     * Creates the Window size and Location
     */
    public void createWindow() {
        setSize(800,400);
        setLocation(50,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * PB
     * Creates the Menubar
     */
    public void createMenu() {
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
        
        super.setJMenuBar(menuBar);
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
        super.setContentPane(mainGrid);
    }
    
    /**
     * PB
     * Creates the GUI and set it visible
     */
    public void createGUI() {
        createWindow();
        createMenu();
        createPlay();
        
        setVisible(true);
    }
    
    /**
     * PB 
     * Creates the buttons
     * @param row
     * @param column
     * @return 
     */
    public JButton createButton(int row, int column) {
        JButton jbutton = new JButton();
        jbutton.addActionListener(bl);
        jbutton.setActionCommand("Shot");
        jbutton.setLocation(row, column);
        return jbutton;
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
            
    public void setPanelPlayer(int heigth, int width) {
        this.panelPlayer = createPanel(heigth, width);
        panelPlayer.setEnabled(false);
    }
   
    public void setPanelOponent(int heigth, int width) {
        this.panelOponent = createPanel(heigth, width);
    }
    
    /**
     * PB
     * Gets the button and the point from the Buttonlistener and checks the shot
     * @param pt
     * @param jb 
     */
    public void shot(JButton jb) {
        int i = bField.shot(jb.getLocation());
        switchButton(jb, i);
        
        // test ob auf Button zugegriffen werden kann:
        for(Component c : panelPlayer.getComponents()) {
            if (c instanceof JButton) {
                JButton button = (JButton)c;
                if (button.getLocation().distance(jb.getLocation()) < 40) {
                    button.setBackground(Color.red);
                }                          
            }
        }
    }
    
    /**
     * PB
     * Changes the button to disabled and prints the status in the label
     * @param button
     * @param status 
     */
    private void switchButton(JButton button, int status) {
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
                    if (c instanceof JButton) {
                        if (c.isEnabled()) {
                            switchButton((JButton)c, 0);
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
     * Sets the text in the label of the GUI
     * @param s 
     */
    public void setLabel(String s) {
        label.setText(s);
    }
        
}
