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
import javax.swing.JMenuBar;
import javax.swing.JPanel;


/**
 *
 * @author User
 */
public class StartScreen {
    
  

 
public StartScreen() 
 { 
        
        

        JFrame frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        
        frame.setResizable(false);
        
        JPanel panelHome = new JPanel (new FlowLayout(0,0,0));
 
        JPanel panel = new JPanel (new GridLayout (1,2));
       
        JLabel title = new JLabel (new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleShip_Live\\src\\battleship\\GUI\\Title.JPG"));
        
        JPanel panelButton = new JPanel (new GridLayout(3,1,1,10));
        panelButton.setBackground(Color.white);
        JPanel panelLabel = new JPanel (new GridLayout (2,1,1,50));
        panelLabel.setBackground(Color.white);    
                
        JLabel imageLabel = new JLabel(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleShip_Live\\src\\battleship\\GUI\\TitleImage.jpg"));
        JLabel label = new JLabel ("Loana Albisser, Patrik Buholzer, Sascha Herger, Andreas Eugster");           
                     
        panelLabel.add(imageLabel);
        panelLabel.add(label);
                  
        JButton multiplayer = new JButton(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleShip_Live\\src\\battleship\\GUI\\MultiIcon.jpg")); 
        multiplayer.setBackground(Color.WHITE);
        JButton singleplayer = new JButton(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleShip_Live\\src\\battleship\\GUI\\SingleIcon.jpg")); 
        
        singleplayer.setBackground(Color.WHITE);
        JButton options = new JButton(new ImageIcon("C:\\Loana\\Schule\\TA.BA_PRG2.H1301\\BattleShip_Live\\src\\battleship\\GUI\\OptionIcon.jpg")); 

        options.setBackground(Color.WHITE);
 
        panelButton.add(multiplayer); 
        multiplayer.addActionListener(new ActionListener() 
        {
 
            public void actionPerformed(ActionEvent e)
            {           
                BattleGUITest test = new BattleGUITest ();

            }
        });
        multiplayer.setActionCommand("multiplayer");
        
        panelButton.add (singleplayer);
        singleplayer.addActionListener(new ActionListener() 
        {
 
            public void actionPerformed(ActionEvent e)
            {           
                BattleGUITest test = new BattleGUITest ();
                
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

public static void SinglePlayer()
{
    
}
{

}
}
