package kr.ac.kpu.sgp02.termproject.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.pool.BitmapPool;

public class NumberDisplay implements GameObject {
    protected int number;
    protected int digitCount;
    protected static Bitmap bitmap;

    protected PointF position = new PointF();
    private PointF inset = new PointF();

    protected Rect srcRect = new Rect();
    protected RectF dstRect = new RectF();

    protected int srcCharWidth;
    protected int srcCharHeight;
    protected float dstCharWidth;
    protected float dstCharHeight;

    private boolean isScalable;

    static {
        bitmap = BitmapPool.getBitmap(R.mipmap.numbers_24x32);
    }

    public NumberDisplay(int number, int digitCount, float x, float y, float size, boolean isScalable) {
        this.number = number;
        this.digitCount = digitCount;

        srcCharWidth = bitmap.getWidth()/10;
        srcCharHeight = bitmap.getHeight();

        dstCharWidth = size;
        dstCharHeight = srcCharHeight * dstCharWidth / srcCharWidth;

        this.isScalable = isScalable;

        setPosition(x, y);
    }

    public void setNumber(int number){
        scaleUpInset();
        this.number = number;
    }

    public void addNumber(int number) {
        scaleUpInset();
        this.number += number;
    }

    public void subNumber(int number) {
        scaleUpInset();
        this.number -= number;
    }

    public void setPosition(float x, float y){
        position.set(x, y);
    }

    public void offset(float dx, float dy) {
        position.offset(dx, dy);
    }

    private void scaleUpInset() {
        if(!isScalable)
            return;

        inset.x = -dstCharWidth * 0.2f;
        inset.y = -dstCharWidth * 0.2f;
    }

    @Override
    public void update(float deltaSecond) {
        if(!isScalable)
            return;

        if(inset.x < 0) {
            inset.x += dstCharWidth * deltaSecond;
            inset.x = Math.min(inset.x, 0);
        }

        if(inset.y < 0) {
            inset.y += dstCharWidth * deltaSecond;
            inset.y = Math.min(inset.y, 0);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        float x = position.x + dstCharWidth * digitCount;
        int value = number;
        int displayedDigitCount = digitCount;

        while (displayedDigitCount-- > 0) {
            int digit = value % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
            x -= dstCharWidth;
            dstRect.set(x - dstCharWidth/2, position.y - dstCharHeight/2,
                    x + dstCharWidth/2, position.y + dstCharHeight/2);
            dstRect.inset(inset.x, inset.y);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            value /= 10;
        }
    }
}
