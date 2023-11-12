package com.example.team41game;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.team41game.interactiveObjFactoryDesign.BoxCreator;
import com.example.team41game.interactiveObjFactoryDesign.ChestCreator;
import com.example.team41game.interactiveObjFactoryDesign.InteractiveObj;
import com.example.team41game.interactiveObjFactoryDesign.InteractiveObjCreator;
import com.example.team41game.models.Player;
import com.example.team41game.models.Room;
import com.example.team41game.viewModels.CollisionViewModel;
import com.example.team41game.interactiveObjFactoryDesign.Chest;
import com.example.team41game.itemFactoryDesign.Item;
import com.example.team41game.Position;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CollisionUnitTests {
    private CollisionViewModel collisionViewModel;
    private Player player;

    @Before
    public void setup() {
        collisionViewModel = new CollisionViewModel();
        player = Player.getPlayer();
    }

    /**
     * Test that getAdjChest correctly returns the chest
     * that the player is one tile away from (Sprint 4)
     */
    @Test
    public void testGetAdjChest() {
        InteractiveObjCreator chestCreator = new ChestCreator();
        InteractiveObj chest = chestCreator.createInteractiveObj(0, 1, "gold");
        List<InteractiveObj> chests = new ArrayList<>();
        chests.add(chest);
        HashMap<String, List<InteractiveObj>> interactiveObjsMap = new HashMap<>();
        interactiveObjsMap.put("chests", chests);
        collisionViewModel.setInteractiveObjsMap(interactiveObjsMap);

        // player is one tile above the chest
        player.setPosition(new Position(0, 0));
        assertEquals(chest, collisionViewModel.getAdjChest());

        // player is far from the chest
        player.setPosition(new Position(10, 10));
        assertNull(collisionViewModel.getAdjChest());

        // player is diagonal to the chest
        player.setPosition(new Position(1, 0));
        assertNull(collisionViewModel.getAdjChest());
    }

    /**
     * Test that handleBoxCollision correctly moves the box that the player collides with (Sprint 4)
     */
    @Test
    public void testHandleBoxCollision() {
        InteractiveObjCreator boxCreator = new BoxCreator();
        InteractiveObj box = boxCreator.createInteractiveObj(1, 0, "unstacked");
        List<InteractiveObj> boxes = new ArrayList<>();
        boxes.add(box);
        HashMap<String, List<InteractiveObj>> interactiveObjsMap = new HashMap<>();
        interactiveObjsMap.put("boxes", boxes);
        collisionViewModel.setInteractiveObjsMap(interactiveObjsMap);

        int[][] roomLayout = {{0, 0, 0}, {0, 0, 0}};
        Room room = new Room(roomLayout);
        collisionViewModel.setRoom(room);

        // player is one tile to the left of the box, so moving right will push the box to (2, 0)
        player.setPosition(new Position(0, 0));
        player.setMovePattern(new MoveRight());
        collisionViewModel.handleBoxCollision();
        assertEquals(new Position(2, 0), box.getPosition());

        // player is one tile to the left of the box, but moving left will not collide with the box
        player.setPosition(new Position(0, 0));
        player.setMovePattern(new MoveLeft());
        collisionViewModel.handleBoxCollision();
        assertEquals(new Position(2, 0), box.getPosition());
    }

    /**
     * Test that handleItemAcquisition correctly acquires an item from a chest
     * adjacent to the player and adds it to the player's inventory (Sprint 4)
     */
    @Test
    public void testHandleItemAcquisition() {
        // Create mocks for Chest and Item
        Chest mockChest = mock(Chest.class);
        Item mockItem = mock(Item.class);

        // Set up the interactiveObjsMap to include the mocked chest
        List<InteractiveObj> chests = new ArrayList<>();
        chests.add(mockChest);
        HashMap<String, List<InteractiveObj>> interactiveObjsMap = new HashMap<>();
        interactiveObjsMap.put("chests", chests);
    }


    /**
     * Test that getCollision correctly identifies a collision with an object of a specified type
     * at a given position (Sprint 4)
     */
    @Test
    public void testGetCollision() {
        InteractiveObj mockInteractiveObj = mock(InteractiveObj.class);
        Position collisionPosition = new Position(1, 1);
        when(mockInteractiveObj.getPosition()).thenReturn(collisionPosition);

        List<InteractiveObj> interactiveObjs = new ArrayList<>();
        interactiveObjs.add(mockInteractiveObj);
        HashMap<String, List<InteractiveObj>> interactiveObjsMap = new HashMap<>();
        interactiveObjsMap.put("testObjects", interactiveObjs);
        collisionViewModel.setInteractiveObjsMap(interactiveObjsMap);

        InteractiveObj result = collisionViewModel.getCollision(collisionPosition, "testObjects");
        assertEquals(mockInteractiveObj, result);

        Position noCollisionPosition = new Position(2, 2);
        assertNull(collisionViewModel.getCollision(noCollisionPosition, "testObjects"));
    }


}