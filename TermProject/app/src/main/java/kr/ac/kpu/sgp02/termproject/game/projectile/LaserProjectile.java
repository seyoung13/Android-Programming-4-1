package kr.ac.kpu.sgp02.termproject.game.projectile;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.MathHelper;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;

public class LaserProjectile extends Projectile {
    protected float degree;
    protected float lifetime;

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
        sprite = new Sprite(x, y, 1, R.mipmap.laser_beam);
        damage = Metrics.intValue(R.dimen.laser_damage);
        lifetime = Metrics.floatValue(R.dimen.laser_delay);
    }

    @Override
    public void update(float deltaSecond) {
        lifetime -= deltaSecond;

        if(lifetime < 0) {
            DefenseGame.getInstance().remove(this);
            return;
        }

        if(target == null)
        {
            DefenseGame.getInstance().remove(this);
            return;
        }

        if(target.isDead())
        {
            DefenseGame.getInstance().remove(this);
            return;
        }

        PointF targetPosition = target.getPosition();
        deltaPosition = MathHelper.subtract(targetPosition, position);
        double radian = Math.atan2(deltaPosition.y, deltaPosition.x);
        degree = (float)Math.toDegrees(radian);
        degree = (float)MathHelper.getDegree(targetPosition, position);

        float distance = (float)Math.sqrt(deltaPosition.x * deltaPosition.x +
                deltaPosition.y * deltaPosition.y);

        sprite.setPositionLeftTop(position.x, position.y,
                distance, Metrics.size(R.dimen.laser_beam_width));
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(degree, position.x, position.y);

        sprite.draw(canvas);
        collider.draw(canvas);

        canvas.restore();
    }

    @Override
    public void setTarget(Monster monster) {
        super.setTarget(monster);
        monster.beDamaged(damage);
    }

    @Override
    public void redeploy(float x, float y) {
        super.redeploy(x, y);
        lifetime = Metrics.floatValue(R.dimen.laser_delay);
    }
}
