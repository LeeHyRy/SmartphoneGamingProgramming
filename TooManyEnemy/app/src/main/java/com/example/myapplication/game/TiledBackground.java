package com.example.myapplication.game;

import android.graphics.Canvas;
import android.util.Log;

import framework.interfaces.IGameObject;
import framework.scene.BaseScene;
import framework.tiled.MapLoader;
import framework.tiled.TiledMap;
import framework.view.Metrics;


public class TiledBackground implements IGameObject {
    public static final int TILE_INDEX_BRICK = 10;
    private static final String TAG = TiledBackground.class.getSimpleName();
    private final TiledMap map;
    private float x, y, dx, dy;

    public TiledBackground(String folder, String tmjFile) {
        this.map = new MapLoader().loadAsset(folder, tmjFile);
    }

    public TiledBackground setFitWidth() {
        map.scale = Metrics.game_width / map.width;
        return this;
    }

    public TiledBackground setFitHeight() {
        map.scale = Metrics.game_height / map.height;
        return this;
    }

    public TiledBackground setScroll(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        return this;
    }

    public TiledBackground setWraps(boolean wraps) {
        map.wraps = wraps;
        return this;
    }



    @Override
    public void update() {
        this.x += dx * BaseScene.frameTime;
        this.y += dy * BaseScene.frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        map.draw(canvas, x, y);
    }

    public boolean canInstallAt(int x, int y) {
        if (x < 1 || y < 1) return false;
        if (x >= map.width || y >= map.height) return false;
        int tile;
        tile = map.getLayerAt(0).tileAt(x, y);
        if (tile != TILE_INDEX_BRICK) return false;
        tile = map.getLayerAt(0).tileAt(x - 1, y);
        if (tile != TILE_INDEX_BRICK) return false;
        tile = map.getLayerAt(0).tileAt(x, y - 1);
        if (tile != TILE_INDEX_BRICK) return false;
        tile = map.getLayerAt(0).tileAt(x - 1, y - 1);
        if (tile != TILE_INDEX_BRICK) return false;

        return true;
    }
}
