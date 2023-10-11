package com.example.team41game.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.team41game.models.GameConfig;
import com.example.team41game.models.Player;

public class GameScreen3ViewModel extends ViewModel {
    private GameConfig gameConfig;
    private Player player;

    public GameScreen3ViewModel() {
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
