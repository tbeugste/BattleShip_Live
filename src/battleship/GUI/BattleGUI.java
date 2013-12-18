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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/** 
 *
 * @author Patrik Buholzer
 */
public class BattleGUI extends javax.swing.JFrame {
    
    private static javax.swing.JPanel panelPlayer;
    private static javax.swing.JPanel panelOponent;
    public Shiptypes ship;
    private javax.swing.JLabel label;
    private JComboBox shipNames;
    private Buttonlistener bl;
    private MyMouselistener ml;
    private boolean horizontal;
    private boolean panelPlayerSet = false;
     
    public BattleGUI () {
        super("Battleship");
        bl = new Buttonlistener(this);
        ml = new MyMouselistener(this);
        //entfernt, da dies erst erstellt werden soll, wenn die Verbindung zum Server aufgebaut wurde
        createWindow();
    }
    
    /**
     * PB
     * Creates the MainWindow
     */
    public void createWindow() {
        super.setSize(800,675);
        super.setResizable(false);
        super.setLocation(200,100);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setJMenuBar(createMenuBar());
        JPanel mainGrid = new JPanel  (new FlowLayout());
        mainGrid.setBackground(Color.white);
        super.add(mainGrid);
        
        label = new JLabel("Schiff wählen!");
        label.setHorizontalAlignment(JLabel.CENTER);
        
        
        JButton button = new JButton ("ready");
        button.addActionListener(bl);
        button.setActionCommand("GameReady");
        
                
        JPanel panel = new JPanel (new GridLayout(3,0));
        //panel.setBackground(Color.white);
        panel.add(setShip());
        panel.add(button);
        panel.add(label);
        
        
        setPanelPlayer();
        mainGrid.add(panelPlayer);
        setPanelOpponent();
        mainGrid.add(panelOponent);
        mainGrid.add(panel); 
                
        Battleship.bField.status.setShipPlacementactive(false);
        Battleship.bField.status.setStatus(false);
        
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
     * Creates a button
     * @param row
     * @param column
     * @return MyButton
     */
    public MyButton createButton(int row, int column) {
        MyButton myButton = new MyButton(row, column);
        myButton.addActionListener(bl);
        myButton.setActionCommand("Shot");
        if(panelPlayerSet) {
            myButton.addMouseListener(ml);
            myButton.setRolloverEnabled(true);
        }
        BufferedImage img = null;
        myButton.setIcon (new ImageIcon(getClass().getResource("pictures"+File.separator+"water.jpg")));
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
        panel.setPreferredSize(new Dimension(392,392));
        
        panel.setLayout(new GridLayout(height,width,1,1));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(panelPlayerSet){
                    Battleship.bField.buttonArray[j][i] = createButton(i,j);
                    panel.add(Battleship.bField.buttonArray[j][i]);           
                } else {
                    panel.add(createButton(i,j));
                }
            }
        }
        return panel;
    }
     
    /**
     * creates the player's panel
     * @return JPanel
     */
    public void setPanelPlayer () {
        panelPlayerSet = true;
        panelPlayer = createPanel(10,10);
        panelPlayer.setBorder(new TitledBorder("Home Field"));
        panelPlayer.setBackground(Color.white);
        panelPlayerSet = false;
    }
    
    /**
     * creates the oponent's panel
     * @return JPanel
     */
    public void setPanelOpponent () 
    {
        panelOponent = createPanel(10,10);
        panelOponent.setBorder(new TitledBorder("Opponent Field"));
        panelOponent.setBackground(Color.white);
        //deactivatePanel(panelOponent);
    } 
    
    /**
     * creates the JComboBox for the shipplacement
     * @return 
     */
    public JComboBox createJComboBox(){
        shipNames = new JComboBox();
        shipNames.addActionListener(bl);
        shipNames.setActionCommand("Ship selected");
        
        for(Shiptypes typ : Shiptypes.values()){
            for (int i = 0; i < (6 - typ.getValue()); i++) {
                shipNames.addItem(typ);
            }
        }
        
        return shipNames;
    }
    
    /**
     * removes an item from the combobox (needed after placement of the ship)
     * @param sTyp 
     */
    public void removeFromCombobox(Shiptypes sTyp){
        shipNames.removeItem(sTyp);
    }
    
    public void setShip(Shiptypes sTyp){
        Battleship.bField.setShip(sTyp);
    }
    
    public JPanel setShip ()
    {
        JPanel ShipPanel = new JPanel (new GridLayout(2,0));   
       
        JButton bship = new JButton("Battleship");
        bship.addActionListener(bl);
        bship.setActionCommand("Ship selected bship");
        bship.setBackground(Color.white);
        bship.setBorder (null);

        JButton cruiser = new JButton ("Cruiser");
        cruiser.addActionListener(bl);
        cruiser.setActionCommand("Ship selected cruiser");
        cruiser.setBorder (null);
        cruiser.setBackground(Color.white);
        
        JButton destroyer = new JButton ("Destroyer");
        destroyer.addActionListener(bl);
        destroyer.setActionCommand("Ship selected destroyer");
        destroyer.setBorder (null);
        destroyer.setBackground (Color.white);
        
        JButton submarine = new JButton("Submarine");
        submarine.addActionListener(bl);
        submarine.setActionCommand("Ship selected submarine");
        submarine.setBorder (null);
        submarine.setBackground(Color.white);
                
        bship.setIcon (new ImageIcon(getClass().getResource("pictures"+File.separator+"BShip.jpg")));
        cruiser.setIcon (new ImageIcon(getClass().getResource("pictures"+File.separator+"Cruiser.jpg")));
        destroyer.setIcon(new ImageIcon(getClass().getResource("pictures"+File.separator+"Destroyer.jpg")));
        submarine.setIcon(new ImageIcon(getClass().getResource("pictures"+File.separator+"Submarine.jpg")));

        ShipPanel.add(bship);
        ShipPanel.add(cruiser);
        ShipPanel.add(destroyer);
        ShipPanel.add(submarine);
        
  
        return ShipPanel;      
    }
    
    public void newGame() {
        super.setVisible(false);
        
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
                button.setIcon(new ImageIcon(getClass().getResource("pictures"+File.separator+"miss.jpg")));
                setLabel("Daneben!");
                break;
            // Treffer:
            case 1:
                button.setEnabled(false);
                button.setIcon(new ImageIcon(getClass().getResource("pictures"+File.separator+"fire.jpg")));
                setLabel("Treffer!");
                break;
            // Versenkt:
            case 2:
                button.setEnabled(false);
                if(ship == SUBMARINE)
                {
                    button.setIcon(new ImageIcon(getClass().getResource("pictures"+File.separator+"Submarine.jpg")));
                }
                if(ship == DESTROYER)
                {
                    button.setIcon(new ImageIcon(getClass().getResource("pictures"+File.separator+"Destroyer.jpg")));
                }
                if(ship == CRUISER)
                {
                    button.setIcon(new ImageIcon(getClass().getResource("pictures"+File.separator+"Cruiser.jpg")));
                }
                if(ship == BSHIP)
                {
                    button.setIcon(new ImageIcon(getClass().getResource("pictures"+File.separator+"BShip.jpg")));
                }
                setLabel("Versenkt!");
                break;
            // Gameover:
            case 3:
                button.setEnabled(false);
                for(Component c : panelOponent.getComponents()) {
                    if (c instanceof MyButton) {
                        if (c.isEnabled()) {
                            switchButton((MyButton)c, 0);
                        }                            
                    }
                }
                setLabel("Game Over!");
                break;
            // marks the button with a green border
            case 4:
                button.setBorder(BorderFactory.createLineBorder(Color.green, 2));
                break;
            // marks the button with a red border
            case 5:
                button.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                break;
            // removes the border from case 4.
            case 6:
                button.setBorder(null);           
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
                    return (MyButton)c;
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
        return panelPlayer;
    }
    
    /**
     * PB
     * returns the JPanel of the Oponent
     * @return JPanel
     */
    public JPanel getPanelOponent() {
        return panelOponent;
    }
    
    /**
     * PB
     * activates all buttons in a panel
     * @param panel 
     */  
    public void activatePanel(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof MyButton) {
                c.setEnabled(true);
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
                c.setEnabled(false);
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
