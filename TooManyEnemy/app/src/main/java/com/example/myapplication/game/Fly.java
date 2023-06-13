package com.example.myapplication.game;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

import framework.interfaces.IRecyclable;
import framework.objects.SheetSprite;
import framework.scene.BaseScene;
import framework.scene.RecycleBin;
import framework.util.Gauge;
import com.example.myapplication.R;


public class Fly extends SheetSprite implements IRecyclable {

    public static final String TAG = Fly.class.getSimpleName();
    private Type type;
    private float speed, distance;
    private float dx, dy;
    private float health, maxHealth;
    private static Gauge gauge;
    private Player player;

    private static Random random = new Random();
    private MainScene scene = (MainScene) BaseScene.getTopScene();


    public enum Type {
        boss, red, blue, cyan, dragon, COUNT, RANDOM;
        float getMaxHealth() {
            return HEALTHS[ordinal()];
        }
        static float[] HEALTHS = { 150, 50, 30, 20, 10 };
        static int[] POSSIBILITIES = { 0, 5, 10, 30, 55 };
        static int POSSIBILITY_SUM;
        static {
            POSSIBILITY_SUM = 0;
            for (int p : POSSIBILITIES) {
                POSSIBILITY_SUM += p;
            }
        }
    }

    public boolean decreaseHealth(float power) {
        health -= power;
        return health <= 0;
    }

    public int score() {
        return Math.round(maxHealth / 10) * 10;
    }

    public static Fly get(Type type, float speed, float size) {
        Fly fly = (Fly) RecycleBin.get(Fly.class);
        if (fly == null) {
            fly = new Fly();
        }
        fly.init(type, speed, size);
        return fly;
    }

    private Fly() {
        super(R.mipmap.slime, 2.0f);
        player = (Player)BaseScene.getTopScene().getObjectsAt(MainScene.Layer.player).get(0);
        if (rects_array == null) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            int gapW = w/7;
            int gapH = h/5;
            rects_array = new Rect[1][];
            int x = 0;
            rects_array[0] = new Rect[6];
            for (int j = 0; j < 6; j++) {
                rects_array[0][j] = new Rect(x, gapH, x+gapW, 2*gapH);
                x += gapW;
            }
        }
    }

    private Rect[][] rects_array;
    private void init(Type type, float speed, float size) {
        if (type == Type.RANDOM) {
            int value = random.nextInt(Type.POSSIBILITY_SUM);
            //Log.d(TAG, "value=" + value);
            for (int i = 0; i < Type.POSSIBILITIES.length; i++) {
                value -= Type.POSSIBILITIES[i];
                if (value < 0) {
                    type = Type.values()[i];
                    //Log.d("Fly", "type=" + type + " i=" + i);
                    break;
                }
            }
        }
        this.type = type;
        this.speed = speed * (float)Math.min(3, Math.pow(1.1, player.stat.waves));
        this.width = this.height = size;
        this.distance = 0;
        dx = dy = 0;
        health = maxHealth = type.getMaxHealth() * (0.9f + random.nextFloat() * 0.2f) * (float)Math.pow(1.1, player.stat.waves);
        srcRects = rects_array[0];

        findAndMovePlayer();
    }

    private void findAndMovePlayer(){
        float playerX = player.getX();
        float playerY = player.getY();

        float dx = playerX - x;
        float dy = playerY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        float moveX = 0;
        float moveY = 0;
        if (distance != 0) {
            moveX = (dx / distance) * speed;
            moveY = (dy / distance) * speed;
        }
        if (distance < 1.5f){
            player.stat.getDamage(Math.round(this.health));
            scene.remove(MainScene.Layer.monster, this);
            return;
        }

        moveTo(x + moveX * BaseScene.frameTime, y + moveY * BaseScene.frameTime);
    }

    private float[] pos = new float[2];
    private float[] tan = new float[2];
    @Override
    public void update() {
        super.update();
        distance += speed * BaseScene.frameTime;
        float maxDiff = width / 5;
        dx += (2 * maxDiff * random.nextFloat() - maxDiff) * BaseScene.frameTime;
        if (dx < -maxDiff) dx = -maxDiff;
        else if (dx > maxDiff) dx = maxDiff;
        dy += (2 * maxDiff * random.nextFloat() - maxDiff) * BaseScene.frameTime;
        if (dy < -maxDiff) dy = -maxDiff;
        else if (dy > maxDiff) dy = maxDiff;

        findAndMovePlayer();
        /*
        if (distance > length) {
            BaseScene.getTopScene().remove(MainScene.Layer.monster, this);
        }

         */
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float size = width * 2 / 3;
        if (gauge == null) {
            gauge = new Gauge(0.2f, R.color.teal_200, R.color.black);
        }
        gauge.draw(canvas, x - size / 2, y + size / 2, size,1f, health / maxHealth);
    }

    @Override
    public void onRecycle() {
    }
}
