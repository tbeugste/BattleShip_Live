/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Server;
import battleship.GUI.BattleGUITest;
import battleship.engine.CommunicationObject;
import battleship.engine.CommunicationObjectType;
import battleship.engine.IListener;
import battleship.Battleship;
import battleship.engine.TCPClient;

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
