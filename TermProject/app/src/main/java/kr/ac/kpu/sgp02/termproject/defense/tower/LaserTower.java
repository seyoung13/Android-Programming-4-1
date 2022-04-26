package kr.ac.kpu.sgp02.termproject.defense.tower;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.defense.Monster;
import kr.ac.kpu.sgp02.termproject.defense.projectile.LaserProjectile;
import kr.ac.kpu.sgp02.termproject.defense.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;

public class LaserTower extends Tower {
    public boolean isLaserFiring = false;
    public LaserProjectile laser;

    public LaserTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        sprite = new Sprite(position.x, position.y,
                Metrics.size(R.dimen.cell_size) , R.mipmap.tower_sample);

        range = new CircleCollider(position.x, position.y, Metrics.size(R.dimen.laser_range));

        maxDelay = Metrics.floatValue(R.dimen.laser_delay);

        laser = new LaserProjectile(position.x, position.y);
    }

    @Override
    protected void fire() {
        if(isLaserFiring)
            return;

        isLaserFiring = true;

        GameView.view.add(laser);
        target = targetList.iterator().next();
        laser.setTarget(target);
    }

    @Override
    public void onEndOverlap(GameObject object) {
        if(!(object instanceof Monster))
            return;

        Monster monster = (Monster) object;
        if(monster == target) {
            isLaserFiring = false;
            GameView.view.remove(laser);
        }

        targetList.remove((Monster) object);
    }
}
