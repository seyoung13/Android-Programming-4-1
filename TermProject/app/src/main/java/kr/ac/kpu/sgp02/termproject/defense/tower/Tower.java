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
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;

public abstract class Tower implements GameObject, Collidable {
    protected float currDelay = 0.0f;
    protected Monster target;

    //생성 후 바뀌지 않는 멤버.
    protected Bitmap bitmap;
    protected float maxDelay = Metrics.floatValue(R.dimen.cannon_delay);
    public CircleCollider range;

    protected LinkedHashSet<Monster> targetList = new LinkedHashSet<>();

    //타일맵 배열내 인덱스
    protected int x, y;
    protected PointF position = new PointF();
    protected RectF dstRect = new RectF();

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;

        setPosition(x, y);

        setSpecification();
    }

    protected abstract void setSpecification();

    private void setPosition(int x, int y) {
        float cellSize = Metrics.size(R.dimen.cell_size);

        position.x = x * cellSize + cellSize / 2;
        position.y = y * cellSize + cellSize / 2;

        dstRect.set(position.x - cellSize/2, position.y- cellSize/2,
                position.x + cellSize/2, position.y + cellSize/2);
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
