package kr.ac.kpu.sgp02.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.framework.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.ProgressBar;
import kr.ac.kpu.sgp02.termproject.framework.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.BoxCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;

public class Monster implements GameObject, Collidable, Recyclable {
    protected int hp;
    protected int maxHp = 120;
    protected float speed;
    private Sprite sprite;
    public BoxCollider collider;
    protected PointF position;
    public boolean isDead;
    public ProgressBar hpBar;
    float size = Metrics.size(R.dimen.cell_size) - 10;

    public static Monster get(float x, float y) {
        Monster recyclable = (Monster) ObjectPool.get(Monster.class);

        if(recyclable != null)
            recyclable.redeploy(x, y);
        else
            recyclable = new Monster(x, y);

        return recyclable;
    }

    protected Monster(float x, float y) {
        sprite = new Sprite(x, y, size, R.mipmap.monster_sample);
        collider = new BoxCollider(x, y,
                Metrics.size(R.dimen.monster_size)/2,
                Metrics.size(R.dimen.monster_size)/2);
        hp = maxHp;
        position = new PointF(x, y);
        isDead = false;
        speed = Metrics.size(R.dimen.monster_speed);
        hpBar = new ProgressBar(position.x, position.y + size/2,
                Metrics.size(R.dimen.hp_bar_width), Metrics.size(R.dimen.hp_bar_height), hp);
    }

    @Override
    public void update(float deltaSecond) {
        if(isDead || position.x > Metrics.width + size) {
            DefenseGame.getInstance().remove(this);
            return;
        }

        float dist = speed * deltaSecond;

        position.offset(dist, 0);
        sprite.offset(dist, 0);
        collider.offset(dist, 0);
        hpBar.offset(dist, 0);
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
            isDead = true;

        hpBar.setProgress(hp);
    }

    public PointF getPosition() {
        return position;
    }

    @Override
    public void redeploy(float x, float y) {
        position.set(x, y);
        sprite.setPosition(x, y);
        collider.set(x, y);
        hp = maxHp;
        isDead = false;
        hpBar.redeploy(x, y + size/2);
    }
}
