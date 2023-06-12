package com.example.myapplication.game;

import android.view.MotionEvent;

import com.example.myapplication.R;

import java.nio.channels.Selector;

import framework.objects.Score;
import framework.scene.BaseScene;
import framework.view.Metrics;

public class MainScene extends BaseScene {

    private Player player;
    protected Selector selector;
    protected Joystick joystick;
    public enum Layer {
        bg, item, monster, shell, player, score, controller, joystick, COUNT
    }
    public MainScene() {
        player = new Player();
        Metrics.setGameSize(18, 32);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLayers(Layer.COUNT);
        TiledBackground tiledBg = new TiledBackground("map", "realstage.tmj");
        add(Layer.bg, tiledBg);
        add(Layer.player, player);
        add(Layer.score, player.stat.score);
        add(Layer.controller, new FlyGen());
        joystick = new Joystick();
        add(Layer.joystick, joystick);

    }
    @Override
    public boolean handleBackKey() {
        new BaseScene() {
            @Override
            public boolean isTransparent() { return true; }
        }.pushScene();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float gx = Metrics.toGameX(event.getX());
        float gy = Metrics.toGameY(event.getY());
        return joystick.onTouch(action, gx, gy);

    }

}
