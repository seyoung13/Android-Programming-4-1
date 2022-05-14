package kr.ac.kpu.sgp02.termproject.game.monster;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;

public class Walker extends Monster{

    public static Walker get(float x, float y) {
        Walker recyclable = (Walker) ObjectPool.get(Walker.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new Walker(x, y);

        return recyclable;
    }

    protected Walker(float x, float y) {
        super(x, y);
    }

    @Override
    protected void setSpec() {
        sprite = new Sprite(position.x, position.y, size, R.mipmap.monster_walker);

        speed = Metrics.size(R.dimen.walker_speed);
        maxHp = Metrics.floatValue(R.dimen.walker_hp);
        reward = Metrics.floatValue(R.dimen.walker_reward);
    }
}
