/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;
import java.io.*; 
import java.net.*;
import java.util.*;
/**
 *
 * @author Andy
 */
public class ClientCommunicator extends Thread{
    Socket _client;
    TCPServer _server;
    
    public ClientCommunicator(TCPServer server, Socket client)
    {
        _client = client;
        _server = server;
        start();
    }
    
    @Override
    public void run()
    {
        try{
        ObjectInputStream ois = new ObjectInputStream(_client.getInputStream());
        
            while(true)
            {
                try{
                    CommunicationObject message = (CommunicationObject)ois.readObject();
                    if(message != null)
                    {
                        switch(message.getType())
                        {
                            case INITIALIZE:
                                actionsOnInitializeMessage();
                                break;
                            case REPLY:
                                actionsOnReplayMessage();
                                break;
                            case SHOT:
                                actionsOnShotMessage();
                                break;
                            case START:
                                actionsOnStartMessage();
                                break;
                        }
                    }

                }
                catch(ClassNotFoundException cnfe)
                {
                    System.out.println("Incorect Object recived");
                    cnfe.printStackTrace();
                }
            }
        
        }
        catch(EOFException ie){}
        catch(IOException ie)
        {
            ie.printStackTrace();
        }
        
        finally
        {
            
        }
    }
    
    /**
     * Method with all Actions to do if a InitializeMessage appears
     */
    private void actionsOnInitializeMessage()
    {
        
    }
    /**
     * Method with all Actions to do if a ReplayMessage appears
     */
    private void actionsOnReplayMessage()
    {
        
    }
    /**
     * Method with all Actions to do if a ShotMessage appears
     */
    private void actionsOnShotMessage()
    {
        
    }
    /**
     * Method with all Actions to do if a StartMessage appears
     */
    private void actionsOnStartMessage()
    {
        
    }
    
}
