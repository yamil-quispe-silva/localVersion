package com.example.team41game.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.team41game.models.GameConfig;
import com.example.team41game.models.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.team41game.Subscriber;
import com.example.team41game.MovePattern;
import com.example.team41game.MoveLeft;
import com.example.team41game.MoveRight;
import com.example.team41game.MoveUp;
import com.example.team41game.MoveDown;
import java.util.List;
import java.util.ArrayList;

import com.example.team41game.models.Enemy;
import com.example.team41game.models.Room;
import com.example.team41game.Position;

public class GameScreenViewModel extends ViewModel {
    private GameConfig gameConfig;
    private Player player;
    private Room room;
    private List<Subscriber> subscribers = new ArrayList<>();

    public GameScreenViewModel() {
        gameConfig = GameConfig.getGameConfig();
        player = Player.getPlayer();
    }

    public void updateScore() {
        int newScore = player.getScore() - 1;

        // Ensure the player's score doesn't go below 0
        if (newScore < 0) {
            player.setScore(0);
        } else {
            player.setScore(newScore);
        }
    }

    public void initPlayerAttempt() {
        player.setScore(100);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String startTime = dateFormat.format(date);
        player.setStartTime(startTime);
    }

    public String getPlayerName() {
        return "Name: " + player.getName();
    }

    public String getHealth() {
        return "Health: " + player.getHealth();
    }

    public int getPlayerAvatar() {
        return player.getAvatarId();
    }

    public String getDifficulty() {
        switch (gameConfig.getDifficulty()) {
        case 0:
            return "Difficulty: Easy";
        case 1:
            return "Difficulty: Medium";
        case 2:
            return "Difficulty: Hard";
        default:
            return "Difficulty: ...";
        }
    }

    public String getScore() {
        return String.format("Score: %d", player.getScore());
    }

    public int getPlayerX() {
        return player.getPosition().getX();
    }

    public int getPlayerY() {
        return player.getPosition().getY();
    }

    //set player's starting position in new screen
    public void initPlayerPosition(int x, int y) {
        player.setPosition(new Position(x, y));
    }

    // set floor layout for current room
    public void initRoom(int[][] layout) {
        room = new Room(layout);
    }

    public void addEnemy(int type, int x, int y) {
        room.addEnemy(new Enemy(type, x, y));
    }

    public int[] getEnemyTypes() {
        int[] types = new int[room.getEnemies().size()];
        for (int i = 0; i < types.length; i++) {
            types[i] = room.getEnemies().get(i).getType();
        }
        return types;
    }

    public int[] getEnemyX() {
        int[] x = new int[room.getEnemies().size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = room.getEnemies().get(i).getPosition().getX();
        }
        return x;
    }

    public int[] getEnemyY() {
        int[] y = new int[room.getEnemies().size()];
        for (int i = 0; i < y.length; i++) {
            y[i] = room.getEnemies().get(i).getPosition().getY();
        }
        return y;
    }

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers() {
        for (Subscriber s: subscribers) {
            s.update(this);
        }
    }

    public void setPlayerMovePattern(MovePattern movePattern) {
        player.setMovePattern(movePattern);
    }

    //return true if player is on door
    public boolean doPlayerMove() {
        // do other movement checks here
        if (inScreenLimit() && checkWallCollisions()) {
            player.doMove();
            notifySubscribers();
        }
        return checkForDoor();
    }
  
    // Check if the player has reached a door.
    public boolean checkForDoor() {
        int[][] layout = room.getFloorLayout();
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        // Check if the player's new position corresponds to a door (6 or 7) in the layout.
        return (layout[y][x] == 6) || (layout[y][x] == 7);
    }

    public void setPlayerWinStatus(boolean winStatus) {
        player.setWinStatus(winStatus);
    }
  
    public boolean checkWallCollisions() {
        int nextX = getPlayerX();
        int nextY = getPlayerY();
        if (player.getMovePattern() instanceof MoveLeft) {
            nextX--;
        } else if (player.getMovePattern() instanceof MoveRight) {
            nextX++;
        } else if (player.getMovePattern() instanceof MoveUp) {
            nextY--;
        } else if (player.getMovePattern() instanceof MoveDown) {
            nextY++;
        }
        int nextTile = room.getFloorLayout()[nextY][nextX];
        return (nextTile != 1) && (nextTile != 3) && (nextTile != 4) && (nextTile != 5);
    }
  
    public boolean inScreenLimit() {
        //check that player does not move off game screen, based on game bitmap sizes
        if (player.getMovePattern() instanceof MoveLeft) {
            return player.getPosition().getX() - 1 >= 0;
        } else if (player.getMovePattern() instanceof MoveRight) {
            return player.getPosition().getX() + 1 < room.getFloorLayout()[0].length;
        } else if (player.getMovePattern() instanceof MoveUp) {
            return player.getPosition().getY() - 1 >= 0;
        } else if (player.getMovePattern() instanceof MoveDown) {
            return player.getPosition().getY() + 1 < room.getFloorLayout().length;
        }
        return false;
    }
}
