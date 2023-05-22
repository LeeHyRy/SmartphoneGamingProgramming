package com.example.myapplication.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import framework.objects.Sprite;
import framework.view.Metrics;

public class ScrollBackground extends Sprite {

    private float BG_WIDTH=32*0.03f;
    private Rect rects;
    public ScrollBackground(int bitmapResId, int x, int y, int size) {
        super(bitmapResId, Metrics.game_width / 2, Metrics.game_height / 2, Metrics.game_width, Metrics.game_height);
        rects = new Rect(size*x,size*y,size*(x+1),size*(y+1));
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(bitmap, rects, dstRect, null);
    }

    public void update(Context context) {
        loadStage(context, 1);
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
}
