/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.net.Socket;

/**
 *
 * @author Andy
 */
public interface IServer {
    public boolean isStarted();   
    public void initialized(Socket client);
    public CommunicationObject getStarted(Socket client);
    public void sendToOpponend(CommunicationObject message, Socket client);
}
