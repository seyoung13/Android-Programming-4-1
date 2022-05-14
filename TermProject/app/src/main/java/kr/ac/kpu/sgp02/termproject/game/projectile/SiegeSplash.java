package kr.ac.kpu.sgp02.termproject.game.projectile;

import android.graphics.Canvas;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;

public class SiegeSplash implements GameObject, Collidable, Recyclable {
    public CircleCollider collider;
    public float lifetime = 0.5f;
    private int damage = 20;

    public static SiegeSplash get(float x, float y) {
        SiegeSplash recyclable = (SiegeSplash) ObjectPool.get(SiegeSplash.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new SiegeSplash(x, y);

        return recyclable;
    }

    protected SiegeSplash(float x, float y) {
        collider = new CircleCollider(x, y, Metrics.size(R.dimen.missile_splash_range));
    }


    @Override
    public void update(float deltaSecond) {
        lifetime -= deltaSecond;

        if(lifetime <= 0) {
            DefenseGame.getInstance().remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        collider.draw(canvas);
    }


    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Monster) {
            Monster monster = (Monster) object;
            monster.beDamaged(damage);
        }
    }

    @Override
    public void onStayOverlap(GameObject object) {

    }

    @Override
    public void onEndOverlap(GameObject object) {

    }

    @Override
    public void redeploy(float x, float y) {
        collider.set(x, y);
    }
}
