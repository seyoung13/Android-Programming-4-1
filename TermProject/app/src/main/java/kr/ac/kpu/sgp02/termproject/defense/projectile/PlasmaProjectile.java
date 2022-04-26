package kr.ac.kpu.sgp02.termproject.defense.projectile;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.MathHelper;

public class PlasmaProjectile extends Projectile{
    protected float degree;
    protected float lifetime = 1.0f;

    public PlasmaProjectile(float x, float y) {
        super(x, y);
    }

    @Override
    public void update(float deltaSecond) {
        lifetime -= deltaSecond;
        if(lifetime < 0)
            GameView.view.remove(this);

        if(target == null)
        {
            GameView.view.remove(this);
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
