package kr.ac.kpu.sgp02.termproject.game.tower;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.Iterator;
import java.util.LinkedHashSet;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.MathHelper;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Recyclable;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Collidable;

public abstract class Tower implements GameObject, Collidable, Recyclable {
    protected float currDelay = 0.0f;
    protected Monster target;

    protected Sprite towerBase;
    protected Sprite towerHead;
    protected float maxDelay = Metrics.floatValue(R.dimen.cannon_delay);
    protected CircleCollider range;

    protected LinkedHashSet<Monster> targetList = new LinkedHashSet<>();

    protected Point tileIndex = new Point();
    protected PointF position = new PointF();
    protected float degree = 0;

    protected Tower(int tileX, int tileY) {
        setTileIndex(tileX, tileY);
        setPositionByTileIndex();

        setSpecification();
    }

    private void setTileIndex(int x, int y) {
        tileIndex.x = x;
        tileIndex.y = y;
    }

    protected abstract void setSpecification();

    protected void setPositionByTileIndex() {
        position = Metrics.tileIndexToPosition(tileIndex.x, tileIndex.y);
        towerBase = new Sprite(position.x, position.y, Metrics.size(R.dimen.tower_base_size), R.mipmap.tower_base);
    }

    protected abstract void fire();

    @Override
    public void update(float deltaSecond) {
        exceptDeadTarget();

        if(targetList.iterator().hasNext()) {
            degree = (float) MathHelper.getDegree(targetList.iterator().next().getPosition(), position);
        }

        currDelay -= deltaSecond;
        if(currDelay <= 0) {
            if(targetList.iterator().hasNext()) {
                fire();
                currDelay += maxDelay;
            } else {
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
        towerBase.draw(canvas);

        canvas.save();
        canvas.rotate(degree, position.x, position.y);
        towerHead.draw(canvas);
        canvas.restore();
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
        setTileIndex((int)x, (int)y);

        setPositionByTileIndex();

        targetList.clear();
    }
}
