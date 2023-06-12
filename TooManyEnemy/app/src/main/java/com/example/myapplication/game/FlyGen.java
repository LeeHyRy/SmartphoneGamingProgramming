package com.example.myapplication.game;

import android.graphics.Canvas;

import java.util.Random;

import framework.interfaces.IGameObject;
import framework.scene.BaseScene;


public class FlyGen implements IGameObject {

    private static final float GEN_INTERVAL = 3.0f;
    private static final float MIN_INTERVAL = 0.1f;
    private Random rand = new Random();
    private Stat stat = ((Player)BaseScene.getTopScene().getObjectsAt(MainScene.Layer.player).get(0)).stat;
    private static final float WAVE_INTERVAL = 30.0f;
    private float time;
    private float interval = GEN_INTERVAL;
    private float waveTime;
    private float waveInteral = WAVE_INTERVAL;
    private float speed = 1.0f;
    private boolean normalPhase = true;
    private MainScene scene = (MainScene) BaseScene.getTopScene();

    @Override
    public void update() {
        waveTime += BaseScene.frameTime;
        if (normalPhase) {
            time += BaseScene.frameTime;
            if (time > interval) {
                spawn(false);
                time -= interval;
            }
            if (waveTime > waveInteral) {
                spawn(true);
                waveTime = 0;
                interval *= 0.98;
                if (interval < MIN_INTERVAL) interval = MIN_INTERVAL;
                normalPhase = false;
            }
        } else {
            if (waveTime > waveInteral && BaseScene.getTopScene().getObjectsAt(MainScene.Layer.monster).size() == 0) {
                waveTime = 0;
                normalPhase = true;
                stat.waves += 1;
            }
        }
    }

    private void spawn(boolean boss) {
        float size = rand.nextFloat() + 1.5f;
        float speed = (float) (this.speed * (rand.nextFloat() * 0.2 + 0.9));
        Fly.Type type;
        if (boss) {
            type = Fly.Type.boss;
            size *= 1.5;
        } else {
            type = Fly.Type.RANDOM;
        }
        Fly fly = Fly.get(type, speed, size);

        int randomEdge = (int) (Math.random() * 4);
        float spawnX, spawnY;
        switch (randomEdge) {
            case 0:  // 왼쪽 변
                spawnX = 0;
                spawnY = 0 + (float) (Math.random() * 18);
                break;
            case 1:  // 위쪽 변
                spawnX = 0 + (float) (Math.random() * 32);
                spawnY = 0;
                break;
            case 2:  // 오른쪽 변
                spawnX = 18;
                spawnY = 0 + (float) (Math.random() * 18);
                break;
            case 3:  // 아래쪽 변
                spawnX = 0 + (float) (Math.random() * 32);
                spawnY = 32;
                break;
            default:
                spawnX= 0;
                spawnY = 0;
                break;

        }
        fly.moveTo(spawnX, spawnY);


        scene.add(MainScene.Layer.monster, fly);
    }

    @Override
    public void draw(Canvas canvas) {
        //Fly.drawPath(canvas);
    }
}
