/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author Andreas Eugster
 */
public class TCPServer {
    // the IP class as such
    InetAddress TCPServerIP;
    // port to talk over
    int portNumber = 9999;
    // the socket for the communication to happen on
    ServerSocket theServerSocket;

    /* 
     * Setup the server socket communication 
     */
    public TCPServer() {
            try {
                    // create the server socket on the port number
                    theServerSocket = new ServerSocket(portNumber);
                    System.out.println("Server created on port : "+portNumber);
            } catch (IOException ExecIO)
            {
                    System.out.println("Error creating the server socket : "+ExecIO.getMessage());
            }
    }


    /**
     * Method witch listens to external connections
     */
    public void listenToConnection()
    {
            try {
                    Socket sock = theServerSocket.accept();
                    System.out.println("Server accepted connection, send to handler");

                    // print out the clients IP Address
                    System.out.println("The client IP address is " + sock.getInetAddress());

                    // send the message to the client
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                    System.out.println("Server socket opened");
                    oos.writeChars("Hi from the server");
                    oos.close();
               

            } catch  (IOException ExecIO)
            {
                    System.out.println("Error creating connection : "+ExecIO.getMessage());
            }
    }

}
