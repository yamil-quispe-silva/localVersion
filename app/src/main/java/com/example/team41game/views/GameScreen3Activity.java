package com.example.team41game.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.team41game.R;
import com.example.team41game.viewModels.GameScreenViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreen3Activity extends AppCompatActivity {
    private Timer newTimer;
    private Handler handler;
    private ImageView gameImage;
    private TextView nameField;
    private TextView healthField;
    private TextView difficultyField;
    private ImageView playerView;
    private Button nextBtn;
    private TextView scoreDisplay;
    private GameScreenViewModel gameScreenViewModel;
    private int[][] gameWorld = {
            {1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1},
            {1, 2, 2, 2, 1, 1, 1, 2, 2, 2, 1},
            {1, 2, 2, 2, 1, 0, 1, 2, 2, 2, 1},
            {1, 2, 2, 2, 1, 1, 1, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 1, 2, 2, 2, 1, 2, 2, 1},
            {1, 2, 2, 1, 2, 2, 2, 1, 2, 2, 1},
            {1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1}
    };
    private int[][] enemyPlacement = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 8, 0, 0, 11, 0, 0, 8, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private int tileWidth = 42;
    private int tileHeight = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        gameScreenViewModel = new ViewModelProvider(this).get(GameScreenViewModel.class);
        newTimer = new Timer();
        handler = new Handler();

        gameImage = findViewById(R.id.imageView);
        nameField = findViewById(R.id.nameField);
        healthField = findViewById(R.id.healthField);
        difficultyField = findViewById(R.id.difficultyField);
        playerView = findViewById(R.id.playerView);
        nextBtn = findViewById(R.id.NextButton);
        scoreDisplay = findViewById(R.id.scoreDisplay);

        displayGameSettings();
        drawGameWorld();

        nextBtn.setOnClickListener(v -> {
            newTimer.cancel();
            Intent next = new Intent(GameScreen3Activity.this, EndScreenActivity.class);
            startActivity(next);
        });

        // Timer to call displayNewScore every second
        newTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> displayNewScore());
            }
        }, 10, 1000);
    }

    private void drawGameWorld() {
        Bitmap gameBitmap = Bitmap.createBitmap(
                gameWorld[0].length * tileWidth,
                gameWorld.length * tileHeight,
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(gameBitmap);

        for (int i = 0; i < gameWorld.length; i++) {
            for (int j = 0; j < gameWorld[0].length; j++) {
                int tileID = gameWorld[i][j];
                Bitmap tileBitmap = getTileBitmap(tileID);
                canvas.drawBitmap(tileBitmap, j * tileWidth, i * tileHeight, null);
            }
        }

        for (int i = 0; i < enemyPlacement.length; i++) {
            for (int j = 0; j < enemyPlacement[0].length; j++) {
                int tileID = enemyPlacement[i][j];
                if (tileID != 0 && tileID != 1) {
                    Bitmap enemyTileBitmap = getTileBitmap(tileID);
                    canvas.drawBitmap(enemyTileBitmap, j * tileWidth, i * tileHeight, null);
                }
            }
        }
        gameImage.setImageBitmap(gameBitmap);
    }

    public Bitmap getTileBitmap(int tileID) {
        Bitmap tileBitmap = null;
        switch (tileID) {
        case 0:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
            break;
        case 1:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wall_front);
            break;
        case 2:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.floor_light);
            break;
        case 3:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wall_flag_blue);
            break;
        case 4:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wall_flag_green);
            break;
        case 5:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wall_flag_red);
            break;
        case 6:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.npc_mage);
            break;
        case 7:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.npc_trickster);
            break;
        case 8:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.monster_ogre);
            break;
        case 9:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.door_closed);
            break;
        case 10:
            tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.torch_2);
            break;
        case 11:
            tileBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.chest_golden_closed);
            break;
        default:
            break;
        }
        return tileBitmap;
    }

    public void displayGameSettings() {
        nameField.setText(gameScreenViewModel.getPlayerName());
        healthField.setText(gameScreenViewModel.getHealth());
        difficultyField.setText(gameScreenViewModel.getDifficulty());
        playerView.setImageResource(gameScreenViewModel.getPlayerAvatar());
    }

    private void displayNewScore() {
        gameScreenViewModel.updateScore();
        scoreDisplay.setText(gameScreenViewModel.getScore());
    }
}


