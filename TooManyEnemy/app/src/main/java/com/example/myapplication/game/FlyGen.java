package com.example.myapplication.game;

import android.graphics.Canvas;

import java.util.Random;

import framework.interfaces.IGameObject;
import framework.scene.BaseScene;


public class FlyGen implements IGameObject {

    private static final float GEN_INTERVAL = 3.0f;
    private static final float MIN_INTERVAL = 0.1f;
    private Random rand = new Random();
    private static final float WAVE_INTERVAL = 30.0f;
    private float time;
    private float interval = GEN_INTERVAL;
    private float waveTime;
    private float waveInteral = WAVE_INTERVAL;
    private float speed = 1.0f;
    private int wave = 0;
    private boolean normalPhase = true;

    @Override
    public void update() {
        waveTime += BaseScene.frameTime;
        if (normalPhase) {
            time += BaseScene.frameTime;
            if (time > interval) {
                spawn(false);
                time -= interval;
                interval *= 0.995;
                if (interval < MIN_INTERVAL) interval = MIN_INTERVAL;
            }
            if (waveTime > waveInteral) {
                spawn(true);
                waveTime = 0;
                normalPhase = false;
            }
        } else {
            if (waveTime > waveInteral || BaseScene.getTopScene().getObjectsAt(MainScene.Layer.monster).size() == 0) {
                waveTime = 0;
                normalPhase = true;
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
        MainScene scene = (MainScene) BaseScene.getTopScene();
        scene.add(MainScene.Layer.monster, fly);
    }

    @Override
    public void draw(Canvas canvas) {
        //Fly.drawPath(canvas);
    }
}