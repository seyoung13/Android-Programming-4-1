package kr.ac.kpu.sgp02.termproject.framework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class ProgressBar implements GameObject, Recyclable{
    private static Paint currPaint;
    private static Paint fullPaint;

    private float currProgress;
    private float fullProgress;
    private float progressPercent;

    private float width;
    private float height;

    private PointF position = new PointF();

    private RectF currRect = new RectF();
    private RectF fullRect = new RectF();

    public ProgressBar(float x, float y, float width, float height, float fullProgress) {
        this.width = width;
        this.height = height;

        position.set(x, y);
        this.fullProgress = fullProgress;
        currProgress = fullProgress;

        this.width = width;

        updateProgressPercent();

        if(currPaint == null)
            setPaints();
        setRects();
    }

    private void updateProgressPercent() {
        progressPercent = currProgress / fullProgress;
    }

    private void setPaints() {
        currPaint = new Paint();
        fullPaint = new Paint();

        currPaint.setColor(Color.GREEN);
        fullPaint.setColor(Color.RED);
    }

    private void setRects() {
        float left = position.x - width/2;
        float top = position.y - height/2;

        currRect.set(left, top,
                left + progressPercent * width, top + height);

        fullRect.set(left, top,
                left + width, top + height);
    }

    public void increaseProgress(float increment) {
        currProgress += Math.min(increment + currProgress, fullProgress);
        setRects();
    }

    public void setProgress(float currProgress) {
        this.currProgress = currProgress;
        updateProgressPercent();
    }

    public void offset(float dx, float dy) {
        position.offset(dx, dy);
        setRects();
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(fullRect, fullPaint);
        canvas.drawRect(currRect, currPaint);
    }

    @Override
    public void redeploy(float x, float y) {
        position.set(x, y);
        setProgress(fullProgress);
        setRects();
    }
}
