package kr.ac.kpu.sgp02.termproject.game.projectile;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.MathHelper;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;

public class LaserProjectile extends Projectile {
    protected float degree;

    public LaserProjectile(float x, float y) {
        super(x, y);
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
        degree = (float)Math.toDegrees(radian);

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

}
