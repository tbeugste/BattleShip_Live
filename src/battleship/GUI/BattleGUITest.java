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
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.*;

/**
 *
 * @author Patrik Buholzer
 */
public class BattleGUITest extends javax.swing.JFrame {
    
    //private Buttonlistener bl = new Buttonlistener(this);
    private static javax.swing.JPanel panelPlayer;
    private static javax.swing.JPanel panelOponent;
    private javax.swing.JLabel label;
    public Buttonlistener bl;
     
    
    public BattleGUITest () {
        super("Battleship");
        bl = new Buttonlistener(this);
        createWindow();
        //createPlayGUI();
        // darstellen:
        super.setVisible(true);
    }
    
    /**
     * PB
     * Creates the MainWindow
     */
    public void createWindow() {
        super.setSize(800,580);
        super.setResizable(false);
        super.setLocation(200,100);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setJMenuBar(createMenuBar());
        JPanel mainGrid = new JPanel (new FlowLayout());
        mainGrid.setBackground(Color.white);
        super.add(mainGrid);
        label = new JLabel("Start");
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
        myButton.setIcon(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\Versenken\\src\\water.jpg"));
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
        return panelPlayer;
    }

    public JPanel setPanelOpponent () 
    {
        this.panelOponent = createPanel(10,10);
        return panelOponent;
    }   
    
        public JPanel setShip ()
    {
        JPanel ShipPanel = new JPanel (new GridLayout(2,0));   
       
        JButton bship = new JButton("Kreuzer");
        
        bship.addActionListener(bl);
        bship.setBackground(Color.white);
        bship.setBorder (null);

        JButton cruiser = new JButton ("Schlachtschiff");
        cruiser.setBorder (null);
        cruiser.setBackground(Color.white);
        
        JButton destroyer = new JButton ("Zerstörer");
        destroyer.setBorder (null);
        destroyer.setBackground (Color.white);
        
        JButton uboot = new JButton("UBoot");
        uboot.setBorder (null);
        uboot.setBackground(Color.white);
                
        bship.setIcon (new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\Kreuzer.jpg"));
        cruiser.setIcon (new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\Schlachtschiff.jpg"));
        destroyer.setIcon(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\Zerstörer.jpg"));
        uboot.setIcon(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\UBoot.jpg"));

        ShipPanel.add(bship);
        ShipPanel.add(cruiser);
        ShipPanel.add(destroyer);
        ShipPanel.add(uboot);
        
  
        return ShipPanel;      
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
    

    
    public void help() throws Exception
    {
                Runtime rt= Runtime.getRuntime();
                String file ="C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\Game_Instruction.txt";
                Process p = rt.exec ("Notepad  "+ file);
    } 
    
    public void SinglePlayer() {
        BattleGUITest test = new BattleGUITest ();
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
                button.setIcon(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\fire.jpg"));
                setLabel("Daneben!");
                break;
            // Treffer:
            case 1:
                button.setEnabled(false);
                button.setIcon(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\fire.jpg"));
                setLabel("Treffer!");
                break;
            // Versenkt:
            case 2:
                button.setEnabled(false);
                button.setIcon(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\fire.jpg"));
                setLabel("Versenkt!");
                break;
            // Gameover:
            case 3:
                button.setEnabled(false);
                button.setIcon(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleGui\\src\\fire.jpg"));
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
     * Sets the text in the label of the GUI
     * @param s 
     */
    public void setLabel(String s) {
        label.setText(s);
    }
   
}
