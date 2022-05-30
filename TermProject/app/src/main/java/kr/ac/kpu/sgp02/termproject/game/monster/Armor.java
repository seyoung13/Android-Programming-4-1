package kr.ac.kpu.sgp02.termproject.game.monster;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;

public class Armor extends Monster {

    public static Armor get(int tileX, int tileY) {
        Armor recyclable = (Armor) ObjectPool.get(Armor.class);

        if(recyclable != null)
            recyclable.redeploy(tileX, tileY);
        else
            recyclable = new Armor(tileX, tileY);

        return recyclable;
    }

    protected Armor(int x, int y) {
        super(x, y);
    }

    protected void setSpec() {
        sprite = new Sprite(position.x, position.y, size, R.mipmap.monster_armor);

        speed = Metrics.size(R.dimen.armor_speed);
        maxHp = Metrics.floatValue(R.dimen.armor_hp);
        reward = Metrics.floatValue(R.dimen.armor_reward);
    }
}
