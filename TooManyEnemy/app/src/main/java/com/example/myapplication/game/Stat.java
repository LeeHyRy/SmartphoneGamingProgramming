package com.example.myapplication.game;

import android.graphics.Canvas;

import com.example.myapplication.R;

import framework.objects.Score;
import framework.objects.Sprite;
import framework.util.Gauge;

public class Stat{

    public int level = 1;
    public float power = 10;
    public float interval = 3.0f;
    public long exp = 0;
    public long levelupExp = 25;
    public float gatherDist = 5.f;
    public int waves = 1;
    public float speed = 1.0f;
    public int maxHp = 100;
    public int hp = 100;

    public Score score;
    public Stat() {

        score = new Score(R.mipmap.gold_number, 17.5f, 0.5f, 1);
        score.setScore(100);
    }

    public void getExp(int ex){
        exp += ex;
        if (levelupExp < exp){
            exp -= levelupExp;
            levelUp();
        }
    }
    private void setLevel(int level) {
        this.level = level;
        this.power = (float) (10 * Math.pow(1.2, level - 1));
        this.interval = 3.0f * (float)(Math.pow(0.90, level - 1));
    }
    private void levelUp(){
        level += 1;
        levelupExp = Math.round(levelupExp * 1.2f);
        healFull();
        new PausedScene(1,this).pushScene();
    }
    public void maxHpUp(){
        this.maxHp *= 1.5f;
    }
    public void powerUp(){
        this.power *= 1.2f;
    }
    public void intervalUp(){
        this.interval *= 0.9f;
    }
    public void gatherDistanceUp(){
        this.gatherDist += 1.0f;
    }
    public void speedUp(){
        this.power += 0.2f;
    }

    public void healFull(){
        this.hp = maxHp;
    }

    public void getDamage(int dmg){
        this.hp -= dmg;
        if (hp <= 0){
            new PausedScene(2,this).pushScene();
        }
    }

}
