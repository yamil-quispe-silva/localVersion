package com.example.team41game;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.team41game.interactiveObjFactoryDesign.DoorCreator;
import com.example.team41game.interactiveObjFactoryDesign.InteractiveObj;
import com.example.team41game.interactiveObjFactoryDesign.InteractiveObjCreator;
import com.example.team41game.models.GameConfig;
import com.example.team41game.models.Player;
import com.example.team41game.viewModels.GameScreenViewModel;

/**
 * Tests 2D Dungeon Game logic.
 */
public class GameScreenUnitTests {
    private GameConfig gameConfig;
    private Player player;
    private GameScreenViewModel gameScreenViewModel;

    @Before
    public void setup() {
        player = Player.getPlayer();
        gameConfig = GameConfig.getGameConfig();
        gameScreenViewModel = new GameScreenViewModel();
    }
  
    /**
     * Test that updateScore correctly alters the player's score (Sprint 2)
     */
    @Test
    public void testUpdatePlayerScore() {
        player.setScore(100);
        gameScreenViewModel.updateScore();
        assertEquals(99, player.getScore());

        player.setScore(0);
        gameScreenViewModel.updateScore();
        assertEquals(0, player.getScore());

        player.setScore(-1);
        gameScreenViewModel.updateScore();
        assertEquals(0, player.getScore());
    }
  
    /**
     * Test that getDifficulty returns the correct string (Sprint 2)
     */
    @Test
    public void testGetDifficulty() {
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

        gameConfig.setDifficulty(-1);
        String actual4 = gameScreenViewModel.getDifficulty();
        assertEquals(expected3, actual4);
    }


    /**
     * Test that initPlayerAttempt correctly sets the player’s score and start time (Sprint 2)
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
     * Test that Player's position is properly set at start of each new game screen. (Sprint 3)
     */
    @Test
    public void testInitPlayerPosition() {
        int testX = 10;
        int testY = 10;

        gameScreenViewModel.initPlayerPosition(testX, testY);
        assertEquals(testX, gameScreenViewModel.getPlayerX());
        assertEquals(testY, gameScreenViewModel.getPlayerY());

        int testX2 = 0;
        int testY2 = 0;

        gameScreenViewModel.initPlayerPosition(testX2, testY2);
        assertEquals(testX2, gameScreenViewModel.getPlayerX());
        assertEquals(testY2, gameScreenViewModel.getPlayerY());
    }

    /**
     * Test that enemies are properly added to the list in the current room. (Sprint 3 - obsolete)
     */
    //    @Test
    //    public void testAddEnemy() {
    //        gameScreenViewModel.initRoom(null);
    //        assertEquals(0, gameScreenViewModel.getEnemyTypes().length);
    //        assertEquals(0, gameScreenViewModel.getEnemyX().length);
    //        assertEquals(0, gameScreenViewModel.getEnemyY().length);
    //
    //        int[] testTypes = {3, 2, 1, 0};
    //        int[] testX = {4, 5, 6, 7};
    //        int[] testY = {8, 9, 10, 11};
    //
    //        gameScreenViewModel.addEnemy(testTypes[0], testX[0], testY[0]);
    //        gameScreenViewModel.addEnemy(testTypes[1], testX[1], testY[1]);
    //        gameScreenViewModel.addEnemy(testTypes[2], testX[2], testY[2]);
    //        gameScreenViewModel.addEnemy(testTypes[3], testX[3], testY[3]);
    //
    //        assertArrayEquals(testTypes, gameScreenViewModel.getEnemyTypes());
    //        assertArrayEquals(testX, gameScreenViewModel.getEnemyX());
    //        assertArrayEquals(testY, gameScreenViewModel.getEnemyY());
    //    }

    /**
     * Test that initRoom() correctly assigns the floor layout (Sprint 3)
     */
    @Test
    public void testInitRoom() {
        int[][] layout = {
                {1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 6, 1, 0, 0, 0, 0}
        };
        gameScreenViewModel.initRoom(layout);
        assertArrayEquals(layout, gameScreenViewModel.getRoom().getFloorLayout());
    }

    /**
     * Test that inScreenLimit verifies that a player's movement
     * doesn't go outside the room's layout (Sprint 3)
     */
    @Test
    public void testInScreenLimit() {
        int[][] layout = {
                {1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 6, 1, 0, 0, 0, 0}
        };
        gameScreenViewModel.initRoom(layout);
        Position position = new Position(1, 0);

        // (1, 0) is valid position that a player can move left from
        player.setPosition(position);
        gameScreenViewModel.setPlayerMovePattern(new MoveLeft());
        assertTrue(gameScreenViewModel.inScreenLimit());

        // (0, 0) is invalid position for a player to move left from
        position.setX(0);
        assertFalse(gameScreenViewModel.inScreenLimit());

        // (0, 0) is valid position that a player can move right from
        gameScreenViewModel.setPlayerMovePattern(new MoveRight());
        assertTrue(gameScreenViewModel.inScreenLimit());

        // (layout[0].length - 1, 0) is invalid position for player to move right from
        position.setX(layout[0].length - 1);
        assertFalse(gameScreenViewModel.inScreenLimit());

        // (0, 1) is valid position that a player can move up from
        position.setX(0);
        position.setY(1);
        gameScreenViewModel.setPlayerMovePattern(new MoveUp());
        assertTrue(gameScreenViewModel.inScreenLimit());

        // (0, 0) is invalid position for a player to move up from
        position.setY(0);
        assertFalse(gameScreenViewModel.inScreenLimit());

        // (0, 0) is valid position that a player can move down from
        gameScreenViewModel.setPlayerMovePattern(new MoveDown());
        assertTrue(gameScreenViewModel.inScreenLimit());

        // (0, layout.length - 1) is invalid position for player to move down from
        position.setY(layout.length - 1);
        assertFalse(gameScreenViewModel.inScreenLimit());
    }

    /**
     * Test for verifying the setPlayerWinStatus method in the GameScreenViewModel class (Sprint 3)
     */
    @Test
    public void testSetPlayerWinStatus() {
        // Ensure the player's win status is initially false
        assertFalse(player.getWinStatus());

        // Set the player's win status to true using the method to be tested
        gameScreenViewModel.setPlayerWinStatus(true);

        // Verify that the player's win status has been updated correctly
        assertTrue(player.getWinStatus());

        // Set the player's win status to false to test the method's ability to change the status
        gameScreenViewModel.setPlayerWinStatus(false);

        // Verify that the player's win status is updated back to false
        assertFalse(player.getWinStatus());
    }

    /**
     * Test that isDoorCollision recognizes when the player hits a door. (Sprint 3)
     */
    @Test
    public void checkForDoorCollision() {

        // mock getResources
        HashMap<String, List<InteractiveObj>> interactiveObjsMap = new HashMap<>();
        List<InteractiveObj> doors = new ArrayList<>();
        InteractiveObjCreator doorCreator = new DoorCreator();
        doors.add(doorCreator.createInteractiveObj(3, 1, "exit"));
        interactiveObjsMap.put("doors", doors);

        // Set position to the door
        gameScreenViewModel.initPlayerPosition(3, 1);
        assertTrue(gameScreenViewModel.isDoorCollision(interactiveObjsMap));

        //Set position away from door
        gameScreenViewModel.initPlayerPosition(2, 1);
        assertFalse(gameScreenViewModel.isDoorCollision(interactiveObjsMap));
    }

    /**
     * Test that setMovePattern changes player's move pattern strategy. (Sprint 3)
     */
    @Test
    public void setPlayerMovePattern() {
        MovePattern moveLeft = new MoveLeft();
        player.setMovePattern(moveLeft);
        assertEquals(moveLeft, player.getMovePattern());

        MovePattern moveRight = new MoveRight();
        player.setMovePattern(moveRight);
        assertEquals(moveRight, player.getMovePattern());

        MovePattern moveUp = new MoveUp();
        player.setMovePattern(moveUp);
        assertEquals(moveUp, player.getMovePattern());

        MovePattern moveDown = new MoveDown();
        player.setMovePattern(moveDown);
        assertEquals(moveDown, player.getMovePattern());
    }

    /**
     * Tests general movement without collision detection (Sprint 3)
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
        gameScreenViewModel.movePlayer();
        assertEquals(2, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());

        // move player up
        gameScreenViewModel.setPlayerMovePattern(new MoveUp());
        gameScreenViewModel.movePlayer();
        assertEquals(2, gameScreenViewModel.getPlayerX());
        assertEquals(0, gameScreenViewModel.getPlayerY());

        // move player down twice
        gameScreenViewModel.setPlayerMovePattern(new MoveDown());
        gameScreenViewModel.movePlayer();
        gameScreenViewModel.movePlayer();
        assertEquals(2, gameScreenViewModel.getPlayerX());
        assertEquals(2, gameScreenViewModel.getPlayerY());

        // move player up then right
        gameScreenViewModel.setPlayerMovePattern(new MoveUp());
        gameScreenViewModel.movePlayer();
        gameScreenViewModel.setPlayerMovePattern(new MoveRight());
        gameScreenViewModel.movePlayer();
        assertEquals(3, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());
    }

    /**
     * This tests ensures that a player can't move into a wall (Sprint 3)
     */
    @Test
    public void testCollisions() {
        int[][] gameWorld = {
                {1, 1, 1, 1},
                {1, 2, 2, 1},
                {1, 1, 1, 1},
        };

        gameScreenViewModel.initRoom(gameWorld);

        // move right
        gameScreenViewModel.initPlayerPosition(1, 1);
        gameScreenViewModel.setPlayerMovePattern(new MoveRight());
        if (gameScreenViewModel.isValidMove()) {
            gameScreenViewModel.movePlayer();
        }
        assertEquals(2, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());

        // move left
        gameScreenViewModel.setPlayerMovePattern(new MoveLeft());
        if (gameScreenViewModel.isValidMove()) {
            gameScreenViewModel.movePlayer();
        }
        assertEquals(1, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());

        // Try to move down. This move would result in a wall collision, so player doesn't move
        gameScreenViewModel.setPlayerMovePattern(new MoveDown());
        if (gameScreenViewModel.isValidMove()) {
            gameScreenViewModel.movePlayer();
        }
        assertEquals(1, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());

        // Try to move up. This move would result in a wall collision, so player doesn't move
        gameScreenViewModel.setPlayerMovePattern(new MoveUp());
        if (gameScreenViewModel.isValidMove()) {
            gameScreenViewModel.movePlayer();
        }
        assertEquals(1, gameScreenViewModel.getPlayerX());
        assertEquals(1, gameScreenViewModel.getPlayerY());
    }
}
