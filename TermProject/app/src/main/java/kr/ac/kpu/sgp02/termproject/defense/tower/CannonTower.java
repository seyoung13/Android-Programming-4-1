package kr.ac.kpu.sgp02.termproject.defense.tower;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.defense.Monster;
import kr.ac.kpu.sgp02.termproject.defense.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;

public class CannonTower extends Tower {
    public CannonTower(int x, int y) {
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
        Projectile cannonball = new Projectile(position.x, position.y);
        GameView.view.add(cannonball);

        Monster monster = targetList.iterator().next();
        cannonball.setTarget(monster);
    }
}