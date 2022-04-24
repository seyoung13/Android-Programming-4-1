package kr.ac.kpu.sgp02.termproject.defense;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.defense.tower.Projectile;
import kr.ac.kpu.sgp02.termproject.framework.BitmapPool;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.collision.BoxCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;

public class Monster implements GameObject, Collidable {
    protected int hp;
    protected float speed;
    private Bitmap bitmap;
    private RectF dstRect = new RectF();
    public BoxCollider collider;
    protected PointF position;
    public boolean isDead;

    public Monster(float x, float y) {
        Resources res = GameView.view.getResources();
        dstRect.set(x-100, y-100, x+100, y+100);
        bitmap = BitmapPool.getBitmap(R.mipmap.monster_sample);
        collider = new BoxCollider(x, y, 105, 105);
        hp = 20;
        position = new PointF(x, y);
        isDead = false;
        speed = 10;
    }

    @Override
    public void update(float deltaSecond) {
        if(isDead)
            return;

        position.x += speed;
        dstRect.offset(speed, 0);
        collider.offset(speed, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        if(isDead)
            return;

        canvas.drawBitmap(bitmap, null, dstRect, null);
        collider.draw(canvas);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Projectile) {
            beDamaged(10);
        }
    }

    @Override
    public void onStayOverlap(GameObject object) {

    }

    @Override
    public void onEndOverlap(GameObject object) {

    }

    private void beDamaged(int damage) {
        hp -= damage;
        if(hp <= 0)
            isDead = true;
    }

    public PointF getPosition() {
        return position;
    }
}
