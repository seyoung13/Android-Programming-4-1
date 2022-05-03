package kr.ac.kpu.sgp02.termproject.game.tower;

import android.util.Log;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.LaserProjectile;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;

public class LaserTower extends Tower {
    public boolean isLaserFiring = false;
    public LaserProjectile laser;

    public static LaserTower get(int x, int y) {
        LaserTower recyclable = (LaserTower) ObjectPool.get(LaserTower.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new LaserTower(x, y);

        return recyclable;
    }

    protected LaserTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        sprite = new Sprite(position.x, position.y,
                Metrics.size(R.dimen.cell_size) , R.mipmap.tower_sample);

        range = new CircleCollider(position.x, position.y, Metrics.size(R.dimen.laser_range));

        maxDelay = Metrics.floatValue(R.dimen.laser_delay);
    }

    @Override
    protected void fire() {
        if(target != null)
            return;

        isLaserFiring = true;
        laser = LaserProjectile.get(position.x, position.y);
        DefenseGame.getInstance().add(laser, DefenseGame.Layer.image);
        target = targetList.iterator().next();
        laser.setTarget(target);
    }

    @Override
    public void onEndOverlap(GameObject object) {
        if(!(object instanceof Monster))
            return;

        Monster monster = (Monster) object;
        if(monster == target) {
            DefenseGame.getInstance().remove(laser);
            laser.setTarget(null);
            target = null;
            if(target == null)
                Log.d("Tower", "target set null");
            else
                Log.d("Tower", "target set not null");
        }

        targetList.remove((Monster) object);
    }

    @Override
    public void redeploy(float x, float y) {

    }
}
