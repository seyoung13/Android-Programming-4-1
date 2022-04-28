package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Canvas;


import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.Monster;

public class MonsterGenerator implements GameObject {
    private float delay = 0.0f;
    private float maxDelay = 1.0f;

    @Override
    public void update(float deltaSecond) {
        delay -= deltaSecond;

        if(delay <= 0) {
            DefenseGame.getInstance().add(new Monster(100, 750));
            delay += maxDelay;
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
