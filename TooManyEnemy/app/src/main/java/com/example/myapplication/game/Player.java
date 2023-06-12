package com.example.myapplication.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.myapplication.R;

import java.util.ArrayList;

import framework.interfaces.IGameObject;
import framework.objects.Sprite;
import framework.scene.BaseScene;
import framework.view.Metrics;

public class Player extends Sprite {

    private static final float FIGHTER_WIDTH= 64f * 0.03f;
    private static final float FIGHTER_HEIGHT= 64f * 0.03f;
    private float spriteframeTime;
    private float time;
    public int level = 1;
    public float power = 10;
    public float interval = 3.0f;
    public float attackAngle = -90;

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

    private void setLevel(int level) {
        this.level = level;
        this.power = (float) (10 * Math.pow(1.2, level - 1));
        this.interval = 3.0f * (float)(Math.pow(0.80, level - 1));
    }

    public void move(float dx, float dy) {
        moveTo(x + dx* BaseScene.frameTime, y + dy* BaseScene.frameTime);
        fixDstRect();
    }

    public void update() {
        super.update();
        time += BaseScene.frameTime;
        spriteframeTime += BaseScene.frameTime;

        Fly fly = findNearestFly();
        if (fly != null) {
            attackAngle = (float) (Math.atan2(fly.getY() - y, fly.getX() - x) / Math.PI * 180);
        }
        if (time > interval && fly != null) {
            Shell shell = Shell.get(this, fly);
            BaseScene.getTopScene().add(MainScene.Layer.shell, shell);
            time = 0;
        }


    }

    public Fly findNearestFly() {
        float dist = 999;
        Fly nearest = null;
        ArrayList<IGameObject> flies = BaseScene.getTopScene().getObjectsAt(MainScene.Layer.monster);
        for (IGameObject gameObject: flies) {
            if (!(gameObject instanceof Fly)) continue;
            Fly fly = (Fly) gameObject;
            float fx = fly.getX();
            float fy = fly.getY();
            float dx = x - fx;
            if (dx > dist) continue;
            float dy = y - fy;
            if (dy > dist) continue;
            float d = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist > d) {
                dist = d;
                nearest = fly;
            }
        }
        return nearest;
    }

    @Override
    public void draw(Canvas canvas) {
        int rollIndex = (int)(spriteframeTime)%2;
        canvas.drawBitmap(bitmap, rects[rollIndex], dstRect, null);
    }
}
