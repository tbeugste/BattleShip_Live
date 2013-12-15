package battleship.GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import battleship.Battleship;
import battleship.engine.Battlefield;

/**
 *
 * @author User
 */
public class StartScreen {

public StartScreen() 
 { 
        
        final JFrame frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        
        frame.setResizable(false);
        
        JPanel panelHome = new JPanel (new FlowLayout(0,0,0));
 
        JPanel panel = new JPanel (new GridLayout (1,2));
       
        JLabel title = new JLabel (new ImageIcon(getClass().getResource("Title.jpg")));
        
        JPanel panelButton = new JPanel (new GridLayout(3,1,1,10));
        panelButton.setBackground(Color.white);
        JPanel panelLabel = new JPanel (new GridLayout (2,1,1,50));
        panelLabel.setBackground(Color.white);    
                
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("TitleImage.jpg")));
        JLabel label = new JLabel ("Loana Albisser, Patrik Buholzer, Sascha Herger, Andreas Eugster");           
                     
        panelLabel.add(imageLabel);
        panelLabel.add(label);
                  
        JButton multiplayer = new JButton(new ImageIcon(getClass().getResource("MultiIcon.jpg"))); 
        multiplayer.setBackground(Color.WHITE);
        JButton singleplayer = new JButton(new ImageIcon(getClass().getResource("SingleIcon.jpg"))); 
        
        singleplayer.setBackground(Color.WHITE);
        JButton options = new JButton(new ImageIcon(getClass().getResource("OptionIcon.jpg"))); 

        options.setBackground(Color.WHITE);
 
        panelButton.add(multiplayer); 
        multiplayer.addActionListener(new ActionListener() 
        {
 
            public void actionPerformed(ActionEvent e)
            {    
                Battleship.bGUI = new BattleGUI();
                Battleship.bField = new Battlefield(Battleship.bGUI,10,10,2);
                
                frame.setVisible(false);

            }
        });
        multiplayer.setActionCommand("multiplayer");
        
        panelButton.add (singleplayer);
        singleplayer.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {           
                Battleship.bGUI = new BattleGUI();
                Battleship.bField = new Battlefield(Battleship.bGUI,10,10,1);
                frame.setVisible(false);
            }
        });
        singleplayer.setActionCommand("singleplayer");
        
        panelButton.add (options);
        panelHome.add(title);
        panelHome.add (panel);
        
        panel.add(panelLabel);
        panel.add(panelButton);

        frame.add(panelHome);   
        frame.pack();         
        frame.setSize(748,425);
        frame.setVisible(true);  
 } 




}

