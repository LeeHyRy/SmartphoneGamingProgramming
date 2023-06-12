package com.example.myapplication.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.myapplication.R;

import java.util.ArrayList;

import framework.interfaces.IGameObject;
import framework.objects.Sprite;
import framework.scene.BaseScene;
import framework.util.Gauge;
import framework.view.Metrics;

public class Player extends Sprite {

    private static final float FIGHTER_WIDTH= 64f * 0.03f;
    private static final float FIGHTER_HEIGHT= 64f * 0.03f;
    private float spriteframeTime;
    private float time;

    public Stat stat = new Stat();
    public float attackAngle = -90;
    private Gauge expGauge, hpGauge;

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



    public void move(float dx, float dy) {
        float newX = x + dx* BaseScene.frameTime*stat.speed;
        float newY = y + dy* BaseScene.frameTime*stat.speed;
        float offset = 0.75f;
        if (newX > 18f - offset) newX = 18f - offset;
        else if (newX < offset) newX = offset;
        if (newY > 32f - offset) newY = 32f - offset;
        else if (newY < offset) newY = offset;

        moveTo(newX, newY);
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
        if (time > stat.interval && fly != null) {
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

        if (expGauge == null){
            expGauge = new Gauge(0.4f, R.color.exp, R.color.black);
            hpGauge =  new Gauge(0.2f, R.color.hp, R.color.black);
        }
        expGauge.draw(canvas, 0, 31, 1f, 18f, (float)stat.exp/(float)stat.levelupExp);
        hpGauge.draw(canvas, x - width / 2, y + height / 2, width,1f, (float)stat.hp / stat.maxHp);
    }
}
