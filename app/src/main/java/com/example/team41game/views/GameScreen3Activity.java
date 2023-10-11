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
import com.example.team41game.viewModels.GameScreen3ViewModel;

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
    private GameScreen3ViewModel gameScreen3ViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        gameScreen3ViewModel = new ViewModelProvider(this).get(GameScreen3ViewModel.class);
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
        gameImage.setImageResource(R.drawable.ref1_map);

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

    public void displayGameSettings() {
        nameField.setText(gameScreen3ViewModel.getPlayerName());
        healthField.setText(gameScreen3ViewModel.getHealth());
        difficultyField.setText(gameScreen3ViewModel.getDifficulty());
        playerView.setImageResource(gameScreen3ViewModel.getPlayerAvatar());
    }

    private void displayNewScore() {
        gameScreen3ViewModel.updateScore();
        scoreDisplay.setText(gameScreen3ViewModel.getScore());
    }
}


