package kr.ac.kpu.sgp02.termproject.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Sprite implements GameObject {
    private RectF dstRect = new RectF();
    private Bitmap bitmap;
    private PointF position = new PointF();
    private float size;

    public Sprite(float x, float y, float size, int bitmapResId) {
        this.size = size;
        setPosition(x, y);
        bitmap = BitmapPool.getBitmap(bitmapResId);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        dstRect.set(x - size / 2, y - size / 2,
                x + size / 2, y + size / 2);
    }

    public void offset(float dx, float dy) {
        position.offset(dx, dy);
        dstRect.offset(dx, dy);
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
