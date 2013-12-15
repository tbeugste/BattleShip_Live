/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.Server;
import battleship.engine.*;
import java.util.ArrayList;
/**
 * This class is responsible to interact with the player. It moves and acts like one.
 * @author Andy
 */
public class KICommunicator {
    /*
    Singleton Construct
    -----------------------------
    */
    private static KICommunicator _instance;
    private KICommunicator ()
    {
        //TODO Create Ships
    }
    public static KICommunicator getInstance()
    {
        if(_instance == null)
        {
            _instance = new KICommunicator();
        }
        return _instance;
    }
    
    /*
    ----------------------------
    */
    
    /**
     * local Variables ----------
     */
    private ArrayList<Ship> _fleet = new ArrayList<>();
    
    
    /**
    * -------------------------->
    */
    /**
    * Method to create Ships on the battlefield
    */
    private void setRandomShips()
    {}
     
    /**
      * Method to reset all local variables
      */
    private void resetLocalVariables(){}
    
    /**
     * Method to reset the game. Set new ships and reset all local variables
     */
    public void regame()
    {}
    
    /**
     * apply Message to the KI
     * @param message 
     */
    public CommunicationObject applyMessage(CommunicationObject message){return message;}
    
    private CommunicationObject shotMessage(CommunicationObject message){return message;}
    
    private CommunicationObject replyMessage(CommunicationObject message){return message;}
    
    
}
