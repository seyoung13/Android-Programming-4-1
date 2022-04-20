package kr.ac.kpu.sgp02.termproject.defense;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.LinkedList;
import java.util.Queue;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.BitmapPool;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collider;

public class Tower implements GameObject, Collidable {

    protected float maxDelay = Metrics.floatValue(R.dimen.fire_delay);
    protected float currDelay = 0.0f;
    private Bitmap bitmap;
    protected Monster target;
    private RectF dstRect = new RectF();
    protected CircleCollider range;
    protected Queue<Monster> targetList = new LinkedList<>();

    //타일맵 배열내 인덱스
    private int x, y;
    private PointF position = new PointF();

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;

        position.x = x * Metrics.size(R.dimen.cell_size);
        position.y = y * Metrics.size(R.dimen.cell_size);

        range = new CircleCollider(position.x, position.y, Metrics.size(R.dimen.range));
        bitmap = BitmapPool.getBitmap(R.mipmap.tower_sample);
    }

    protected void fire(Monster target) {
        //Projectile p = new Projectile();
        //p.fire(target);
    }

    protected void setTarget(Monster target) {
        this.target = target;
    }

    protected void addTarget(Monster target){
        targetList.offer(target);
    }


    @Override
    public void update(float deltaSecond) {
        currDelay -= deltaSecond;
        if(currDelay <= 0) {
            Projectile p = new Projectile(position.x, position.y);
            GameView.view.add(p);
            currDelay += maxDelay;
        }

        dstRect.set(position.x -100, position.y-100, position.x+100, position.y+100);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
        range.draw(canvas);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Monster)
            addTarget((Monster) object);
    }

    @Override
    public void onStayOverlap(GameObject object) {

    }

    @Override
    public void onEndOverlap(GameObject object) {

    }
}
