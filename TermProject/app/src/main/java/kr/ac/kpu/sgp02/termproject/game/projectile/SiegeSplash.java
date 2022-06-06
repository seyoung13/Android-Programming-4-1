package kr.ac.kpu.sgp02.termproject.game.projectile;

import android.graphics.Canvas;
import android.graphics.Paint;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collider;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Collidable;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;

public class SiegeSplash implements GameObject, Collidable, Recyclable {
    protected CircleCollider collider;
    protected Sprite sprite;

    protected float lifetime = Metrics.floatValue(R.dimen.missile_splash_lifetime);
    protected int damage = Metrics.intValue(R.dimen.missile_damage);

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
        collider.isVisible = false;
        sprite = new Sprite(x, y, Metrics.size(R.dimen.missile_splash_range), R.mipmap.splash_effect);
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
        sprite.draw(canvas);
    }

    @Override
    public <T extends Collider> T getCollider(Class<T> type) {
        return type.cast(collider);
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
        sprite.setPosition(x, y);
    }
}
