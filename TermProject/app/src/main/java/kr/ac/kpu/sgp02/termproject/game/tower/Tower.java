package kr.ac.kpu.sgp02.termproject.game.tower;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import java.util.Iterator;
import java.util.LinkedHashSet;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.collision.BoxCollider;
import kr.ac.kpu.sgp02.termproject.framework.helper.MathHelper;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Touchable;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Collidable;

public abstract class Tower implements GameObject, Collidable, Recyclable, Touchable {
    protected float currDelay = 0.0f;
    protected Monster target;

    protected Sprite towerBase;
    protected Sprite towerHead;
    protected Sprite upgradeStar;
    protected static int[] levelSymbols;

    protected int level = 1;
    protected int cost = 50;
    protected float maxDelay = Metrics.floatValue(R.dimen.cannon_delay);
    protected CircleCollider range;
    protected BoxCollider touchBox;

    protected LinkedHashSet<Monster> targetList = new LinkedHashSet<>();

    protected Point tileIndex = new Point();
    protected PointF position = new PointF();
    protected float degree = 0;

    static {
        levelSymbols = new int[3];
        levelSymbols[0] = R.mipmap.level_symbol_1;
        levelSymbols[1] = R.mipmap.level_symbol_2;
        levelSymbols[2] = R.mipmap.level_symbol_3;
    }

    protected Tower(int tileX, int tileY) {
        setTileIndex(tileX, tileY);
        setPositionByTileIndex();

        setSpecification();
    }

    public boolean isMaxLevel() {
        return level >= 3;
    }

    public int getUpgradeCost() {
        return (int)((0.35 + 0.4 * level) * cost);
    }

    public int getSellCost() {
        return (int)((0.35 + 0.4 * level) * cost);
    }

    public void showRange(boolean isVisible) {
        range.isVisible = isVisible;
    }

    public void upgrade() {
        level = Math.min(++level, 3);

        upgradeStar.setBitmap(levelSymbols[level-1]);

        range.radius += range.radius * (0.1 * (level-1));
        maxDelay -= maxDelay * (0.1 * (level-1));
    }

    public void uninstall() {
        DefenseGame.getInstance().getTileAt(tileIndex.x, tileIndex.y).onTowerUninstalled();
        DefenseGame.getInstance().remove(this);
    }


    private void setTileIndex(int x, int y) {
        tileIndex.x = x;
        tileIndex.y = y;
    }

    protected abstract void setSpecification();

    protected void setPositionByTileIndex() {
        position = Metrics.tileIndexToPosition(tileIndex.x, tileIndex.y);

        float baseSize = Metrics.size(R.dimen.tower_base_size);

        towerBase = new Sprite(position.x, position.y, baseSize, R.mipmap.tower_base);
        touchBox = new BoxCollider(position.x, position.y, baseSize/2, baseSize/2);

        float starSize = Metrics.size(R.dimen.upgrade_star_size);
        upgradeStar = new Sprite(position.x, position.y - baseSize/2 + starSize, starSize, R.mipmap.level_symbol_1);
    }

    protected abstract void fire();

    private void exceptDeadTarget() {
        Iterator<Monster> iterator = targetList.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().isDead())
                iterator.remove();
        }
    }

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

    @Override
    public void draw(Canvas canvas) {
        range.draw(canvas);
        touchBox.draw(canvas);
        towerBase.draw(canvas);

        canvas.save();
        canvas.rotate(degree, position.x, position.y);
        towerHead.draw(canvas);
        canvas.restore();
        upgradeStar.draw(canvas);
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
        level = 1;

        setTileIndex((int)x, (int)y);

        setPositionByTileIndex();

        targetList.clear();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF touchedPosition = new PointF(event.getX(), event.getY());

        if (!touchBox.contains(touchedPosition)){
            return false;
        }

        return true;
    }
}
