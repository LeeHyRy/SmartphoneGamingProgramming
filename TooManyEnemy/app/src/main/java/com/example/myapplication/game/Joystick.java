package com.example.myapplication.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import framework.objects.Sprite;
import framework.scene.BaseScene;
import com.example.myapplication.R;

public class Joystick extends Sprite {

    private Player player;
    private static float centerX = 9;
    private static float centerY = 26;

    private static final float maxDistance = 2;
    private static final Rect rects
            = new Rect(0, 0, 300, 300);

    public Joystick(){
        super(
            R.mipmap.joystick,
            centerX,
            centerY,
            maxDistance,
            maxDistance
        );
        player = (Player)BaseScene.getTopScene().getObjectsAt(MainScene.Layer.player).get(0);

    }

    public boolean onTouch(int action, float gx, float gy) {

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                handleJoystickMovement(gx, gy);
                player.isWalk = true;
                break;
            case MotionEvent.ACTION_UP:
                dx = 0;
                dy = 0;
                moveTo(centerX, centerY);
                player.isWalk = false;
                break;
        }

        return true;
    }

    private float dx = 0f;
    private float dy = 0f;

    private void handleJoystickMovement(float gx, float gy) {

        dx = gx - centerX;
        dy = gy - centerY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > maxDistance) {
            // 조이스틱을 최대 이동 반경 내에서 제한
            dx = (dx / distance) * maxDistance;
            dy = (dy / distance) * maxDistance;

            moveTo(centerX + dx, centerY + dy);
        }
        else {
            moveTo(gx, gy);
        }


        // 나머지 게임 요소의 스크롤 처리 (예: 배경, 장애물 등)
    }

    @Override
    public void update(){
        player.move(dx, dy);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rects, dstRect, null);
    }
}
