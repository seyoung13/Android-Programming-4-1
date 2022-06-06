package kr.ac.kpu.sgp02.termproject.game.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;

public class FloatingNumberDisplay extends NumberDisplay implements Recyclable {
    private float opacityPercent;

    public static FloatingNumberDisplay get(int number, int digitCount, float x, float y, int color){
        FloatingNumberDisplay recyclable = (FloatingNumberDisplay) ObjectPool.get(FloatingNumberDisplay.class);

        if(recyclable != null) {
            recyclable.redeploy(x, y);
            recyclable.setNumber(number);
            recyclable.setDigitCount(digitCount);
            recyclable.setColor(color);
        }
        else
            recyclable = new FloatingNumberDisplay(number, digitCount, x, y, color);

        return recyclable;
    }

    public FloatingNumberDisplay(int number, int digitCount, float x, float y, int color) {
        super(number, digitCount, x, y, Metrics.size(R.dimen.floating_number_size), color, false);
        opacityPercent = 100;
        setOpacity(opacityPercent);
    }

    private void setOpacity(float percent) {
        float opacity = percent/100 * 255;

        paint.setAlpha((int)opacity);
    }

    private void setDigitCount(int digitCount){
        this.digitCount = digitCount;
    }

    @Override
    public void update(float deltaSecond) {
        if(opacityPercent < 0) {
            DefenseGame.getInstance().remove(this);
            return;
        }

        position.y -= Metrics.size(R.dimen.number_floating_speed) * deltaSecond;
        opacityPercent -= 75 * deltaSecond;
        opacityPercent = Math.max(opacityPercent, 0);

        setOpacity(opacityPercent);
    }

    @Override
    public void draw(Canvas canvas) {
        float x = position.x + dstCharWidth * digitCount;
        int value = number;

        while (value > 0) {
            int digit = value % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
            x -= dstCharWidth;
            dstRect.set(x - dstCharWidth/2, position.y - dstCharHeight/2,
                    x + dstCharWidth/2, position.y + dstCharHeight/2);
            canvas.drawBitmap(bitmap, srcRect, dstRect, paint);
            value /= 10;
        }
    }

    @Override
    public void redeploy(float x, float y) {
        position = new PointF(x, y);
        opacityPercent = 100;
    }
}
