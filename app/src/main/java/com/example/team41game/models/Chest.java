package com.example.team41game.models;

import com.example.team41game.Position;

//for later use
public class Chest {
    private int contents;
    private boolean destroyed;
    private Position position;

    public Chest(int contents, int x, int y) {
        this.contents = contents;
        this.destroyed = false;
        this.position = new Position(x, y);
    }

    public int getContents() {
        return this.contents;
    }

    public void setContents(int contents) {
        this.contents = contents;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
