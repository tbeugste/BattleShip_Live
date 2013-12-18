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
import java.io.File;

/**
 *
 * @author User
 */
public class StartScreen {

public StartScreen() 
 { 
        
        final JFrame frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        frame.setLocation(200,100);
        frame.setResizable(false);
        
        JPanel panelHome = new JPanel (new FlowLayout(0,0,0));
 
        JPanel panel = new JPanel (new GridLayout (1,2));
       
        JLabel title = new JLabel (new ImageIcon(getClass().getResource("pictures"+File.separator+"Title.jpg")));
        
        JPanel panelButton = new JPanel (new GridLayout(3,1,1,10));
        panelButton.setBackground(Color.white);
        JPanel panelLabel = new JPanel (new GridLayout (2,1,1,50));
        panelLabel.setBackground(Color.white);    
                
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("pictures"+File.separator+"TitleImage.jpg")));
        JLabel label = new JLabel ("Loana Albisser, Patrik Buholzer, Sascha Herger, Andreas Eugster");           
                     
        panelLabel.add(imageLabel);
        panelLabel.add(label);
                  
        JButton multiplayer = new JButton(new ImageIcon(getClass().getResource("pictures"+File.separator+"MultiIcon.jpg"))); 
        multiplayer.setBackground(Color.WHITE);
        JButton singleplayer = new JButton(new ImageIcon(getClass().getResource("pictures"+File.separator+"SingleIcon.jpg"))); 
        
        singleplayer.setBackground(Color.WHITE);
        JButton options = new JButton(new ImageIcon(getClass().getResource("pictures"+File.separator+"OptionIcon.jpg"))); 

        options.setBackground(Color.WHITE);
 
        panelButton.add(multiplayer); 
        multiplayer.addActionListener(new ActionListener() 
        {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {    
                try{
                MulitScreen.createWindow();
                //Battleship.bGUI.setVisible(true);
                //Battleship.bField.initializeServer(2);
                } catch(Exception ex ) {
                    
                }
                frame.setVisible(false);

            }
        });
        multiplayer.setActionCommand("multiplayer");
        
        panelButton.add (singleplayer);
        singleplayer.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {           
                try{
                Battleship.bGUI.setVisible(true);
                Battleship.bField.initializeServer(1);
                } catch(Exception ex ) {
                    
                }
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

