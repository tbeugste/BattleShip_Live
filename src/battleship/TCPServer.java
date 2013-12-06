/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Andreas Eugster
 */
public class TCPServer implements Runnable {
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
    ArrayList<Socket> allCommunicators = new ArrayList<Socket>();
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
    }

    
    @Override
    public void run()
    {
        try{
        
            while(running)
            {
                if(allCommunicators.size()<2 && !started)
                {
                    Socket client = serverSocket.accept();
                    allCommunicators.add(client);
                    new Thread(new ClientCommunicator(allCommunicators, allCommunicators.indexOf(client), serverSocket)).start();
                    if(allCommunicators.size()==2)
                    {
                        started = true;
                        //Start initializing Game
                        ObjectOutputStream os;
                        CommunicationObject communicationObject = new CommunicationObject(CommunicationObjectType.INITIALIZE,false);
                        for(Socket players: allCommunicators)
                        {
                            os = new ObjectOutputStream(players.getOutputStream());
                            os.writeObject(communicationObject);
                            os.flush();
                            os.close();
                        }
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
    
    public void stop()
    {
        running = false;
    }
}
