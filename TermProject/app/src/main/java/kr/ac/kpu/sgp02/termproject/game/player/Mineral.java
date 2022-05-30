package kr.ac.kpu.sgp02.termproject.game.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.pool.BitmapPool;
import kr.ac.kpu.sgp02.termproject.framework.pool.Sound;

public class Mineral implements GameObject {
    private Bitmap bitmap;
    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();
    private int amount;

    private int srcCharWidth, srcCharHeight;
    private float dstCharWidth, dstCharHeight;
    private float top, right;

    public Mineral(int amount) {
        this.amount = amount;

        bitmap = BitmapPool.getBitmap(R.mipmap.numbers_24x32);
        srcCharWidth = bitmap.getWidth()/10;
        srcCharHeight = bitmap.getHeight();

        dstCharWidth = Metrics.size(R.dimen.mineral_digit_width);
        dstCharHeight = Metrics.size(R.dimen.mineral_digit_height);

        top = Metrics.size(R.dimen.mineral_digit_margin_top);
        right = dstCharWidth * 5;
    }

    public void addAmount(int number) {
        Sound.playSfx(R.raw.jelly_coin);

        amount += number;
        amount = Math.min(amount, 9999);
    }

    /**
     * 저장 중인 자원량을 감소시키는 함수.
     * @param number 소모하려는 자원량
     * @return 소모량이 저장량보다 많으면 true, 적으면 false
     */
    public boolean subAmount(int number) {
        if(amount - number < 0) {
            Sound.playSfx(R.raw.jelly_gold);
            return false;
        }
        else {
            amount -= number;
            return true;
        }
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        // 표시할 자릿수 0050
        float fullDigit = 4;

        float x = right;
        int value = amount;
        while (fullDigit-- > 0) {
            int digit = value % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
            x -= dstCharWidth;
            dstRect.set(x, top, x + dstCharWidth, top + dstCharHeight);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            value /= 10;
        }
    }
}
