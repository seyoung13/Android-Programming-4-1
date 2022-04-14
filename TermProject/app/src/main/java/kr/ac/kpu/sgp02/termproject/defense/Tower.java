package kr.ac.kpu.sgp02.termproject.defense;

import android.graphics.Canvas;

import kr.ac.kpu.sgp02.termproject.framework.GameObject;

public class Tower implements GameObject {

    private float range;
    private float maxDelay = 20.0f;
    private float currDelay = 0.0f;
    private Projectile projectile;

    //private Monster target;

    //타일맵 배열내 인덱스
    private int x, y;

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void fire(Monster target) {

    }

    @Override
    public void update(float deltaSecond) {
        currDelay -= deltaSecond;
        if(currDelay <= 0) {
            fire(monster);
            currDelay += maxDelay;
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
