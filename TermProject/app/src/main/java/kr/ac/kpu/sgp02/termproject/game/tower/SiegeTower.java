package kr.ac.kpu.sgp02.termproject.game.tower;

import android.util.Log;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.PlasmaProjectile;
import kr.ac.kpu.sgp02.termproject.game.projectile.SiegeProjectile;

public class SiegeTower extends Tower{

    public static SiegeTower get(int x, int y) {
        SiegeTower recyclable = (SiegeTower) ObjectPool.get(SiegeTower.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new SiegeTower(x, y);

        return recyclable;
    }

    protected SiegeTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        sprite = new Sprite(position.x, position.y,
                Metrics.size(R.dimen.cell_size), R.mipmap.tower_sample);

        range = new CircleCollider(position.x, position.y,  Metrics.size(R.dimen.siege_range));

        maxDelay = Metrics.floatValue(R.dimen.siege_delay);
    }

    @Override
    protected void fire() {
        SiegeProjectile cannonball = SiegeProjectile.get(position.x, position.y);
        DefenseGame.getInstance().add(cannonball, DefenseGame.Layer.damageCauser);

        Monster monster = targetList.iterator().next();
        cannonball.setTarget(monster);
    }

    @Override
    public void redeploy(float x, float y) {
        super.redeploy(x, y);
    }
}
