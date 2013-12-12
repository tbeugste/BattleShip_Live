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
    IServer _server;
    
    public ClientCommunicator(IServer server, Socket client)
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
                                actionsOnInitializeMessage(message);
                                break;
                            case REPLY:
                                actionsOnReplayMessage(message);
                                break;
                            case SHOT:
                                actionsOnShotMessage(message);
                                break;
                            case START:
                                actionsOnStartMessage(message);
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
    private void actionsOnInitializeMessage(CommunicationObject message)
    {
        if(!_server.isStarted() && message.getInitialized())
        {
            _server.initialized(_client);

            while(true)
            {
                CommunicationObject cco = _server.getStarted(_client);
                if(cco!=null)
                {
                    try{
                    ObjectOutputStream oos = new ObjectOutputStream(_client.getOutputStream());
                    oos.writeObject(cco);
                    oos.flush();
                    }
                    catch(IOException ie)
                    {
                        System.out.println("Error with sending Start Object");
                        ie.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
    /**
     * Method with all Actions to do if a ReplayMessage appears
     */
    private void actionsOnReplayMessage(CommunicationObject message)
    {
        _server.sendToOpponend(message, _client);
    }
    /**
     * Method with all Actions to do if a ShotMessage appears
     */
    private void actionsOnShotMessage(CommunicationObject message)
    {
        _server.sendToOpponend(message, _client);
    }
    /**
     * Method with all Actions to do if a StartMessage appears
     */
    private void actionsOnStartMessage(CommunicationObject message)
    {
      //at the moment wount be send from a client   
    }
    
}
