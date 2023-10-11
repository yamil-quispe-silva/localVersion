package com.example.team41game;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import com.example.team41game.models.GameConfig;
import com.example.team41game.models.Player;
import com.example.team41game.viewModels.ConfigScreenViewModel;
import com.example.team41game.viewModels.GameScreen1ViewModel;

/**
 * Tests 2D Dungeon Game logic.
 */
public class UnitTest {

    private ConfigScreenViewModel viewModel;

    private GameScreen1ViewModel gameViewModel;

    private GameConfig gameConfig;
    private int easyButtonId;
    private int mediumButtonId;

    private int hardButtonId;

    private Player player;

    @Before
    public void setup() {
        player = Player.getPlayer();
        gameConfig = GameConfig.getGameConfig();
        viewModel = new ConfigScreenViewModel();
        gameViewModel = new GameScreen1ViewModel();
        easyButtonId = 0;
        mediumButtonId = 1;
        hardButtonId = 2;
    }

    /**
     * Test for accurate setDifficulty method (Spring 1).
     */
    @Test
    public void testSetDifficultyMethod() {
        // Test for Difficulty Easy
        viewModel.setDifficulty(easyButtonId, easyButtonId, mediumButtonId);
        assertEquals(0, gameConfig.getDifficulty());

        // Test for Difficulty Medium
        viewModel.setDifficulty(mediumButtonId, easyButtonId, mediumButtonId);
        assertEquals(1, gameConfig.getDifficulty());

        //Test for Difficulty Hard
        viewModel.setDifficulty(hardButtonId, easyButtonId, mediumButtonId);
        assertEquals(2, gameConfig.getDifficulty());
    }

    /**
     * Test for accurate updateScore method (Spring 1).
     */
    @Test
    public void testUpdateScoreMethod() {
        player.setScore(100);
        gameViewModel.updateScore();
        assertEquals(99, player.getScore());

        player.setScore(0);
        gameViewModel.updateScore();
        assertEquals(0, player.getScore());
    }

}
