package com.example.team41game.models;

import com.example.team41game.Position;

//for later use
public class Door {
    private boolean locked;
    private Position position;
    public Door(boolean locked, int x, int y) {
        this.locked = locked;
        this.position = new Position(x, y);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
