package kr.ac.kpu.sgp02.termproject.defense.projectile;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.MathHelper;

public class LaserProjectile extends Projectile {
    protected double radian;

    public LaserProjectile(float x, float y) {
        super(x, y);
    }

    @Override
    public void update(float deltaSecond) {
        if(target == null)
        {
            GameView.view.remove(this);
            return;
        }

        PointF targetPosition = target.getPosition();
        deltaPosition = MathHelper.subtract(targetPosition, position);
        radian = Math.atan2(deltaPosition.y, deltaPosition.x);

        float distance = (float)Math.sqrt(deltaPosition.x * deltaPosition.x +
                deltaPosition.y * deltaPosition.y);

        sprite.setPositionLeftTop(position.x, position.y,
                distance, 10);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float)Math.toDegrees(radian), position.x, position.y);

        //Log.d("Degree", "Degree : " + (float)degree);
        sprite.draw(canvas);
        collider.draw(canvas);

        canvas.restore();
    }

    @Override
    public void onBeginOverlap(GameObject object) {
    }

}
