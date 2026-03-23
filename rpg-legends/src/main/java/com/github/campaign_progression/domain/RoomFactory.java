
package com.github.campaign_progression.domain;

public class RoomFactory {

    public Room createNextRoom(float battlechance) {
        //TODO: Add input to constructors?
        if (Math.random() < battlechance) {
            return new BattleRoom();
        } else {
            return new Inn();
        }
    }
}
