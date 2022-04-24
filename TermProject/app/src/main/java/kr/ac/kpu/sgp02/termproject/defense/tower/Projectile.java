package kr.ac.kpu.sgp02.termproject.defense.tower;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.defense.Monster;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.MathHelper;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collider;

public class Projectile implements GameObject, Collidable {
    private float damage;
    private Monster target;
    public CircleCollider collider;
    protected PointF position;
    private Bitmap bitmap;
    private RectF dstRect = new RectF();
    protected PointF deltaPosition = new PointF();
    protected float speed = 3000;

    public Projectile(float x, float y) {
        position = new PointF(x, y);
        collider = new CircleCollider(x, y, 30);
        damage = 5;
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
        double degree = Math.atan2(deltaPosition.y, deltaPosition.x);
        float dx = (float) Math.cos(degree) * speed * deltaSecond;
        float dy = (float) Math.sin(degree) * speed * deltaSecond;

        if(Math.abs(dx) > Math.abs(deltaPosition.x)) {
            dx = targetPosition.x - position.x;
        }
        if(Math.abs(dy) > Math.abs(deltaPosition.y)) {
            dy = targetPosition.y - position.y;
        }

        position.offset(dx, dy);
        collider.offset(dx, dy);
    }

    @Override
    public void draw(Canvas canvas) {
        collider.draw(canvas);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Monster) {
            Monster monster = (Monster) object;
            if(object == target)
                GameView.view.remove(this);
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
}
