package com.example.myapplication.game;

import android.content.Context;

import androidx.constraintlayout.helper.widget.Layer;

import com.example.myapplication.R;

import framework.scene.BaseScene;

public class MainScene extends BaseScene {
    private final Player player;

    public enum Layer {
        platfrom1, player, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);
        player = new Player();
        add(Layer.player, player);
        add(Layer.platfrom1, new ScrollBackground(R.mipmap.platform,7, 1, 16));
    }
}
