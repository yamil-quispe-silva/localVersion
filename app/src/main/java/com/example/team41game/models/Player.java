package com.example.team41game.models;

public class Player {
    private String name;
    private int health;
    private int avatarId;
    private static volatile Player player;
    private int score;
    private String startTime;

    private Player() { }

    public static Player getPlayer() {
        if (player == null) {
            synchronized (Player.class) {
                if (player == null) {
                    player = new Player();
                }
            }
        }
        return player;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getAvatarId() {
        return this.avatarId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

}
