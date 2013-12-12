/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Patrik
 */
public interface IListener {
    
    public void shotMessage(CommunicationObject message);
    public void replyMessage(CommunicationObject message);
    public void startMessage(CommunicationObject message);
    public void initializeMessage(CommunicationObject message);
    
}
