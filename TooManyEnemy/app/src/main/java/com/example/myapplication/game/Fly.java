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
    private float angle;
    private float dx, dy;
    private float health, maxHealth;
    private static Gauge gauge;
    private Player player;

    private static Random random = new Random();
    private static Path path;
    private static PathMeasure pm;
    private static final float length;
//    private static Paint paint;

    static {
        path = new Path();
        path.moveTo(-1.28f, 18.176f);
        path.cubicTo(1.984f, 17.856f, 3.84f, 15.584f, 3.84f, 12.8f);
        path.cubicTo(3.84f, 10.016f, 0.864f, 9.568f, 0.896f, 6.56f);
        path.cubicTo(0.928f, 3.552f, 3.328f, 0.544f, 6.4f, 0.512f);
        path.cubicTo(9.472f, 0.48f, 11.776f, 3.392f, 11.84f, 6.496f);
        path.cubicTo(11.904f, 9.6f, 9.888f, 9.248f, 9.92f, 12.512f);
        path.cubicTo(9.952f, 15.776f, 14.4f, 16.928f, 16.096f, 16.928f);
        path.cubicTo(17.792f, 16.928f, 22.176f, 15.168f, 22.208f, 12.64f);
        path.cubicTo(22.24f, 10.112f, 19.936f, 9.408f, 19.936f, 6.624f);
        path.cubicTo(19.936f, 3.84f, 22.368f, 0.832f, 25.76f, 0.864f);
        path.cubicTo(29.152f, 0.896f, 31.2f, 4.192f, 31.104f, 6.752f);
        path.cubicTo(31.008f, 9.312f, 28.16f, 10.784f, 28.192f, 13.408f);
        path.cubicTo(28.224f, 16.032f, 31.552f, 17.824f, 33.6f, 17.664f);


        pm = new PathMeasure(path, false);
        length = pm.getLength();

//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(0.1f);
//        paint.setColor(Color.MAGENTA);
    }

//    public static void drawPath(Canvas canvas) {
//        canvas.drawPath(path, paint);
//    }

    public enum Type {
        boss, red, blue, cyan, dragon, COUNT, RANDOM;
        float getMaxHealth() {
            return HEALTHS[ordinal()];
        }
        static float[] HEALTHS = { 150, 50, 30, 20, 10 };
        static int[] POSSIBILITIES = { 0, 10, 20, 30, 40 };
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
        this.speed = speed;
        this.width = this.height = size;
        this.distance = 0;
        dx = dy = 0;
        health = maxHealth = type.getMaxHealth() * (0.9f + random.nextFloat() * 0.2f);
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

        moveTo(x + moveX * BaseScene.frameTime, y + moveY * BaseScene.frameTime);
        angle = (float) Math.toDegrees(Math.atan2(moveY, moveX));
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
        if (distance > length) {
            BaseScene.getTopScene().remove(MainScene.Layer.monster, this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        super.draw(canvas);
        canvas.restore();
        float size = width * 2 / 3;
        if (gauge == null) {
            gauge = new Gauge(0.2f, R.color.teal_200, R.color.black);
        }
        gauge.draw(canvas, x - size / 2, y + size / 2, size, health / maxHealth);
    }

    @Override
    public void onRecycle() {
    }
}
