package kr.ac.kpu.sgp02.termproject.defense.tower;

import java.util.ArrayList;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.defense.Monster;
import kr.ac.kpu.sgp02.termproject.defense.projectile.LaserProjectile;
import kr.ac.kpu.sgp02.termproject.defense.projectile.PlasmaProjectile;
import kr.ac.kpu.sgp02.termproject.defense.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;

public class PlasmaTower extends Tower{
    ArrayList<PlasmaProjectile> plasmaProjectiles = new ArrayList<>();

    public PlasmaTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        sprite = new Sprite(position.x, position.y,
                Metrics.size(R.dimen.cell_size) , R.mipmap.tower_sample);

        range = new CircleCollider(position.x, position.y, Metrics.size(R.dimen.plasma_range));

        maxDelay = Metrics.floatValue(R.dimen.plasma_delay);
    }

    @Override
    protected void fire() {
        for(Monster monster : targetList) {
            PlasmaProjectile plasma = new PlasmaProjectile(position.x, position.y);
            plasmaProjectiles.add(plasma);
            GameView.view.add(plasma);

            plasma.setTarget(monster);
        }
    }
}
