package kr.ac.kpu.sgp02.termproject.game.tower;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.MissileProjectile;

public class MissileTower extends Tower{

    public static MissileTower get(int x, int y) {
        MissileTower recyclable = (MissileTower) ObjectPool.get(MissileTower.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new MissileTower(x, y);

        return recyclable;
    }

    protected MissileTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        sprite = new Sprite(position.x, position.y, Metrics.size(R.dimen.cell_size), R.mipmap.tower_missile);

        range = new CircleCollider(position.x, position.y,  Metrics.size(R.dimen.missile_range));

        maxDelay = Metrics.floatValue(R.dimen.missile_delay);
    }

    @Override
    protected void fire() {
        MissileProjectile missile = MissileProjectile.get(position.x, position.y);
        DefenseGame.getInstance().add(missile, DefenseGame.Layer.damageCauser);

        Monster monster = targetList.iterator().next();
        missile.setTarget(monster);
    }

    @Override
    public void redeploy(float x, float y) {
        super.redeploy(x, y);
    }
}