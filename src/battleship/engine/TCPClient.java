/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.engine;
import java.util.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 *
 * @author Andreas Eugster
 * This class interact with BusinessLogic and GameServer
 */
public class TCPClient implements Runnable{
    //Server Connection
    private Socket socket;
    
    //Streams to and from the server
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ArrayList<IListener> allListeners = new ArrayList<>();
    
    public TCPClient(String host, int port)
    {
        // Connect to the server
    try {
        
          
        // Initiate the connection
        socket = new Socket( host, port );
        
        // Create ObjectStreams
        ois = new ObjectInputStream( socket.getInputStream() );
        oos = new ObjectOutputStream( socket.getOutputStream() );
        // Start a background thread for receiving messages
        Thread client = new Thread( this );
        client.setName("TCPClient");
        client.start();
        } catch( IOException ie ) { System.out.println( ie ); }         
    }
    
    public void run()
    {
        try{
            while(true)
            {
                CommunicationObject message = (CommunicationObject) ois.readObject();
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
                try{
                Thread.sleep(10);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        catch(IOException ie)
        {
                System.out.println(ie);
        }
        catch(ClassNotFoundException cnfe)
        {
            System.out.println(cnfe);
        }
        
    }
    
    /**
     * method to send a message to the server
     * @param message 
     */
    public synchronized void sendToServer(CommunicationObject message)
    {
        try{
            oos.writeObject(message);
            oos.flush();
        }
        catch(IOException ie)
        {
            System.out.println("Fehler beim versenden an den Server");
            ie.printStackTrace();
        }
    
    }
    /**
     * Add Listener to list
     * @param iListener 
     */
    public void addActionListener(IListener iListener) {
        allListeners.add(iListener);
    }
    
    /**
     * Inform all listeners and tell them the message
     * @param message 
     */
    public synchronized void actionsOnInitializeMessage(CommunicationObject message)
    {
        for(IListener listener: allListeners)
        {
            listener.initializeMessage(message);
        }
    }
    
    /**
     * Inform all listeners and tell them the message
     * @param message 
     */
    public synchronized void actionsOnReplayMessage(CommunicationObject message)
    {
        for(IListener listener: allListeners)
        {
            listener.replyMessage(message);
        }
    }
    /**
     * Inform all listeners and tell them the message
     * @param message 
     */
    public synchronized void actionsOnShotMessage(CommunicationObject message)
    {
        for(IListener listener: allListeners)
        {
            listener.shotMessage(message);
        }
    }
    /**
     * Inform all listeners and tell them the message
     * @param message 
     */
    public synchronized void actionsOnStartMessage(CommunicationObject message)
    {
        for(IListener listener: allListeners)
        {
            listener.startMessage(message);
        }
    }
}
