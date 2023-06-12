package com.example.myapplication.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.myapplication.R;

import framework.interfaces.IRecyclable;
import framework.objects.Sprite;
import framework.scene.RecycleBin;

public class Exporb extends Sprite implements IRecyclable {
    private static final int gap = 264/11;
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
    }

    public static Exporb get(Fly target) {
        Exporb exporb = (Exporb) RecycleBin.get(Exporb.class);
        if (exporb == null) {
            exporb = new Exporb();
        }
        //exporb.init(player, target);
        return exporb;
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
    @Override
    public void onRecycle() {

    }
}
