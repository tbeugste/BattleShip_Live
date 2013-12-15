/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Battleship;
import battleship.engine.Shiptypes;
import static battleship.engine.Shiptypes.BSHIP;
import static battleship.engine.Shiptypes.CRUISER;
import static battleship.engine.Shiptypes.DESTROYER;
import static battleship.engine.Shiptypes.SUBMARINE;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/** 
 *
 * @author Patrik Buholzer
 */
public class BattleGUI extends javax.swing.JFrame {
    
    //private Buttonlistener bl = new Buttonlistener(this);
    private static javax.swing.JPanel panelPlayer;
    private static javax.swing.JPanel panelOponent;
    private Shiptypes ship;
    private javax.swing.JLabel label;
    private JComboBox shipNames;
    public Buttonlistener bl;
    public MyMouselistener ml;
    private boolean _horizontal;
     
    
    public BattleGUI () {
        super("Battleship");
        bl = new Buttonlistener(this);
        ml = new MyMouselistener(this);
        createWindow();
        // darstellen:
        super.setVisible(true);
    }
    
    /**
     * PB
     * Creates the MainWindow
     */
    public void createWindow() {
        super.setSize(800,600);
        super.setResizable(false);
        super.setLocation(200,100);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setJMenuBar(createMenuBar());
        JPanel mainGrid = new JPanel (new FlowLayout());
        mainGrid.setBackground(Color.white);
        super.add(mainGrid);
        
        label = new JLabel("Schiffe plazieren!");
        label.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel panel = new JPanel (new GridLayout(2,0));
        
        panel.add(setShip());
        panel.add(label);
        
        mainGrid.add(setPanelPlayer());
        mainGrid.add(setPanelOpponent());
        //mainGrid.add(setShip());
        //mainGrid.add(label);
        mainGrid.add(panel);
     
        super.setVisible(true);                 
    }
    
    /**
     * PB
     * Creates the Menubar
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar(); 
        // Neuer Menupunkt:
        JMenu game = new JMenu("Game");
        JMenu option = new JMenu ("Option");
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
        //Neue Untermenus zu Option
            //Mode:
            JMenu mode = new JMenu("Mode");
            option.add(mode);
            //Untermenus zu Mode             
                //SingelPlayer:
                JCheckBoxMenuItem single = new JCheckBoxMenuItem ("SinglePlayer");
                single.addActionListener(bl);
                single.setActionCommand("single");
                mode.add(single);
                //MultiPlayer:
                JCheckBoxMenuItem multi = new JCheckBoxMenuItem ("MulitPlayer");
                multi.addActionListener(bl);
                multi.setActionCommand("single");
                mode.add(multi);
                //ButtonGroup erstellen
                ButtonGroup group = new ButtonGroup();
                group.add(single);
                group.add(multi);                         
            //Help:
            JMenuItem help = new JMenuItem("Help");
            help.addActionListener(bl);
            help.setActionCommand("help");
            option.add(help);
            
        // Menupunkt zu Menubar hinzufügen:
        menuBar.add(game);
        menuBar.add(option);
        
        // Die Menubar zurück geben:
        return menuBar;
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
        myButton.addMouseListener(ml);
        myButton.setRolloverEnabled(true);
        BufferedImage img = null;
        myButton.setIcon (new ImageIcon(getClass().getResource("water.jpg")));
        myButton.setBorder(null);
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
        
        panel.setLayout(new GridLayout(height,width,1,1));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                panel.add(createButton(i,j));           
            }
        }
        return panel;
    }
     public JPanel setPanelPlayer ()
    {
        this.panelPlayer = createPanel(10,10);
        panelPlayer.setBorder(new TitledBorder("Home Field"));
        panelPlayer.setBackground(Color.white);
        return panelPlayer;
    }
    public void creatJComboBox(){
        shipNames = new JComboBox();
        shipNames.addActionListener(bl);
        shipNames.setActionCommand("Ship selected");
        
        for(Shiptypes typ : Shiptypes.values()){
            shipNames.addItem(typ);
        }
    }
    
    public void removeFromCombobox(Shiptypes sTyp){
        shipNames.removeItem(sTyp);
    }
    public void setShip(Shiptypes sTyp){
        Battleship.bField.setShip(sTyp);
    }

    public JPanel setPanelOpponent () 
    {
        this.panelOponent = createPanel(10,10);
        panelOponent.setBorder(new TitledBorder("Opponent Field"));
        panelOponent.setBackground(Color.white);
        return panelOponent;
    }  
    
    public JPanel setShip ()
    {
        JPanel ShipPanel = new JPanel (new GridLayout(2,0));   
       
        JButton bship = new JButton("Schlachtschiff");
        bship.addActionListener(bl);
        bship.setBackground(Color.white);
        bship.setBorder (null);

        JButton cruiser = new JButton ("Kreuzer");
        cruiser.setBorder (null);
        cruiser.setBackground(Color.white);
        
        JButton destroyer = new JButton ("Zerstörer");
        destroyer.setBorder (null);
        destroyer.setBackground (Color.white);
        
        JButton submarine = new JButton("UBoot");
        submarine.setBorder (null);
        submarine.setBackground(Color.white);
                
        bship.setIcon (new ImageIcon(getClass().getResource("BShip.jpg")));
        cruiser.setIcon (new ImageIcon(getClass().getResource("Cruiser.jpg")));
        destroyer.setIcon(new ImageIcon(getClass().getResource("Destroyer.jpg")));
        submarine.setIcon(new ImageIcon(getClass().getResource("Submarine.jpg")));

        ShipPanel.add(bship);
        ShipPanel.add(cruiser);
        ShipPanel.add(destroyer);
        ShipPanel.add(submarine);
        
  
        return ShipPanel;      
    }
    
    public void newGame() {
        Battleship.bField.setShips();
        panelOponent = createPanel(Battleship.bField.getWidth(), Battleship.bField.getHeight());
        panelPlayer = createPanel(Battleship.bField.getWidth(), Battleship.bField.getHeight());
                
        //test:
        Battleship.bField.initializeGame();
        Battleship.bField.setShips();
        
        super.setVisible(true);
    }
        
    
    /**
     * PB
     * Gets the button and the point from the Buttonlistener and send the shot
     * @param MyButton mb
     */
    public void shot(MyButton mb) {
        Battleship.bField.sendPlayerShot(mb.getPoint());
    }
    

    
    public void help() throws Exception
    {
                Runtime rt= Runtime.getRuntime();
                String file ="battleship.GUI.Game_Instruction.txt";
                Process p = rt.exec ("Notepad  "+ file);
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
                button.setIcon(new ImageIcon(getClass().getResource("miss.jpg")));
                setLabel("Daneben!");
                break;
            // Treffer:
            case 1:
                button.setEnabled(false);
                button.setIcon(new ImageIcon(getClass().getResource("fire.jpg")));
                setLabel("Treffer!");
                break;
            // Versenkt:
            case 2:
                button.setEnabled(false);
                if(ship == SUBMARINE)
                {
                    button.setIcon(new ImageIcon(getClass().getResource("Submarine.jpg")));
                }
                if(ship == DESTROYER)
                {
                    button.setIcon(new ImageIcon(getClass().getResource("Destroyer.jpg")));
                }
                if(ship == CRUISER)
                {
                    button.setIcon(new ImageIcon(getClass().getResource("Cruiser.jpg")));
                }
                if(ship == BSHIP)
                {
                    button.setIcon(new ImageIcon(getClass().getResource("BShip.jpg")));
                }
                setLabel("Versenkt!");
                break;
            // Gameover:
            case 3:
                button.setEnabled(false);
                //button.setIcon(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\fire.jpg"));
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
     * @return MyButton
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
     * activates all buttons in a panel
     * @param panel 
     */  
    private void activatePanel(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof MyButton) {
                MyButton button = (MyButton)c;
                button.setEnabled(true);
            }
        }
    }
    
    /**
     * PB
     * deactivates all buttons in a panel
     * @param panel 
     */
    private void deactivatePanel(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof MyButton) {
                MyButton button = (MyButton)c;
                button.setEnabled(false);
            }
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
