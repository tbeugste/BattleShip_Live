/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;


/**
 *
 * @author Andreas Eugster
 */
public class TCPServer extends Thread implements IServer {
    // the IP class as such
    private InetAddress TCPServer;
    // port to talk over
    private int portNumber = 9999;
    // the socket for the communication to happen on
    private ServerSocket serverSocket;
    //max Connections
    private int maxServerConnections = 2;
    //List of ClientConnections
    private Hashtable<Socket, ObjectOutputStream> allCommunicators = new Hashtable<Socket, ObjectOutputStream>();
        //Counter to know if both are ready to start
    private ArrayList<Socket> readyPlayers = new ArrayList<Socket>();
    private int starterID = -1;
    private boolean started= false;
    /* 
     * Setup the server socket communication 
     */
    public TCPServer() {
        try{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server created on port: "+portNumber);
        }
        catch(Exception ExecIO)
        {
            System.out.println("Error creating server on socket: "+ExecIO.getMessage());
        }
        start();
    }

    /**
     * Method is started uppon calling start();
     */
    @Override
    public void run()
    {
        try{
        
            while(true)
            {
                if(allCommunicators.size()<2 && !started)
                {
                    //getClient socket
                    Socket client = serverSocket.accept();
                    //Get ObjectOutputStream to write to the client
                    ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                    //Store both informations
                    allCommunicators.put(client, oos);
                    //Start new Threads
                    new ClientCommunicator(this, client);
                    if(allCommunicators.size()==2)
                    {
                        started = true;
                        //Start initializing Game
                        CommunicationObject communicationObject = new CommunicationObject(CommunicationObjectType.INITIALIZE);
                        communicationObject.setConnected(true);
                        sendToAll(communicationObject);
                    }
                }
                else if(allCommunicators.size()<2 && started)
                {
                    //TODO: RECONNECT
                }
            }
            
        }
        catch(IOException ex)
        {
            System.out.println("er1: Interrupt Network Server");
        }
    }
    
    /**
     * Create Enumeration to iterate threw all OutputStreams
     * @return 
     */
    Enumeration getOutputStreams()
    {
        return allCommunicators.elements();
    }
    
    /**
     * method to send a Message to all Clients
     * @param message 
     */
    public void sendToAll(CommunicationObject message)
    {
        //lock to one access per Thread
        synchronized(allCommunicators)
        {
            try{
                //Iterate threw all Objects
                for(Enumeration e=getOutputStreams(); e.hasMoreElements();)
                {
                    //get Outputstream
                    ObjectOutputStream os = (ObjectOutputStream)e.nextElement();
                    //send Object
                    os.writeObject(message);
                    //make sure it sends
                    os.flush();
                }
            }
            catch(IOException ie)
            {
                ie.printStackTrace();
            }
        }
    }
    
    /**
     * method to send to oppondend
     * @param message
     * @param mySocket 
     */
    public void sendToOpponend(CommunicationObject message, Socket mySocket)
    {
        //make sure no problems with Multithreading
        synchronized(allCommunicators)
        {
            try{
                //Create Enumeration with keys
                Enumeration e = allCommunicators.keys();
                //go threw all keys
                while(e.hasMoreElements())
                {
                    //if the key is not the same as the one from the sender its the opponends
                    if(!e.nextElement().equals(mySocket))
                    {
                        //get opponends outputstream
                        ObjectOutputStream os = (ObjectOutputStream)(allCommunicators.get(e));
                        //send message
                        os.writeObject(message);
                        //make sure its send
                        os.flush();
                        //stop while
                        break;
                    }
                }
            }
            catch(IOException ie)
            {
                ie.printStackTrace();
            }
        }
    }
    
    /**
     * remove a particular connection
     * @param client 
     */
    public void removeConnection(Socket client)
    {
        //make sure there are no problems with multithreading
        synchronized(allCommunicators)
        {
            System.out.println("Disconnect Client "+client);
            //Remove from Hashtable
            allCommunicators.remove(client);
            
            try{
                //make sure connection from soket is closed and with it all ressources reliable to..
                client.close();
            }
            catch(IOException ie)
            {
                System.out.println("Error closing connection to "+client);
                ie.printStackTrace();
            }
            
        }
    }
    
    public void initialized(Socket client)
    {
        synchronized(readyPlayers)
        {
            if(!readyPlayers.contains(client))
            {
                readyPlayers.add(client);
            }
        }
    }
    
    /**
     * Method witch returns the starting Message
     * @return 
     */
    public CommunicationObject getStarted(Socket client)
    {
        CommunicationObject message = null;
        if(readyPlayers.size() == maxServerConnections)
        {
            if(starterID==-1)
            {
                Random r = new Random();
                starterID = r.nextInt(maxServerConnections);
                
            }
            
            for(int i=0; i<=readyPlayers.size();i++ )
            {
                if(readyPlayers.get(i).equals(client))
                {
                    message = new CommunicationObject(CommunicationObjectType.START);
                    message.setInitialized(true);
                    if(i==starterID)
                    {
                        message.setStarted(true);
                    }
                    else
                    {
                        message.setStarted(false);
                    }
                }
            }

        }
        return message;
    }
    
    public boolean isStarted()
    {
        return (readyPlayers.size()==maxServerConnections);
    }
    
}
