package com.example.myapplication.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

import framework.interfaces.IGameObject;
import framework.interfaces.IRecyclable;
import framework.objects.Sprite;
import framework.scene.BaseScene;
import framework.scene.RecycleBin;
import framework.view.Metrics;
import com.example.myapplication.R;


public class Shell extends Sprite implements IRecyclable {
    private Rect srcRect = new Rect();
    private float dx, dy, radius;
    private Fly target;
    private float power;
    private boolean splash;

    private MainScene scene = (MainScene) BaseScene.getTopScene();

    private Shell() {
        super(R.mipmap.shells, 0, 0, 0.5f, 0.5f);
    }

    public static Shell get(Player player, Fly target) {
        Shell shell = (Shell) RecycleBin.get(Shell.class);
        if (shell == null) {
            shell = new Shell();
        }
        shell.init(player, target);
        return shell;
    }

    private void init(Player player, Fly target) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int maxLevel = w / h;
        int level = player.stat.level;
        if (level < 1) level = 1;
        if (level > maxLevel) level = maxLevel;
        srcRect.set(h * (level - 1), 0, h * level, h);
        //Log.d("CannonFire", "shell rect: " + srcRect);
        this.x = player.getX();
        this.y = player.getY();
        this.target = target;
        double radian = player.attackAngle * Math.PI / 180;
        double speed = 10.0 + level;
        dx = (float) (speed * Math.cos(radian));
        dy = (float) (speed * Math.sin(radian));
        this.power = player.stat.power;
        radius = 0.2f + level * 0.02f;
        splash = level >= 4;
        setSize(2 * radius, 2 * radius);
    }

    @Override
    public void update() {
        BaseScene scene = BaseScene.getTopScene();
        x += dx * BaseScene.frameTime;
        y += dy * BaseScene.frameTime;
        fixDstRect();
        if (x < -radius || x > Metrics.game_width + radius ||
                y < -radius || y > Metrics.game_height + radius) {
            //Log.d("CannonFire", "Remove(" + x + "," + y + ") " + this);
            scene.remove(MainScene.Layer.shell, this);
            return;
        }
        if (target != null) {
            checkCollision(target);
        } else {
            ArrayList<IGameObject> flies = scene.getObjectsAt(MainScene.Layer.monster);
            for (int i = flies.size() - 1; i >= 0; i--) {
                Fly fly = (Fly) flies.get(i);
                checkCollision(fly);
            }
        }
    }

    private boolean checkCollision(Fly target) {
        float dx = x - target.getX();
        float dy = y - target.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        float flyRadius = target.getWidth() / 2;
        if (dist >= radius + flyRadius) {
            return false;
        }
        scene.remove(MainScene.Layer.shell, this);
        /*
        if (splash) {
            explode();
            return true;
        }
         */
        hit(target, power);
        return true;
    }

    private void hit(Fly target, float power) {
        boolean dead = target.decreaseHealth(power);
        if (!dead) {
            return;
        }
        this.target = null;
        //scene.score.add(target.score());
        ((Player)BaseScene.getTopScene().getObjectsAt(MainScene.Layer.player).get(0)).stat.score.add(target.score());
        scene.remove(MainScene.Layer.monster, target);
        Exporb exporb = Exporb.get(target);
        scene.add(MainScene.Layer.item, exporb);
        for (IGameObject o: scene.getObjectsAt(MainScene.Layer.shell)) {
            Shell s = (Shell)o;
            if (s.target == target) {
                s.target = null;
            }
        }
    }
/*
    private void explode() {
        float explosion_radius = 2;
        Explosion ex = Explosion.get(getX(), getY(), explosion_radius);
        MainScene scene = (MainScene) BaseScene.getTopScene();
        scene.add(MainScene.Layer.explosion, ex);
        ArrayList<IGameObject> flies = scene.getObjectsAt(MainScene.Layer.enemy);
        for (int i = flies.size() - 1; i >= 0; i--) {
            Fly fly = (Fly) flies.get(i);
            float dx = x - fly.getX();
            float dy = y - fly.getY();
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < explosion_radius) {
                hit(fly, power);
            }
        }
    }
*/
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    @Override
    public void onRecycle() {

    }
}
