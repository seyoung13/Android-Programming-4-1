package kr.ac.kpu.sgp02.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.framework.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.game.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.BoxCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;

public class Monster implements GameObject, Collidable {
    protected int hp;
    protected float speed;
    private Sprite sprite;
    public BoxCollider collider;
    protected PointF position;
    public boolean isDead;

    public Monster(float x, float y) {
        Resources res = GameView.view.getResources();
        sprite = new Sprite(x, y, Metrics.size(R.dimen.cell_size) - 10, R.mipmap.monster_sample);
        collider = new BoxCollider(x, y,
                Metrics.size(R.dimen.cell_size)/2,
                Metrics.size(R.dimen.cell_size)/2);
        hp = 5;
        position = new PointF(x, y);
        isDead = false;
        speed = 8;
    }

    @Override
    public void update(float deltaSecond) {
        if(isDead)
            return;

        position.offset(speed, 0);
        sprite.offset(speed, 0);
        collider.offset(speed, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        if(isDead)
            return;

        sprite.draw(canvas);
        collider.draw(canvas);
    }

    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Projectile) {
            //beDamaged(5);
        }
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
    }

    public PointF getPosition() {
        return position;
    }
}
