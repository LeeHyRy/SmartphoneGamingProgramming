package framework.util;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

import framework.view.GameView;


public class Gauge {
    private Paint fgPaint = new Paint();
    private Paint bgPaint = new Paint();
    public Gauge(float width, int fgColorResId, int bgColorResId) {
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(width);
        // Gauge 생성 시점이 GameView.res 가 설정된 이후여야 한다.
        Resources res = GameView.view.getResources();
        bgPaint.setColor(ResourcesCompat.getColor(res, bgColorResId, null));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(width / 2);
        fgPaint.setColor(ResourcesCompat.getColor(res, fgColorResId, null));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
    }
    public void draw(Canvas canvas, float value, float length) {
        canvas.drawLine(0, 0, length, 0.0f, bgPaint);
        if (value > 0) {
            canvas.drawLine(0, 0, length * value, 0.0f, fgPaint);
        }
    }

    public void draw(Canvas canvas, float x, float y, float scale, float length, float value) {
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scale, scale);
        draw(canvas, value, length);
        canvas.restore();
    }
}
