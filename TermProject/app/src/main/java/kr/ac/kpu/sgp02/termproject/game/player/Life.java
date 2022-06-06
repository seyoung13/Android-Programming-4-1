package kr.ac.kpu.sgp02.termproject.game.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.pool.Sound;
import kr.ac.kpu.sgp02.termproject.game.system.NumberDisplay;

public class Life implements GameObject {
    private Sprite sprite;
    private int life;
    private int maxLife;
    private PointF position;

    private NumberDisplay numberDisplay;

    public Life() {
        this.life = Metrics.intValue(R.dimen.max_life);
        maxLife = life;

        float lifeSize = Metrics.size(R.dimen.life_size);
        float marginLeftTop = Metrics.size(R.dimen.ui_margin_left_top);
        float numberSize = Metrics.size(R.dimen.life_digit_width);

        position = new PointF(Metrics.width - lifeSize - marginLeftTop - numberSize * 2, numberSize/2 + marginLeftTop);

        sprite = new Sprite(position.x, position.y, lifeSize, R.mipmap.life);

        numberDisplay = new NumberDisplay(maxLife, 2, position.x + lifeSize, position.y, numberSize, Color.GREEN, true);
    }

    public void restore(int number) {
        life += number;
        life = Math.min(life, maxLife);

        if(life > maxLife/2)
            numberDisplay.setColor(Color.GREEN);

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

        if(life <= maxLife/2)
            numberDisplay.setColor(Color.RED);

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
        numberDisplay.update(deltaSecond);
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
        numberDisplay.draw(canvas);
    }
}
