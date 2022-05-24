package kr.ac.kpu.sgp02.termproject.game.monster;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;

public class Walker extends Monster{

    public static Walker get(int tileX, int tileY) {
        Walker recyclable = (Walker) ObjectPool.get(Walker.class);

        if(recyclable != null)
            recyclable.redeploy(tileX, tileY);
        else
            recyclable = new Walker(tileX, tileY);

        return recyclable;
    }

    protected Walker(int x, int y) {
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
