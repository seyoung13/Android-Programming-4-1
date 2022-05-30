package kr.ac.kpu.sgp02.termproject.framework.collision;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.HashSet;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;

public abstract class Collider implements GameObject {
    public PointF center;
    public boolean isVisible = true;

    protected static Paint paint;
    public HashSet<Collider> overlappedColliders;

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
        center.offset(dx, dy);
    }

    public void set(float x, float y) {
        center.x = x;
        center.y = y;
    }

    private void initialize(float x, float y) {
        center = new PointF(x, y);
        overlappedColliders = new HashSet<>(32);
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


