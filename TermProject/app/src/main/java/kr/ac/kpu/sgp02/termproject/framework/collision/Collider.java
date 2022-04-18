package kr.ac.kpu.sgp02.termproject.framework.collision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;

public abstract class Collider implements GameObject {
    public PointF center;
    public boolean isVisible = true;

    protected static Paint paint;

    // --------------- 생성자 ---------------

    Collider(Point center){
        initialize(center.x, center.y);
    }

    Collider(PointF center) {
        initialize(center.x, center.y);
    }

    Collider(float x, float y) {
        initialize(x, y);
    }

    // --------------- 메소드 ---------------

    public void offset(float dx, float dy) {
        center.x += dx;
        center.y += dy;
    }

    private void initialize(float x, float y) {
        center = new PointF(x, y);

        setPaint();
    }

    private void setPaint() {
        if(paint != null)
            return;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Metrics.size(R.dimen.collider_line_width));
    }

    // --------------- 인터페이스 ---------------

    public abstract boolean contains(Point point);
    public abstract boolean contains(PointF point);
    public abstract boolean intersects(BoxCollider box);
    public abstract boolean intersects(CircleCollider circle);

}


