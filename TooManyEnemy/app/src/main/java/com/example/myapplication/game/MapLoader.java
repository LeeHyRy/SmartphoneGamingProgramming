package com.example.myapplication.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import framework.interfaces.IGameObject;
import framework.scene.BaseScene;
import framework.view.Metrics;

public class MapLoader implements IGameObject {
    private float x, y;
    private Player player;


    private static final int STAGE_WIDTH = 16;
    private static final int STAGE_HEIGHT = 16;

    public MapLoader(Context context, Player pl){
        loadStage(context, 1);
        player = pl;
    }

    private void loadStage(Context context, int stage) {
        AssetManager assets = context.getAssets();
        try {
            String file = "stage1.json";
            InputStream is = assets.open(file);
            InputStreamReader jsr = new InputStreamReader(is);
            JsonReader jr = new JsonReader(jsr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
        if (x != player.x || y != player.y) {
            x = player.x;
            y = player.y;
            createPlatform();
        }
    }

    private void createPlatform() {
        for (int row = 0; row < STAGE_HEIGHT; row++) {
            for (int col = 0; col < STAGE_WIDTH; col++) {
                int tile = getAt(col, row);
                createObject(tile, col, row);
            }
        }
    }

    private void createObject(int tile, float left, int top) {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        if (tile == 61 || tile == 71 || tile == 73) {
            Platform.Type ptype =
                    tile == 61 ? Platform.Type.T_10x2 :
                            tile == 71 ? Platform.Type.T_2x2 :
                                    Platform.Type.T_3x1;
            Platform platform = Platform.get(ptype, left, top);
            scene.add(MainScene.Layer.platform, platform);
            return;
        }
    }

    private int getAt(int col, int row) {
        int idx = row * STAGE_WIDTH + col;
        if (idx >= STAGES[0].length) return 0;
        return STAGES[0][idx];
    }


    @Override
    public void draw(Canvas canvas) {}

}
