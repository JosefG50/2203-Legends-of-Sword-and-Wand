package com.github.domain;

public class RoomFactory {

    public Room CreateNextRoom(float battlechance) {
        //TODO: Add input to constructors?
        if (Math.random() < battlechance) {
            return new Battle();
        } else {
            return new InnTest();
        }
    }
}
