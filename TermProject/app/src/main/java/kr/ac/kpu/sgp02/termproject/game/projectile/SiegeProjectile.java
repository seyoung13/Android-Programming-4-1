package kr.ac.kpu.sgp02.termproject.game.projectile;

import android.util.Log;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.Monster;

public class SiegeProjectile extends Projectile {
    private CircleCollider splash;

    public static SiegeProjectile get(float x, float y) {
        SiegeProjectile recyclable = (SiegeProjectile) ObjectPool.get(SiegeProjectile.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new SiegeProjectile(x, y);

        return recyclable;
    }

    protected SiegeProjectile(float x, float y) {
        super(x, y);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Monster) {
            Monster monster = (Monster) object;
            if(object == target) {
                DefenseGame.getInstance().add(new SiegeSplash(position.x, position.y),
                        DefenseGame.Layer.damageCauser);

                DefenseGame.getInstance().remove(this);
            }
        }
    }
}
