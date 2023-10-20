package com.example.team41game;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import com.example.team41game.models.GameConfig;
import com.example.team41game.models.Leaderboard;
import com.example.team41game.models.Player;
import com.example.team41game.viewModels.EndScreenViewModel;
public class LeaderboardUnitTests {
    private Player player;
    private GameConfig gameConfig;
    private EndScreenViewModel endScreenViewModel;

    private Leaderboard leaderboard;

    @Before
    public void setup() {
        player = Player.getPlayer();
        gameConfig = GameConfig.getGameConfig();
        endScreenViewModel = new EndScreenViewModel();
        leaderboard = Leaderboard.getLeaderboard();
    }

    /**
     * Test that updateLeaderboard correctly places a new leaderboard entry (Sprint 2).
     */
    @Test
    public void testUpdateLeaderboard() {
        String[] topFiveNames = new String[]{"Bob", "Max", "Tony", "Sally", "Haley"};
        int[] topFiveScores = new int[]{95, 80, 70, 63, 42};
        String[] topFiveStartTimes = new String[]{"2023/10/10 07:00:00", "2023/10/10 06:00:00",
            "2023/10/10 08:00:00", "2023/10/10 09:00:00", "2023/10/10 09:30:00"};
        leaderboard.setTopFiveNames(topFiveNames);
        leaderboard.setTopFiveScores(topFiveScores);
        leaderboard.setTopFiveStartTimes(topFiveStartTimes);

        String[] updatedTopFiveNames = new String[]{"Bob", "John", "Max", "Tony", "Sally"};
        int[] updatedTopFiveScores = new int[]{95, 85, 80, 70, 63};
        String[] updatedTopFiveStartTimes = new String[]{"2023/10/10 07:00:00",
            "2023/10/10 11:25:00", "2023/10/10 06:00:00", "2023/10/10 08:00:00",
            "2023/10/10 09:00:00"};

        endScreenViewModel.updateLeaderboard("John", 85,
                "2023/10/10 11:25:00");
        assertArrayEquals(updatedTopFiveNames, leaderboard.getTopFiveNames());
        assertArrayEquals(updatedTopFiveScores, leaderboard.getTopFiveScores());
        assertArrayEquals(updatedTopFiveStartTimes, leaderboard.getTopFiveStartTimes());
    }

    /**
     * Test that shiftElementsRightByOne correctly shifts elements of the passed in arrays
     * (Sprint 2).
     */
    @Test
    public void testShiftElementsRightByOne() {
        String[] leaderboardNames = new String[]{"Bob", "Max", "Tony", "Sally", "Haley"};
        int[] leaderboardScores = new int[]{95, 80, 70, 63, 42};
        String[] leaderboardStartTimes = new String[]{"2023/10/10 07:00:00", "2023/10/10 06:00:00",
            "2023/10/10 08:00:00", "2023/10/10 09:00:00", "2023/10/10 09:30:00"};

        String[] shiftedLeaderboardNames = new String[]{"Bob", "Bob", "Max", "Tony", "Sally"};
        int[] shiftedLeaderboardScores = new int[]{95, 95, 80, 70, 63};
        String[] shiftedLeaderboardStartTimes = new String[]{"2023/10/10 07:00:00",
            "2023/10/10 07:00:00", "2023/10/10 06:00:00", "2023/10/10 08:00:00",
            "2023/10/10 09:00:00"};

        endScreenViewModel.shiftElementsRightByOne(0, leaderboardNames, leaderboardScores,
                leaderboardStartTimes);

        assertArrayEquals(shiftedLeaderboardNames, leaderboardNames);
        assertArrayEquals(shiftedLeaderboardScores, leaderboardScores);
        assertArrayEquals(shiftedLeaderboardStartTimes, leaderboardStartTimes);
    }
}
