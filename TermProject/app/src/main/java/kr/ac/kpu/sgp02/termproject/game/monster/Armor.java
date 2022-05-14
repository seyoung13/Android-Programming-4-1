package kr.ac.kpu.sgp02.termproject.game.monster;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;

public class Armor extends Monster {

    public static Armor get(float x, float y) {
        Armor recyclable = (Armor) ObjectPool.get(Armor.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new Armor(x, y);

        return recyclable;
    }

    protected Armor(float x, float y) {
        super(x, y);
    }

    protected void setSpec() {
        sprite = new Sprite(position.x, position.y, size, R.mipmap.monster_armor);

        speed = Metrics.size(R.dimen.armor_speed);
        maxHp = Metrics.floatValue(R.dimen.armor_hp);
        reward = Metrics.floatValue(R.dimen.armor_reward);
    }
}
