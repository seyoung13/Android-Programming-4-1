package kr.ac.kpu.sgp02.termproject.game.projectile;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Recyclable;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.MathHelper;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.BoxCollider;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Collidable;

public class Projectile implements GameObject, Collidable, Recyclable {
    private int damage;
    protected Monster target;
    public BoxCollider collider;
    protected Sprite sprite;
    protected PointF position;
    protected PointF deltaPosition = new PointF();
    protected float speed = 3000;

    public static Projectile get(float x, float y) {
        Projectile recyclable = (Projectile) ObjectPool.get(Projectile.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new Projectile(x, y);

        return recyclable;
    }

    private void set(float x, float y) {
        position.set(x, y);
    }


    protected Projectile(float x, float y) {
        position = new PointF(x, y);
        collider = new BoxCollider(x, y, 30, 30);
        sprite = new Sprite(x, y, 30, R.mipmap.tile_grid);
        damage = 30;
    }


    @Override
    public void update(float deltaSecond) {
        if(target == null)
        {
            DefenseGame.getInstance().remove(this);
            return;
        }

        PointF targetPosition = target.getPosition();
        deltaPosition = MathHelper.subtract(targetPosition, position);
        double radian = Math.atan2(deltaPosition.y, deltaPosition.x);

        float dx = (float) Math.cos(radian) * speed * deltaSecond;
        float dy = (float) Math.sin(radian) * speed * deltaSecond;
        if(Math.abs(dx) > Math.abs(deltaPosition.x)) {
            dx = deltaPosition.x;
        }
        if(Math.abs(dy) > Math.abs(deltaPosition.y)) {
            dy = deltaPosition.y;
        }

        sprite.offset(dx, dy);
        position.offset(dx, dy);
        collider.offset(dx, dy);
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
        collider.draw(canvas);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Monster) {
            Monster monster = (Monster) object;
            if(monster == target) {
                monster.beDamaged(damage);
                DefenseGame.getInstance().remove(this);
            }
        }
    }

    @Override
    public void onStayOverlap(GameObject object) {

    }

    @Override
    public void onEndOverlap(GameObject object) {

    }

    public void setTarget(Monster monster) {
        target = monster;
    }

    @Override
    public void redeploy(float x, float y) {
        position.set(x, y);
        collider.set(x, y);
        sprite.setPosition(x, y);
    }
}
