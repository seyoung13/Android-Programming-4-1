package kr.ac.kpu.sgp02.termproject.game.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.pool.BitmapPool;
import kr.ac.kpu.sgp02.termproject.framework.pool.Sound;
import kr.ac.kpu.sgp02.termproject.game.system.NumberDisplay;

public class LifePoint implements GameObject {
    private Sprite sprite;
    private int life;
    private int maxLife;
    private PointF position;

    private NumberDisplay numberDisplay;

    public LifePoint() {
        this.life = Metrics.intValue(R.dimen.max_life);
        maxLife = life;

        float life_size = Metrics.size(R.dimen.life_size);
        float marginLeftTop = Metrics.size(R.dimen.ui_margin_left_top);
        float number_size = Metrics.size(R.dimen.life_digit_width);

        position = new PointF(Metrics.width - life_size - marginLeftTop - number_size * 2, number_size/2 + marginLeftTop);

        sprite = new Sprite(position.x, position.y, life_size, R.mipmap.life_point);

        numberDisplay = new NumberDisplay(maxLife, 2, position.x + life_size, position.y, number_size);
    }

    public void restore(int number) {
        life += number;
        life = Math.min(life, maxLife);
        Sound.playSfx(R.raw.jelly);

        numberDisplay.setNumber(life);
    }

    /**
     * 라이프를 감소시키고 사망 처리를 하는 함수.
     * @param number 감소시킬 라이프 수.
     * @return 라이프가 0 보다 많으면 false, 0 이하이면 false
     */
    public boolean decrease(int number) {
        life -= number;
        Sound.playSfx(R.raw.jelly_alphabet);

        if(life > 0) {
            numberDisplay.subNumber(number);
            return false;
        }
        else {
            numberDisplay.setNumber(0);
            return true;
        }
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
        numberDisplay.draw(canvas);
    }
}
