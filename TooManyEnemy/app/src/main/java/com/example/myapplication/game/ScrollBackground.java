package com.example.myapplication.game;

import android.graphics.Canvas;
import android.graphics.Rect;

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
}
