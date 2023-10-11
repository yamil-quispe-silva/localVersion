package com.example.team41game.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.team41game.models.GameConfig;
import com.example.team41game.models.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameScreen1ViewModel extends ViewModel {
    private GameConfig gameConfig = GameConfig.getGameConfig();
    private Player player = Player.getPlayer();

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
}
