package kr.ac.kpu.sgp02.termproject.game.tower;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.Iterator;
import java.util.LinkedHashSet;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.Recyclable;
import kr.ac.kpu.sgp02.termproject.game.Monster;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;

public abstract class Tower implements GameObject, Collidable, Recyclable {
    protected float currDelay = 0.0f;
    protected Monster target;

    //생성 후 바뀌지 않는 멤버.
    protected Sprite sprite;
    protected float maxDelay = Metrics.floatValue(R.dimen.cannon_delay);
    public CircleCollider range;

    protected LinkedHashSet<Monster> targetList = new LinkedHashSet<>();

    //타일맵 배열내 인덱스
    protected Point tileIndex = new Point();
    protected PointF position = new PointF();

    protected Tower(int x, int y) {
        tileIndex.x = x;
        tileIndex.y = y;
        setPositionByTileIndex();

        setSpecification();
    }

    protected abstract void setSpecification();

    protected void setPositionByTileIndex() {
        position = Metrics.tileIndexToPosition(tileIndex.x, tileIndex.y);
    }

    protected abstract void fire();

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
        range.draw(canvas);
        sprite.draw(canvas);
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

    @Override
    public void redeploy(float x, float y){
        tileIndex.x = (int)x;
        tileIndex.y = (int)y;

        setPositionByTileIndex();

        targetList.clear();
    }
}
