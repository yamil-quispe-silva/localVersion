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

import android.view.KeyEvent;
import com.example.team41game.Subscriber;
import com.example.team41game.MoveLeft;
import com.example.team41game.MoveRight;
import com.example.team41game.MoveUp;
import com.example.team41game.MoveDown;

public class GameScreen2Activity extends AppCompatActivity implements Subscriber {
    private Timer newTimer;
    private Handler handler;
    private ImageView gameImage;
    private TextView nameField;
    private TextView healthField;
    private TextView difficultyField;
    private Button nextBtn;
    private TextView scoreDisplay;
    private GameScreenViewModel gameScreenViewModel;
    private int[][] gameWorld = {
            {4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 1, 1, 1, 1, 6, 1, 1},
            {1, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0},
            {2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}
    };
    private int tileWidth = 42;
    private int tileHeight = 42;
    private Bitmap sprite;
    private Bitmap[] enemyTiles = new Bitmap[4];
    private Bitmap[] roomTiles = new Bitmap[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        gameScreenViewModel = new ViewModelProvider(this).get(GameScreenViewModel.class);
        gameScreenViewModel.subscribe(this);
        newTimer = new Timer();
        handler = new Handler();

        gameImage = findViewById(R.id.imageView);
        nameField = findViewById(R.id.nameField);
        healthField = findViewById(R.id.healthField);
        difficultyField = findViewById(R.id.difficultyField);
        scoreDisplay = findViewById(R.id.scoreDisplay);

        initGameTiles();

        displayGameSettings();
        gameScreenViewModel.initPlayerPosition(0, 7);
        gameScreenViewModel.initRoom(gameWorld);
        gameScreenViewModel.addEnemy(2, 1, 3);
        gameScreenViewModel.addEnemy(3, 8, 2);
        drawGameWorld();

        // Timer to call displayNewScore every second
        newTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> displayNewScore());

            }
        }, 10, 1000);
    }

    private void initGameTiles() {
        sprite = BitmapFactory.decodeResource(getResources(),
                gameScreenViewModel.getPlayerAvatar());

        enemyTiles[0] = BitmapFactory.decodeResource(getResources(), R.drawable.monster_bies);
        enemyTiles[1] = BitmapFactory.decodeResource(getResources(), R.drawable.monster_zombie);
        enemyTiles[2] =
                BitmapFactory.decodeResource(getResources(), R.drawable.monster_orc_veteran);
        enemyTiles[3] =
                BitmapFactory.decodeResource(getResources(), R.drawable.monster_elemental_goo);

        roomTiles[0] = BitmapFactory.decodeResource(getResources(), R.drawable.black);
        roomTiles[1] = BitmapFactory.decodeResource(getResources(), R.drawable.wall_front);
        roomTiles[2] = BitmapFactory.decodeResource(getResources(), R.drawable.floor_light);
        roomTiles[3] = BitmapFactory.decodeResource(getResources(), R.drawable.wall_flag_blue);
        roomTiles[4] = BitmapFactory.decodeResource(getResources(), R.drawable.wall_flag_green);
        roomTiles[5] = BitmapFactory.decodeResource(getResources(), R.drawable.wall_flag_red);
        roomTiles[6] = BitmapFactory.decodeResource(getResources(), R.drawable.door_open);
        roomTiles[7] = BitmapFactory.decodeResource(getResources(), R.drawable.door_closed);
        roomTiles[8] = BitmapFactory.decodeResource(getResources(), R.drawable.chest_golden_closed);
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
                Bitmap tileBitmap = roomTiles[tileID];
                canvas.drawBitmap(tileBitmap, j * tileWidth, i * tileHeight, null);
            }
        }

        int[] enemyX = gameScreenViewModel.getEnemyX();
        int[] enemyY = gameScreenViewModel.getEnemyY();
        int[] enemyTypes = gameScreenViewModel.getEnemyTypes();
        for (int i = 0; i < enemyTypes.length; i++) {
            canvas.drawBitmap(enemyTiles[enemyTypes[i]], enemyX[i] * tileWidth,
                    enemyY[i] * tileHeight, null);
        }

        canvas.drawBitmap(sprite, gameScreenViewModel.getPlayerX() * tileWidth,
                gameScreenViewModel.getPlayerY() * tileHeight, null);

        gameImage.setImageBitmap(gameBitmap);
    }

    public void displayGameSettings() {
        nameField.setText(gameScreenViewModel.getPlayerName());
        healthField.setText(gameScreenViewModel.getHealth());
        difficultyField.setText(gameScreenViewModel.getDifficulty());
    }

    private void displayNewScore() {
        gameScreenViewModel.updateScore();
        scoreDisplay.setText(gameScreenViewModel.getScore());
    }

    public void update(GameScreenViewModel subject) {
        drawGameWorld();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_DPAD_LEFT:
            gameScreenViewModel.setPlayerMovePattern(new MoveLeft());
            if (gameScreenViewModel.doPlayerMove()) {
                newTimer.cancel();
                Intent next = new Intent(GameScreen2Activity.this, GameScreen3Activity.class);
                startActivity(next);
            }
            return true;
        case KeyEvent.KEYCODE_DPAD_RIGHT:
            gameScreenViewModel.setPlayerMovePattern(new MoveRight());
            if (gameScreenViewModel.doPlayerMove()) {
                newTimer.cancel();
                Intent next = new Intent(GameScreen2Activity.this, GameScreen3Activity.class);
                startActivity(next);
            }
            return true;
        case KeyEvent.KEYCODE_DPAD_UP:
            gameScreenViewModel.setPlayerMovePattern(new MoveUp());
            if (gameScreenViewModel.doPlayerMove()) {
                newTimer.cancel();
                Intent next = new Intent(GameScreen2Activity.this, GameScreen3Activity.class);
                startActivity(next);
            }
            return true;
        case KeyEvent.KEYCODE_DPAD_DOWN:
            gameScreenViewModel.setPlayerMovePattern(new MoveDown());
            if (gameScreenViewModel.doPlayerMove()) {
                newTimer.cancel();
                Intent next = new Intent(GameScreen2Activity.this, GameScreen3Activity.class);
                startActivity(next);
            }
            return true;
        default:
            return super.onKeyDown(keyCode, event);
        }
    }
}


