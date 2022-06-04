package kr.ac.kpu.sgp02.termproject.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.pool.BitmapPool;

public class NumberDisplay implements GameObject {
    private int number;
    private int digitCount;
    private Bitmap bitmap;
    private Paint paint;

    private PointF position = new PointF();

    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();

    private int srcCharWidth, srcCharHeight;
    private float dstCharWidth, dstCharHeight;

    public NumberDisplay(int number, int digitCount, float x, float y, float size) {
        this.number = number;
        this.digitCount = digitCount;

        bitmap = BitmapPool.getBitmap(R.mipmap.numbers_24x32);

        srcCharWidth = bitmap.getWidth()/10;
        srcCharHeight = bitmap.getHeight();

        dstCharWidth = size;
        dstCharHeight = srcCharHeight * dstCharWidth / srcCharWidth;

        paint = new Paint();

        setPosition(x, y);
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void addNumber(int number) {
        this.number += number;
    }

    public void subNumber(int number) {
        this.number -= number;
    }

    public void setPosition(float x, float y){
        position.set(x, y);
    }

    public void offset(float dx, float dy) {
        position.offset(dx, dy);
    }

    public void setAlpha(int percent) {
        int alpha = percent / 100 * 255;

        paint.setAlpha(alpha);
    }

    @Override
    public void update(float deltaSecond) {

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
            canvas.drawBitmap(bitmap, srcRect, dstRect, paint);
            value /= 10;
        }
    }
}
