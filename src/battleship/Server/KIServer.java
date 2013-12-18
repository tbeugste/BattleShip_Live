/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Server;
import battleship.engine.CommunicationObject;
import battleship.engine.CommunicationObjectType;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

/**
 *
 * @author Andy
 * This class is responsible for all actions based on Communication between player and KI.
 * KICommunicator <> KI Server <> ClientCommunicator
 */
public class KIServer extends Thread implements IServer{
    
 InetAddress TCPServer;
    // port to talk over
    int portNumber = 9999;
    // the socket for the communication to happen on
    ServerSocket serverSocket;
    //max Connections
    int maxServerConnections = 1;
    //List of ClientConnections
    private Hashtable<Socket, ObjectOutputStream> allCommunicators = new Hashtable<Socket, ObjectOutputStream>();
        //Counter to know if both are ready to start
    private ArrayList<Socket> readyPlayers = new ArrayList<Socket>();
    private int starterID = -1;
    private boolean started= false;
    KICommunicator _kiCommunicator;
    /* 
     * Setup the server socket communication 
     */
    public KIServer() {
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
                if(allCommunicators.size()<maxServerConnections)
                {
                    //getClient socket
                    Socket client = serverSocket.accept();
                    //Get ObjectOutputStream to write to the client
                    ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                    //Store both informations
                    allCommunicators.put(client, oos);
                    //Start new Threads
                    new ClientCommunicator(this, client);
                    if(allCommunicators.size()==maxServerConnections)
                    {
                        started = true;
                        //Start initializing Game
                        CommunicationObject communicationObject = new CommunicationObject(CommunicationObjectType.INITIALIZE);
                        communicationObject.setConnected(true);
                        sendToAll(communicationObject);
                        _kiCommunicator = KICommunicator.getInstance();
                        _kiCommunicator.regame();
                    }
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
     * method to send to opponend
     * @param message
     * @param mySocket 
     */
    public void sendToOpponend(CommunicationObject message, Socket mySocket)
    {
        //make sure no problems with Multithreading
        synchronized(allCommunicators)
        {
            try{
                
                if(mySocket != null)
                {
                    //Send from a player so it sends to KI and get returned the message
                    message =_kiCommunicator.sendMessage(message);
                    //KI returnmessage get send to player
                    //Create Enumeration with keys
                    Enumeration e = allCommunicators.keys();
                    //go threw all keys
                    
                    //get opponends outputstream
                    ObjectOutputStream os = (ObjectOutputStream)(allCommunicators.get(mySocket));
                    //send message
                    os.writeObject(message);
                    //make sure its send
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
     * Method witch returns the starting Message -> Player will start every time
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

