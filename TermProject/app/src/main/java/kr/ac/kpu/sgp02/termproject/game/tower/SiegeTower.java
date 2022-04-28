package kr.ac.kpu.sgp02.termproject.game.tower;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.game.projectile.SiegeProjectile;

public class SiegeTower extends Tower{
    public SiegeTower(int x, int y) {
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
        SiegeProjectile cannonball = new SiegeProjectile(position.x, position.y);
        DefenseGame.getInstance().add(cannonball);

        Monster monster = targetList.iterator().next();
        cannonball.setTarget(monster);
    }
}
