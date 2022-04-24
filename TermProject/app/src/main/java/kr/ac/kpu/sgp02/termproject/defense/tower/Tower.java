package kr.ac.kpu.sgp02.termproject.defense.tower;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Iterator;
import java.util.LinkedHashSet;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.defense.Monster;
import kr.ac.kpu.sgp02.termproject.framework.BitmapPool;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;

public abstract class Tower implements GameObject, Collidable {

    protected float maxDelay = Metrics.floatValue(R.dimen.fire_delay);
    protected float currDelay = 0.0f;
    private Bitmap bitmap;
    protected Monster target;
    private RectF dstRect = new RectF();
    public CircleCollider range;
    protected LinkedHashSet<Monster> targetList = new LinkedHashSet<>();

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

    protected void fire() {
         Projectile p = new Projectile(position.x, position.y);
         GameView.view.add(p);

         Monster monster = targetList.iterator().next();
         p.setTarget(monster);
    }

    @Override
    public void update(float deltaSecond) {
        exceptDeadTarget();

        currDelay -= deltaSecond;
        if(currDelay <= 0) {
            if(targetList.iterator().hasNext()) {
                fire();
                currDelay += maxDelay;
            }
            else {
                currDelay = 0;
            }
        }

        dstRect.set(position.x -100, position.y-100, position.x+100, position.y+100);
    }

    private void exceptDeadTarget() {
        Iterator<Monster> iterator = targetList.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().isDead)
                iterator.remove();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
        range.draw(canvas);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
        if(!(object instanceof Monster))
           return;

        targetList.add((Monster) object);
    }

    @Override
    public void onStayOverlap(GameObject object) {

    }

    @Override
    public void onEndOverlap(GameObject object) {
        if(!(object instanceof Monster))
            return;

        targetList.remove((Monster) object);
    }
}
