package kr.ac.kpu.sgp02.termproject.framework.interfaces;

import android.graphics.Canvas;

public interface GameObject {
    void update(float deltaSecond);
    void draw(Canvas canvas);
}
