package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Canvas;


import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.game.monster.Armor;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;

public class MonsterGenerator implements GameObject {
    private float delay = 1.0f;
    private float maxDelay = 1.0f;

    @Override
    public void update(float deltaSecond) {
        delay -= deltaSecond;

        if(delay <= 0) {
            DefenseGame.getInstance().add(Armor.get(100, 750),
                    DefenseGame.Layer.monster);
            delay += maxDelay;
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
