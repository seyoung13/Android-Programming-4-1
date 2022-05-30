package kr.ac.kpu.sgp02.termproject.game.tower;

import java.util.ArrayList;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.PlasmaProjectile;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;

public class PlasmaTower extends Tower{
    ArrayList<PlasmaProjectile> plasmaProjectiles = new ArrayList<>();

    public static PlasmaTower get(int tileX, int tileY) {
        PlasmaTower recyclable = (PlasmaTower) ObjectPool.get(PlasmaTower.class);

        if(recyclable != null)
            recyclable.redeploy(tileX, tileY);
        else
            recyclable = new PlasmaTower(tileX, tileY);

        return recyclable;
    }

    protected PlasmaTower(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setSpecification() {
        sprite = new Sprite(position.x, position.y, Metrics.size(R.dimen.cell_size), R.mipmap.tower_plasma);

        range = new CircleCollider(position.x, position.y, Metrics.size(R.dimen.plasma_range));

        maxDelay = Metrics.floatValue(R.dimen.plasma_delay);
    }

    @Override
    protected void fire() {
        for(Monster monster : targetList) {
            PlasmaProjectile plasma = PlasmaProjectile.get(position.x, position.y);
            plasmaProjectiles.add(plasma);
            DefenseGame.getInstance().add(plasma, DefenseGame.Layer.image);

            plasma.setTarget(monster);
        }
    }

    @Override
    public void redeploy(float x, float y) {
        super.redeploy(x, y);
    }
}
