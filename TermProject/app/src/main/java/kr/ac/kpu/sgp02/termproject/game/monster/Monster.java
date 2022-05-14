package kr.ac.kpu.sgp02.termproject.game.monster;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.ProgressBar;
import kr.ac.kpu.sgp02.termproject.framework.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.BoxCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;

public class Monster implements GameObject, Collidable, Recyclable {
    protected float hp;
    protected float maxHp;
    protected float speed;
    protected float reward;
    protected Sprite sprite;
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
        position = new PointF(x, y);
        setSpec();
        collider = new BoxCollider(x, y,
                Metrics.size(R.dimen.monster_size)/2,
                Metrics.size(R.dimen.monster_size)/2);
        hp = maxHp;
        isDead = false;
        hpBar = new ProgressBar(position.x, position.y + size/2,
                Metrics.size(R.dimen.hp_bar_width), Metrics.size(R.dimen.hp_bar_height), hp);
    }

    protected void setSpec() {
        sprite = new Sprite(position.x, position.y, size, R.mipmap.monster_walker);

        maxHp = 100;
        speed = Metrics.size(R.dimen.walker_speed);
        reward = 20;
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
