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
import com.example.team41game.viewModels.GameScreen1ViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreen1Activity extends AppCompatActivity {
    private Timer newTimer;
    private Handler handler;
    private ImageView gameImage;
    private TextView nameField;
    private TextView healthField;
    private TextView difficultyField;
    private ImageView playerView;
    private Button nextBtn;
    private TextView scoreDisplay;
    private GameScreen1ViewModel gameScreen1ViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        gameScreen1ViewModel = new ViewModelProvider(this).get(GameScreen1ViewModel.class);
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
        gameImage.setImageResource(R.drawable.game_pic);

        nextBtn.setOnClickListener(v -> {
            newTimer.cancel();
            Intent next = new Intent(GameScreen1Activity.this, GameScreen2Activity.class);
            startActivity(next);
        });

        gameScreen1ViewModel.initPlayerAttempt();

        // Timer to call displayNewScore every second
        newTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> displayNewScore());
            }
        }, 10, 1000);
    }

    public void displayGameSettings() {
        nameField.setText(gameScreen1ViewModel.getPlayerName());
        healthField.setText(gameScreen1ViewModel.getHealth());
        difficultyField.setText(gameScreen1ViewModel.getDifficulty());
        playerView.setImageResource(gameScreen1ViewModel.getPlayerAvatar());
    }

    private void displayNewScore() {
        gameScreen1ViewModel.updateScore();
        scoreDisplay.setText(gameScreen1ViewModel.getScore());
    }
}


