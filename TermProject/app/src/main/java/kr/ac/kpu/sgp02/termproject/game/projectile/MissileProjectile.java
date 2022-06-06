package kr.ac.kpu.sgp02.termproject.game.projectile;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;

public class MissileProjectile extends Projectile {

    public static MissileProjectile get(float x, float y) {
        MissileProjectile recyclable = (MissileProjectile) ObjectPool.get(MissileProjectile.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new MissileProjectile(x, y);

        return recyclable;
    }

    protected MissileProjectile(float x, float y) {
        super(x, y);
        sprite = new Sprite(x, y, Metrics.size(R.dimen.missile_projectile_size), R.mipmap.missile_projectile);
        damage = Metrics.intValue(R.dimen.cannon_damage);
        speed = Metrics.size(R.dimen.missile_projectile_speed);
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
