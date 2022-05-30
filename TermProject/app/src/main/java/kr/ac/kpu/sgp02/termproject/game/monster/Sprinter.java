package kr.ac.kpu.sgp02.termproject.game.monster;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;

public class Sprinter extends Monster{

    public static Sprinter get(int tileX, int tileY) {
        Sprinter recyclable = (Sprinter) ObjectPool.get(Sprinter.class);

        if(recyclable != null)
            recyclable.redeploy(tileX, tileY);
        else
            recyclable = new Sprinter(tileX, tileY);

        return recyclable;
    }

    protected Sprinter(int x, int y) {
        super(x, y);
    }

    protected void setSpec() {
        sprite = new Sprite(position.x, position.y, size, R.mipmap.monster_sprinter);

        speed = Metrics.size(R.dimen.sprinter_speed);
        maxHp = Metrics.floatValue(R.dimen.sprinter_hp);
        reward = Metrics.floatValue(R.dimen.sprinter_reward);
    }
}
