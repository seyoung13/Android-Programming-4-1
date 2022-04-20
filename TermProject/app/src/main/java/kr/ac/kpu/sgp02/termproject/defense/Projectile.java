package kr.ac.kpu.sgp02.termproject.defense;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collider;

public class Projectile implements GameObject, Collidable {
    private float damage;
    private Monster target;
    public CircleCollider collider;
    private Bitmap bitmap;
    private RectF dstRect = new RectF();

    public Projectile(float x, float y) {
        collider = new CircleCollider(x, y, 30);
        damage = 5;
    }


    @Override
    public void update(float deltaSecond) {
        collider.offset(20, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        collider.draw(canvas);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Monster) {
            GameView.view.remove(this);
        }
    }

    @Override
    public void onStayOverlap(GameObject object) {

    }

    @Override
    public void onEndOverlap(GameObject object) {

    }
}
