package kr.ac.kpu.sgp02.termproject.game.player;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.pool.Sound;
import kr.ac.kpu.sgp02.termproject.game.system.NumberDisplay;

public class Mineral implements GameObject {
    private int amount;
    private int maxAmount;

    private PointF position;
    private NumberDisplay numberDisplay;

    public Mineral(int amount) {
        this.amount = amount;
        maxAmount = Metrics.intValue(R.dimen.max_mineral);

        float size = Metrics.size(R.dimen.mineral_digit_width);
        float marginLeftTop = Metrics.size(R.dimen.ui_margin_left_top);

        position = new PointF(size/2 + marginLeftTop, size/2 + marginLeftTop);

        numberDisplay = new NumberDisplay(amount, 4, position.x, position.y, size);
    }

    public void addAmount(int number) {
        Sound.playSfx(R.raw.jelly_coin);

        amount += number;
        amount = Math.min(amount, maxAmount);

        numberDisplay.setNumber(amount);
    }

    /**
     * 저장 중인 자원량을 감소시키는 함수.
     * @param number 소모하려는 자원량.
     * @return 저장량이 소모량보다 많아 뺄 수 있으면 true, 아니면 false.
     */
    public boolean subAmount(int number) {
        if(amount - number < 0) {
            Sound.playSfx(R.raw.jelly_gold);
            return false;
        }
        else {
            amount -= number;
            numberDisplay.subNumber(number);
            return true;
        }
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        numberDisplay.draw(canvas);
    }
}
