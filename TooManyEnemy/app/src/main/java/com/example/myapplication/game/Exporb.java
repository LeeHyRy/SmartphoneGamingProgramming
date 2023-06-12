package com.example.myapplication.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.myapplication.R;

import framework.interfaces.IRecyclable;
import framework.objects.Sprite;
import framework.scene.BaseScene;
import framework.scene.RecycleBin;

public class Exporb extends Sprite implements IRecyclable {
    private static final int gap = 264/11;
    private Player player;
    private float speed = 0.f;
    private float gatherDist = 5.f;
    private Rect srcRect
            = new Rect(gap*2, gap*2, gap*3, gap*3);

    private Exporb(){
        super(
                R.mipmap.orbs, // 264x264
                0,
                0,
                1.5f,
                1.5f
        );
        player = (Player)BaseScene.getTopScene().getObjectsAt(MainScene.Layer.player).get(0);
    }
    private void init() {
        speed = 0.f;
        gatherDist = 5.f;
    }

    public static Exporb get() {
        Exporb exporb = (Exporb) RecycleBin.get(Exporb.class);
        if (exporb == null) {
            exporb = new Exporb();
        }
        exporb.init();
        return exporb;
    }

    private void findAndMovePlayer(){
        float playerX = player.getX();
        float playerY = player.getY();

        float dx = playerX - x;
        if (dx > gatherDist) return;
        float dy = playerY - y;
        if (dy > gatherDist) return;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > gatherDist) return;

        float moveX = 0;
        float moveY = 0;
        if (distance != 0) {
            moveX = (dx / distance) * speed;
            moveY = (dy / distance) * speed;
        }
        // checkCollision
        if (distance > 0.5f)
            speed = Math.min(speed+BaseScene.frameTime, 30.f);
        else {
            MainScene scene = (MainScene) BaseScene.getTopScene();
            scene.remove(MainScene.Layer.item, this);
        }

        moveTo(x + moveX * BaseScene.frameTime, y + moveY * BaseScene.frameTime);
    }
    @Override
    public void update() {
        super.update();
        findAndMovePlayer();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
    @Override
    public void onRecycle() {

    }
}
