package kr.ac.kpu.sgp02.termproject.game.monster;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;

public class Sprinter extends Monster{

    public static Sprinter get(float x, float y) {
        Sprinter recyclable = (Sprinter) ObjectPool.get(Sprinter.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new Sprinter(x, y);

        return recyclable;
    }

    protected Sprinter(float x, float y) {
        super(x, y);
    }

    protected void setSpec() {
        sprite = new Sprite(position.x, position.y, size, R.mipmap.monster_sprinter);

        speed = Metrics.size(R.dimen.sprinter_speed);
        maxHp = Metrics.floatValue(R.dimen.sprinter_hp);
        reward = Metrics.floatValue(R.dimen.sprinter_reward);
    }
}
