package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.game.MainScene;

import framework.res.Sound;
import framework.view.GameView;
import framework.scene.BaseScene;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        gameView.setFullScreen();
        setContentView(gameView);
        Sound.playMusic(R.raw.bgm);
        new MainScene().pushScene();
    }

    @Override
    protected void onPause() {
        gameView.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        BaseScene.popAll();
        super.onDestroy();
    }

}