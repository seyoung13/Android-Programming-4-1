package kr.ac.kpu.sgp02.termproject.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Sprite implements GameObject {
    private RectF dstRect = new RectF();
    private Bitmap bitmap;
    private PointF position = new PointF();

    public Sprite(float x, float y, float size, int bitmapResId) {
        setPosition(x, y, size, size);
        bitmap = BitmapPool.getBitmap(bitmapResId);
    }

    public Sprite(float x, float y, float width, float height, int bitmapResId) {
        setPosition(x, y, width, height);
        bitmap = BitmapPool.getBitmap(bitmapResId);
    }

    public void setPosition(float x, float y, float width, float height) {
        position.set(x, y);
        dstRect.set(x - width / 2, y - height / 2,
                x + width / 2, y + height / 2);
    }

    public void setPositionLeftTop(float x, float y, float width, float height) {
        position.set(x + width/2, y + height/2);
        dstRect.set(x, y, x + width, y + height);
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
