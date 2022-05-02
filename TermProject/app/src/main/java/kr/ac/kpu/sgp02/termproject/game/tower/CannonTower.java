package kr.ac.kpu.sgp02.termproject.game.tower;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.Recyclable;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;

public class CannonTower extends Tower {
    public static CannonTower get(int x, int y) {
        CannonTower recyclable = (CannonTower) ObjectPool.get(CannonTower.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new CannonTower(x, y);

        return recyclable;
    }

    protected CannonTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        sprite = new Sprite(position.x, position.y,
                Metrics.size(R.dimen.cell_size), R.mipmap.tower_sample);

        range = new CircleCollider(position.x, position.y,  Metrics.size(R.dimen.cannon_range));

        maxDelay = Metrics.floatValue(R.dimen.cannon_delay);
    }

    @Override
    protected void fire()  {
        Projectile cannonball = Projectile.get(position.x, position.y);
        DefenseGame.getInstance().add(cannonball, DefenseGame.Layer.damageCauser);

        Monster monster = targetList.iterator().next();
        cannonball.setTarget(monster);
    }

    @Override
    public void redeploy(float x, float y) {
        position.set(x, y);
        range.set(x, y);
    }
}
