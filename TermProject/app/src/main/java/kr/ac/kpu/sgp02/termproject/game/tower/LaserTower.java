package kr.ac.kpu.sgp02.termproject.game.tower;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collider;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.pool.Sound;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.LaserProjectile;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;

public class LaserTower extends Tower {
    public static LaserTower get(int tileX, int tileY) {
        LaserTower recyclable = (LaserTower) ObjectPool.get(LaserTower.class);

        if(recyclable != null)
            recyclable.redeploy(tileX, tileY);
        else
            recyclable = new LaserTower(tileX, tileY);

        return recyclable;
    }

    protected LaserTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        towerHead = new Sprite(position.x, position.y, Metrics.size(R.dimen.tower_head_size), R.mipmap.laser_head);
        range = new CircleCollider(position.x, position.y, Metrics.size(R.dimen.laser_range));
        maxDelay = Metrics.floatValue(R.dimen.laser_delay);
        cost = Metrics.intValue(R.dimen.laser_cost);
    }

    @Override
    protected void fire() {
        LaserProjectile laser = LaserProjectile.get(position.x, position.y);
        DefenseGame.getInstance().add(laser, DefenseGame.Layer.damageCauser);
        Sound.playSfx(R.raw.laser_beam);

        Monster monster = targetList.iterator().next();
        laser.setTarget(monster);
    }

    @Override
    public <T extends Collider> T getCollider(Class<T> type) {
        return type.cast(range);
    }

    @Override
    public void redeploy(float x, float y) {
        super.redeploy(x, y);
        towerHead.setPosition(position.x, position.y);
        range.set(position.x, position.y);
        range.radius = Metrics.size(R.dimen.laser_range);
        maxDelay = Metrics.floatValue(R.dimen.laser_delay);
    }
}
