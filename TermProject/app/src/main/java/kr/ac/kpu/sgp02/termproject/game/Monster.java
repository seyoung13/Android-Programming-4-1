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
        Resources res = GameView.view.getResources();
        sprite = new Sprite(x, y, size, R.mipmap.monster_sample);
        collider = new BoxCollider(x, y,
                Metrics.size(R.dimen.cell_size)/2,
                Metrics.size(R.dimen.cell_size)/2);
        hp = 50;
        position = new PointF(x, y);
        isDead = false;
        speed = 8;

        hpBar = new ProgressBar(position.x, position.y + size/2,
                Metrics.size(R.dimen.hp_bar_width), Metrics.size(R.dimen.hp_bar_height), hp);
    }

    @Override
    public void update(float deltaSecond) {
        if(isDead)
            return;

        position.offset(speed, 0);
        sprite.offset(speed, 0);
        collider.offset(speed, 0);
        hpBar.offset(speed, 0);
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
        collider.set(x, y);
        hp = 50;
        isDead = false;
        hpBar.redeploy(x, y);
    }
}
