package com.example.team41game;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.team41game.models.GameConfig;
import com.example.team41game.models.Leaderboard;
import com.example.team41game.models.Player;
import com.example.team41game.viewModels.ConfigScreenViewModel;
import com.example.team41game.viewModels.GameScreenViewModel;

/**
 * Tests 2D Dungeon Game logic.
 */
public class UnitTest {

    private ConfigScreenViewModel configScreenViewModel;
    private GameConfig gameConfig;
    private int easyButtonId;
    private int mediumButtonId;
    private int hardButtonId;
    private Player player;
    private GameScreenViewModel gameScreenViewModel;

    @Before
    public void setup() {
        player = Player.getPlayer();
        gameConfig = GameConfig.getGameConfig();
        configScreenViewModel = new ConfigScreenViewModel();
        gameScreenViewModel = new GameScreenViewModel();
        easyButtonId = 0;
        mediumButtonId = 1;
        hardButtonId = 2;
    }

    /**
     * Test for detection of white-space only, null, and empty names.
     */
    @Test
    public void testConfigValidateName() {
        String testName = "test";
        player.setName(testName);
        configScreenViewModel.validateName("");
        assertSame(testName, player.getName());

        configScreenViewModel.validateName("   ");
        assertSame(testName, player.getName());

        configScreenViewModel.validateName(null);
        assertSame(testName, player.getName());

        String newName = "new";
        configScreenViewModel.validateName("new");
        assertSame(newName, player.getName());
    }

    /**
     * Test for proper initial health based on difficulty level.
     */
    @Test
    public void testConfigSetModelAttributesHealth() {
        gameConfig.setPlayer(player);
        gameConfig.getPlayer().setHealth(-1);

        int easyHealth = 15;
        gameConfig.setDifficulty(0);
        configScreenViewModel.setModelAttributes(-1); // not testing avatar ID assignment
        assertEquals(easyHealth, gameConfig.getPlayer().getHealth());

        int mediumHealth = 10;
        gameConfig.setDifficulty(1);
        configScreenViewModel.setModelAttributes(-1); // not testing avatar ID assignment
        assertEquals(mediumHealth, gameConfig.getPlayer().getHealth());

        int hardHealth = 5;
        gameConfig.setDifficulty(2);
        configScreenViewModel.setModelAttributes(-1); // not testing avatar ID assignment
        assertEquals(hardHealth, gameConfig.getPlayer().getHealth());
    }

    /**
     * Test for accurate setDifficulty method (Spring 1).
     */
    @Test
    public void testSetDifficultyMethod() {
        // Test for Difficulty Easy
        configScreenViewModel.setDifficulty(easyButtonId, easyButtonId, mediumButtonId);
        assertEquals(0, gameConfig.getDifficulty());

        // Test for Difficulty Medium
        configScreenViewModel.setDifficulty(mediumButtonId, easyButtonId, mediumButtonId);
        assertEquals(1, gameConfig.getDifficulty());

        //Test for Difficulty Hard
        configScreenViewModel.setDifficulty(hardButtonId, easyButtonId, mediumButtonId);
        assertEquals(2, gameConfig.getDifficulty());
    }

  
    /**
     * Test for accurate updateScore method (Spring 1).
     */
    @Test
    public void testUpdateScoreMethod() {
        player.setScore(100);
        gameScreenViewModel.updateScore();
        assertEquals(99, player.getScore());

        player.setScore(0);
        gameScreenViewModel.updateScore();
        assertEquals(0, player.getScore());
    }
  
  
    /**
     * Tests that getDifficulty returns the correct string for GameScreen1View Model.
     */
    @Test
    public void testGameScreen1ViewModelGetDifficulty() {
        gameConfig.setDifficulty(0);
        String expected0 = "Difficulty: Easy";
        String actual = gameScreenViewModel.getDifficulty();
        assertEquals(expected0, actual);

        gameConfig.setDifficulty(1);
        String expected1 = "Difficulty: Medium";
        String actual1 = gameScreenViewModel.getDifficulty();
        assertEquals(expected1, actual1);



        gameConfig.setDifficulty(2);
        String expected2 = "Difficulty: Hard";
        String actual2 = gameScreenViewModel.getDifficulty();
        assertEquals(expected2, actual2);


        gameConfig.setDifficulty(3);
        String expected3 = "Difficulty: ...";
        String actual3 = gameScreenViewModel.getDifficulty();
        assertEquals(expected3, actual3);
    }


    /**
     * Tests that the initPlayerAttempt() in GameScreen1ViewModel correctly sets the player’s score
     * and start time
     */
    @Test
    public void testInitPlayerAttempt() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        gameScreenViewModel.initPlayerAttempt();
        assertEquals(100, player.getScore());
        assertEquals(dateFormat.format(date), player.getStartTime());
    }

 
    /**
     * This test checks the functionality of the setTopFiveNames method in the Leaderboard class.
     */
    @Test
    public void testSetTopFiveNames() {
        Leaderboard leaderboard = Leaderboard.getLeaderboard();
        String[] expectedNames = {"Alice", "Bob", "Charlie", "David", "Eve"};
        leaderboard.setTopFiveNames(expectedNames);
        assertArrayEquals(expectedNames, leaderboard.getTopFiveNames());
    }

  
    /**
     * This test verifies the functionality of the setTopFiveScores method in the Leaderboard class.
     */
    @Test
    public void testSetTopFiveScores() {
        Leaderboard leaderboard = Leaderboard.getLeaderboard();
        int[] expectedScores = {100, 90, 80, 70, 60};
        leaderboard.setTopFiveScores(expectedScores);
        assertArrayEquals(expectedScores, leaderboard.getTopFiveScores());
    }


    /**
     * This tests checks for wall collisions.
     * The player should not move as it is surrounded by walls.
     */

    @Test
    public void testCollisions() {
        int[][] gameWorld = {
                {1, 1, 1, 1, 1},
                {1, 2, 1, 1, 1},
                {1, 1, 1, 1, 1},
        };

        gameScreenViewModel.initRoom(gameWorld);
        //move right
        gameScreenViewModel.initPlayerPosition(1, 1);
        gameScreenViewModel.setPlayerMovePattern(new MoveRight());
        gameScreenViewModel.doPlayerMove();
        assertEquals(1, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());


        //move left
        gameScreenViewModel.setPlayerMovePattern(new MoveLeft());
        gameScreenViewModel.doPlayerMove();
        assertEquals(1, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());


        //move down
        gameScreenViewModel.setPlayerMovePattern(new MoveDown());
        gameScreenViewModel.doPlayerMove();
        assertEquals(1, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());

        //move up
        gameScreenViewModel.setPlayerMovePattern(new MoveUp());
        gameScreenViewModel.doPlayerMove();
        assertEquals(1, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());


    }

    /**
     * Tests general movement making sure character can move
     * when there is no wall at there next desired position.
     */
    @Test
    public void testMovement() {

        int[][] gameWorld = {
                {1, 1, 2, 1, 1},
                {1, 2, 2, 2, 1},
                {1, 1, 2, 1, 1},
        };
        // move player to the right.
        gameScreenViewModel.initRoom(gameWorld);
        gameScreenViewModel.initPlayerPosition(1, 1);
        gameScreenViewModel.setPlayerMovePattern(new MoveRight());
        gameScreenViewModel.doPlayerMove();
        assertEquals(2, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());

        // now character is in dead center of map. The  position should be able to go up from here.
        gameScreenViewModel.setPlayerMovePattern(new MoveUp());
        gameScreenViewModel.doPlayerMove();
        assertEquals(2, gameScreenViewModel.getPlayerX());
        assertEquals(0, gameScreenViewModel.getPlayerY());

        // Now I will do two down movements, the player should be in the bottom center of the map.
        gameScreenViewModel.setPlayerMovePattern(new MoveDown());
        gameScreenViewModel.doPlayerMove();
        gameScreenViewModel.doPlayerMove();
        assertEquals(2, gameScreenViewModel.getPlayerX());
        assertEquals(2, gameScreenViewModel.getPlayerY());

        // Now I will do an up and to the right movement.

        gameScreenViewModel.setPlayerMovePattern(new MoveUp());
        gameScreenViewModel.doPlayerMove();
        gameScreenViewModel.setPlayerMovePattern(new MoveRight());
        gameScreenViewModel.doPlayerMove();
        assertEquals(3, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());

        //testing a wall collisions, when the player tries to move right from here,
        //the player doesn't move.
        gameScreenViewModel.setPlayerMovePattern(new MoveRight());
        gameScreenViewModel.doPlayerMove();
        assertEquals(3, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());

    }
}
