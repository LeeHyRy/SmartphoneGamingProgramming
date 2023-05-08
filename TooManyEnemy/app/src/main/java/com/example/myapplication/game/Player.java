package com.example.myapplication.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.myapplication.R;

import framework.objects.Sprite;
import framework.scene.BaseScene;
import framework.view.Metrics;

public class Player extends Sprite {

    private static final float FIGHTER_WIDTH= 32f * 0.03f;
    private static final float FIGHTER_HEIGHT= 32f * 0.03f;
    private float spriteframeTime;

    private static final Rect[] rects = new Rect[] {
            new Rect(  0, 0,   0 + 32, 32),
            new Rect( 32, 0,  32 + 32, 32)
    };

    public Player()
    {
        super(
                R.mipmap.players,
                Metrics.game_width / 2,
                Metrics.game_height / 2,
                FIGHTER_WIDTH,
                FIGHTER_HEIGHT
        );
    }

    public void update() {
        super.update();
        spriteframeTime += BaseScene.frameTime;

    }

    @Override
    public void draw(Canvas canvas) {
        int rollIndex = (int)(spriteframeTime)%2;
        canvas.drawBitmap(bitmap, rects[rollIndex], dstRect, null);
    }
}
