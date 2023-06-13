package com.example.myapplication.game;



import android.content.Context;

import framework.objects.Button;
import framework.objects.Score;
import framework.objects.Sprite;
import framework.res.Sound;
import framework.scene.BaseScene;

import com.example.myapplication.R;
import com.example.myapplication.TitleActivity;

public class PausedScene extends BaseScene {

    private float angle;

    public enum Layer {
        bg, score, touch, COUNT
    }
    public PausedScene(int casenum, Stat stat) {
        switch(casenum) {
            case 1:
            initLayers(Layer.COUNT);
            add(Layer.bg, new Sprite(R.mipmap.trans_50b, 9.0f, 16f, 18, 32));
            add(Layer.bg, new Sprite(R.mipmap.statui, 9.0f, 16f, 17.2f, 12));
            add(Layer.touch, new Button(R.mipmap.btn_up, 7f, 13.5f, 3, 2, new Button.Callback() {
                @Override
                public boolean onTouch(Button.Action action) {
                    if (action == Button.Action.pressed) {
                        stat.maxHpUp();
                        popScene();
                        stat.healFull();
                    }
                    return false;
                }
            }));
            add(Layer.touch, new Button(R.mipmap.btn_up, 11f, 13.5f, 3, 2, new Button.Callback() {
                @Override
                public boolean onTouch(Button.Action action) {
                    if (action == Button.Action.pressed) {
                        stat.gatherDistanceUp();
                        popScene();
                    }
                    return false;
                }
            }));
            add(Layer.touch, new Button(R.mipmap.btn_up, 7f, 16f, 3, 2, new Button.Callback() {
                @Override
                public boolean onTouch(Button.Action action) {
                    if (action == Button.Action.pressed) {
                        stat.powerUp();
                        popScene();
                    }
                    return false;
                }
            }));
            add(Layer.touch, new Button(R.mipmap.btn_up, 11f, 16f, 3, 2, new Button.Callback() {
                @Override
                public boolean onTouch(Button.Action action) {
                    if (action == Button.Action.pressed) {
                        stat.intervalUp();
                        popScene();
                    }
                    return false;
                }
            }));
            add(Layer.touch, new Button(R.mipmap.btn_up, 7f, 18.5f, 3, 2, new Button.Callback() {
                @Override
                public boolean onTouch(Button.Action action) {
                    if (action == Button.Action.pressed) {
                        stat.speedUp();
                        popScene();
                    }
                    return false;
                }
            }));
            add(Layer.touch, new Button(R.mipmap.btn_ok, 13, 18.5f, 3, 2, new Button.Callback() {
                @Override
                public boolean onTouch(Button.Action action) {
                    if (action == Button.Action.pressed) {
                        popScene();
                    }
                    return false;
                }
            }));
            break;
            default:
                Sound.stopMusic();
                Sound.playMusic(R.raw.gameover);
                initLayers(Layer.COUNT);
                add(Layer.bg, new Sprite(R.mipmap.trans_50b, 9.0f, 16f, 18, 32));
                add(Layer.bg, new Sprite(R.mipmap.gameoverui, 9.0f, 16f, 17.2f, 12));
                Score nscore = new Score(R.mipmap.gold_number, 11.0f, 17f, 1);
                nscore.setScore(stat.score.getScore());
                add(Layer.score, nscore);
                TitleActivity ta = ((TitleActivity)TitleActivity.context_title);
                int score = stat.score.getScore();
                if (ta.getHighScore() < score) {
                    ta.saveHighScore(score);
                    ta.setHighScore();
                }
                add(Layer.touch, new Button(R.mipmap.btn_ok, 13, 18.5f, 3, 2, new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if (action == Button.Action.pressed) {
                            Sound.stopMusic();
                            finishActivity();
                        }
                        return false;
                    }
                }));
                break;

        }
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public void update(long elapsedNanos) {
        super.update(elapsedNanos);
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
