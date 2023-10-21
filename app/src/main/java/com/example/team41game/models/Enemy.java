package com.example.team41game.models;

import com.example.team41game.Position;

public class Enemy {
    private int type; // 1 = bies, 2 = zombie, 3 = orc, 4 = goo
    private Position position;
    //also have movePattern to be able to move specific direction

    public Enemy(int type, int x, int y) {
        this.type = type;
        this.position = new Position(x, y);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
