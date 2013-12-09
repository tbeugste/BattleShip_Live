/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 *
 * @author Andreas Eugster
 */
public class TCPServer extends Thread {
    //Flag to stop server
    boolean running = true;
    // the IP class as such
    InetAddress TCPServer;
    // port to talk over
    int portNumber = 9999;
    // the socket for the communication to happen on
    ServerSocket serverSocket;
    //max Connections
    int maxServerConnections = 2;
    //List of ClientConnections
    private Hashtable<Socket, ObjectOutputStream> allCommunicators = new Hashtable<Socket, ObjectOutputStream>();
    //Flag if game is started
    boolean started = false;
    
    /* 
     * Setup the server socket communication 
     */
    public TCPServer() {
            try {
                    // create the server socket on the port number
                    serverSocket = new ServerSocket(portNumber);
                    System.out.println("Server created on port : "+portNumber);
            } catch (IOException ExecIO)
            {
                    System.out.println("Error creating the server socket : "+ExecIO.getMessage());
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
    
}
