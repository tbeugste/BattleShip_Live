/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;
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
        new Thread( this ).start();
        } catch( IOException ie ) { System.out.println( ie ); }         
    }
    
    public void run()
    {
        //TODO: Communication with Battlefield to send the message
    }
    
    public void sendToServer(CommunicationObjectType message)
    {
        try{
        oos.writeObject(message);
        }
        catch(IOException ie)
        {
            System.out.println("Fehler beim versenden an den Server");
            ie.printStackTrace();
        }
    
    }
    
    public void addActionListener(IListener iListener) {
        allListeners.add(iListener);
    }
    
    
    
}
