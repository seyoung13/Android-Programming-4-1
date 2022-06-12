package kr.ac.kpu.sgp02.termproject.game.tower;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collider;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.pool.Sound;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.MissileProjectile;

public class MissileTower extends Tower{

    public static MissileTower get(int tileX, int tileY) {
        MissileTower recyclable = (MissileTower) ObjectPool.get(MissileTower.class);

        if(recyclable != null)
            recyclable.redeploy(tileX, tileY);
        else
            recyclable = new MissileTower(tileX, tileY);

        return recyclable;
    }

    protected MissileTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        towerHead = new Sprite(position.x, position.y, Metrics.size(R.dimen.tower_head_size), R.mipmap.missile_head);
        range = new CircleCollider(position.x, position.y,  Metrics.size(R.dimen.missile_range));
        maxDelay = Metrics.floatValue(R.dimen.missile_delay);
        cost = Metrics.intValue(R.dimen.missile_cost);
    }

    @Override
    protected void fire() {
        MissileProjectile missile = MissileProjectile.get(position.x, position.y);
        DefenseGame.getInstance().add(missile, DefenseGame.Layer.damageCauser);
        Sound.playSfx(R.raw.missile);

        Monster monster = targetList.iterator().next();
        missile.setTarget(monster);
    }

    @Override
    public void redeploy(float x, float y) {
        super.redeploy(x, y);
        towerHead.setPosition(position.x, position.y);
        range.set(position.x, position.y);
        range.radius = Metrics.size(R.dimen.missile_range);
        maxDelay = Metrics.floatValue(R.dimen.missile_delay);
    }

    @Override
    public <T extends Collider> T getCollider(Class<T> type) {
        return type.cast(range);
    }
}
