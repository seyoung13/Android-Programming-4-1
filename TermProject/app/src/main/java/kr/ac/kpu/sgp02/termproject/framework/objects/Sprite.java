package kr.ac.kpu.sgp02.termproject.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.pool.BitmapPool;

public class Sprite implements GameObject {
    private RectF dstRect = new RectF();
    private Bitmap bitmap;
    private PointF position = new PointF();
    private float width, height;
    private Paint alphaPaint;

    public Sprite(float x, float y, float size, int bitmapId) {
        setPosition(x, y, size, size);
        bitmap = BitmapPool.getBitmap(bitmapId);

        alphaPaint = new Paint();

        width = size;
        height = size;
    }

    public Sprite(float x, float y, float width, float height, int bitmapId) {
        this.width = width;
        this.height = height;

        setPosition(x, y, width, height);
        bitmap = BitmapPool.getBitmap(bitmapId);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        dstRect.set(x - width / 2, y - height / 2,
                x + width / 2, y + height / 2);
    }

    public void setPosition(float x, float y, float width, float height) {
        position.set(x, y);
        dstRect.set(x - width / 2, y - height / 2,
                x + width / 2, y + height / 2);
    }

    public void setPositionLeftTop(float left, float top, float width, float height) {
        position.set(left + width/2, top + height/2);
        dstRect.set(left, top, left + width, top + height);
    }

    public void offset(float dx, float dy) {
        position.offset(dx, dy);
        dstRect.offset(dx, dy);
    }

    public void setOpacity(float percent) {
        int opacity = (int)(percent/100 * 255);

        alphaPaint.setAlpha(opacity);
    }

    public void setBitmap(int bitmapId) {
        bitmap = BitmapPool.getBitmap(bitmapId);
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, alphaPaint);
    }
}
