package com.example.myapplication.game;

import java.nio.channels.Selector;

import framework.scene.BaseScene;
import framework.view.Metrics;

public class MainScene extends BaseScene {

    protected Selector selector;
    public enum Layer {
        bg, item, monster, player, controller, COUNT
    }
    public MainScene() {
        Metrics.setGameSize(32, 18);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLayers(Layer.COUNT);
        TiledBackground tiledBg = new TiledBackground("map", "stage1.tmj");
        add(Layer.bg, tiledBg);
        add(Layer.controller, new FlyGen());
    }
    @Override
    public boolean handleBackKey() {
        new BaseScene() {
            @Override
            public boolean isTransparent() { return true; }
        }.pushScene();
        return true;
    }


}
