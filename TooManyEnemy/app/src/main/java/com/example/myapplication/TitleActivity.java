package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityTitleBinding;

public class TitleActivity extends AppCompatActivity {

    private ActivityTitleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setHighScore();

        // Start Game 버튼 클릭 이벤트 처리
        binding.startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // High Score 버튼 클릭 이벤트 처리
        binding.hsPlusOneBuuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHighScore(getHighScore()+1);
                setHighScore();
            }
        });

    }
    public void saveHighScore(int score) {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("high_score", score);
        editor.apply();
    }
    public int getHighScore() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getInt("high_score", 0);
    }
    private void setHighScore(){
        int highScore = getHighScore();
        binding.highScoreText.setText("High Score: " + highScore);
    }



}
