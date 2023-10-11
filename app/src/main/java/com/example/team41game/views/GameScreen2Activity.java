package com.example.team41game.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.team41game.R;
import com.example.team41game.viewModels.GameScreen2ViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreen2Activity extends AppCompatActivity {
    private Timer newTimer;
    private Handler handler;
    private ImageView gameImage;
    private TextView nameField;
    private TextView healthField;
    private TextView difficultyField;
    private ImageView playerView;
    private Button nextBtn;
    private TextView scoreDisplay;
    private GameScreen2ViewModel gameScreen2ViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        gameScreen2ViewModel = new ViewModelProvider(this).get(GameScreen2ViewModel.class);
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
        gameImage.setImageResource(R.drawable.dungeon_gif);

        nextBtn.setOnClickListener(v -> {
            newTimer.cancel();
            Intent next = new Intent(GameScreen2Activity.this, GameScreen3Activity.class);
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

    public void displayGameSettings() {
        nameField.setText(gameScreen2ViewModel.getPlayerName());
        healthField.setText(gameScreen2ViewModel.getHealth());
        difficultyField.setText(gameScreen2ViewModel.getDifficulty());
        playerView.setImageResource(gameScreen2ViewModel.getPlayerAvatar());
    }

    private void displayNewScore() {
        gameScreen2ViewModel.updateScore();
        scoreDisplay.setText(gameScreen2ViewModel.getScore());
    }
}


