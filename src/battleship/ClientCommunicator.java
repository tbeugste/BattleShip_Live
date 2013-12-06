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
public class ClientCommunicator implements Runnable{
    ArrayList<Socket> _allCommunicators;
    ServerSocket _serverSocket;
    int _actClient;
    
    public ClientCommunicator(ArrayList<Socket> allCommunicators, int actClient, ServerSocket serverSocket)
    {
        _allCommunicators = allCommunicators;
        _serverSocket = serverSocket;
        _actClient = actClient;
    }
    
    public void run()
    {
        
    }
    
    
}
