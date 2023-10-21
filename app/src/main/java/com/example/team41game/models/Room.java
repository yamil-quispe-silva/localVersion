package com.example.team41game.models;

import java.util.List;
import java.util.ArrayList;

public class Room {
    private List<Enemy> enemies;
    private int[][] floorLayout;
    //add door, chests, etc. later

    public Room(int[][] floorLayout) {
        enemies = new ArrayList<>();
        this.floorLayout = floorLayout;
    }

    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public boolean removeEnemy(Enemy enemy) {
        return this.enemies.remove(enemy);
    }

    //use this to access floor plan in view model bounds checks
    public int[][] getFloorLayout() {
        return this.floorLayout;
    }
}
