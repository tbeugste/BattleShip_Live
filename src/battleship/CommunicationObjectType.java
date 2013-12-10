/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;

/**
 *
 * @author Andy
 */
public enum CommunicationObjectType {
    INITIALIZE(1), SHOT(2), REPLY(3), START(4);
    
    private final int id;
    CommunicationObjectType(int id) { this.id = id; }
    public int getValue() { return id; }
}
