package kr.ac.kpu.sgp02.termproject.game.projectile;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.MathHelper;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;

public class LaserProjectile extends Projectile {
    protected float degree;
    protected float currDelay = 0.0f;
    protected float maxDelay = 0.5f;
    private int damage = 5;

    public static LaserProjectile get(float x, float y) {
        LaserProjectile recyclable = (LaserProjectile) ObjectPool.get(LaserProjectile.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new LaserProjectile(x, y);

        return recyclable;
    }

    protected LaserProjectile(float x, float y) {
        super(x, y);
    }

    @Override
    public void update(float deltaSecond) {
        currDelay -= deltaSecond;
        if(currDelay < 0) {
            target.beDamaged(damage);
            currDelay += maxDelay;
        }

        if(target == null)
            return;

        if(target.isDead)
        {
            DefenseGame.getInstance().remove(this);
            return;
        }

        PointF targetPosition = target.getPosition();
        deltaPosition = MathHelper.subtract(targetPosition, position);
        double radian = Math.atan2(deltaPosition.y, deltaPosition.x);
        degree = (float)Math.toDegrees(radian);
        degree = (float)MathHelper.getDegreeBetween(position, targetPosition);

        float distance = (float)Math.sqrt(deltaPosition.x * deltaPosition.x +
                deltaPosition.y * deltaPosition.y);

        sprite.setPositionLeftTop(position.x, position.y,
                distance, 10);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(degree, position.x, position.y);

        sprite.draw(canvas);
        collider.draw(canvas);

        canvas.restore();
    }

    @Override
    public void onBeginOverlap(GameObject object) {
    }


    @Override
    public void redeploy(float x, float y) {
        super.redeploy(x, y);
    }
}
