package kr.ac.kpu.sgp02.termproject.game.tower;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;

public class CannonTower extends Tower {
    public static CannonTower get(int tileX, int tileY) {
        CannonTower recyclable = (CannonTower) ObjectPool.get(CannonTower.class);

        if(recyclable != null)
            recyclable.redeploy(tileX, tileY);
        else
            recyclable = new CannonTower(tileX, tileY);

        return recyclable;
    }

    protected CannonTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        sprite = new Sprite(position.x, position.y, Metrics.size(R.dimen.cell_size), R.mipmap.tower_cannon);

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
        super.redeploy(x, y);
    }
}
