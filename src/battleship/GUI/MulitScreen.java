/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.GUI;

import java.awt.Color;
import java.awt.TextArea;
import static java.awt.TextArea.SCROLLBARS_NONE;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class MulitScreen {
    
     public static void createWindow()
    {
        JFrame frame = new JFrame ();
        frame.setSize(400,250);
       
        JPanel panel = new JPanel ();
        panel.setBackground(Color.white);
        
        JLabel label = new JLabel ("Bitte IP- Adresse eingeben:");
        TextField ipadresse = new TextField (20);
        TextArea area = new TextArea ("",5,50, SCROLLBARS_NONE);
        JButton button = new JButton ("Verbinden");
        
        frame.add(panel);
        
        panel.add(label);
        panel.add(ipadresse);
        panel.add(area);
        panel.add(button);
        
        frame.setResizable(false);
        frame.setVisible (true);   
    }
}