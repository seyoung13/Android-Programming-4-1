package kr.ac.kpu.sgp02.termproject.game.monster;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collider;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.objects.ProgressBar;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.BoxCollider;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Collidable;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;

public class Monster implements GameObject, Collidable, Recyclable {
    protected float hp;
    protected float maxHp;
    protected float speed;
    protected int reward;

    protected Sprite sprite;
    protected BoxCollider collider;
    protected PointF position;
    protected float size;

    private PathMeasure pathMeasure;
    protected float distance;
    private float[] measuredPosition = new float[2];
    private float[] measuredTangent = new float[2];

    public boolean isDead;
    public ProgressBar hpBar;

    public static Monster get(int tileX, int tileY, Path path) {
        Monster recyclable = (Monster) ObjectPool.get(Monster.class);

        if(recyclable != null)
            recyclable.redeploy(tileX, tileY);
        else
            recyclable = new Monster(tileX, tileY);

        recyclable.setPath(path);

        return recyclable;
    }

    protected Monster(int tileX, int tileY) {
        position = Metrics.tileIndexToPosition(tileX, tileY);
        size = Metrics.size(R.dimen.monster_size);
        setSpec();
        collider = new BoxCollider(position.x, position.y, size/2, size/2);
        hp = maxHp;
        isDead = false;
        hpBar = new ProgressBar(position.x, position.y + size/2,
                Metrics.size(R.dimen.hp_bar_width), Metrics.size(R.dimen.hp_bar_height), hp);
        distance = 0;
    }

    protected void setSpec() {
        sprite = new Sprite(position.x, position.y, size, R.mipmap.monster_walker);

        maxHp = 100;
        speed = Metrics.size(R.dimen.walker_speed);
        reward = 20;
    }


    @Override
    public void update(float deltaSecond) {
        distance += speed * deltaSecond;

        if(isDead || distance > pathMeasure.getLength()) {
            DefenseGame.getInstance().remove(this);
            return;
        }

        pathMeasure.getPosTan(distance, measuredPosition, measuredTangent);

        position.set(measuredPosition[0], measuredPosition[1]);
        sprite.setPosition(measuredPosition[0], measuredPosition[1]);
        collider.set(measuredPosition[0], measuredPosition[1]);
        hpBar.setPosition(measuredPosition[0], measuredPosition[1] + size/2);
    }

    @Override
    public void draw(Canvas canvas) {
        if(isDead)
            return;

        sprite.draw(canvas);
        collider.draw(canvas);
        hpBar.draw(canvas);
    }

    @Override
    public <T extends Collider> T getCollider(Class<T> type) {
        return type.cast(collider);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
    }

    @Override
    public void onStayOverlap(GameObject object) {

    }

    @Override
    public void onEndOverlap(GameObject object) {

    }

    public void beDamaged(int damage) {
        hp -= damage;
        if(hp <= 0)
            onDead();

        hpBar.setProgress(hp);
    }

    public PointF getPosition() {
        return position;
    }

    public void onDead() {
        isDead = true;
        DefenseGame.getInstance().storeMineral(reward);
    }

    @Override
    public void redeploy(float x, float y) {
        position = Metrics.tileIndexToPosition((int)x, (int)y);
        sprite.setPosition(position.x, position.y);
        collider.set(position.x, position.y);
        hp = maxHp;
        isDead = false;
        hpBar.redeploy(position.x, position.y + size/2);
        distance = 0;
    }

    public void setPath(Path path) {
        pathMeasure = new PathMeasure(path, false);
    }
}
