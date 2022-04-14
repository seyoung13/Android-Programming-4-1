package kr.ac.kpu.sgp02.termproject.framework;

import android.graphics.Canvas;

public interface GameObject {
    public void update(float deltaSecond);
    public void draw(Canvas canvas);
}
