package kr.ac.kpu.sgp02.termproject.game.projectile;

import android.graphics.Canvas;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.framework.collision.Collidable;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.Monster;

public class SiegeSplash implements GameObject, Collidable {
    public CircleCollider splash;
    public float lifetime = 0.5f;

    SiegeSplash(float x, float y) {
        splash = new CircleCollider(x, y, Metrics.size(R.dimen.siege_splash));
    }


    @Override
    public void update(float deltaSecond) {
        lifetime -= deltaSecond;

        if(lifetime <= 0) {
            DefenseGame.getInstance().remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        splash.draw(canvas);
    }


    @Override
    public void onBeginOverlap(GameObject object) {
        if(object instanceof Monster) {
            Monster monster = (Monster) object;
            monster.beDamaged(10);
        }
    }

    @Override
    public void onStayOverlap(GameObject object) {

    }

    @Override
    public void onEndOverlap(GameObject object) {

    }
}
