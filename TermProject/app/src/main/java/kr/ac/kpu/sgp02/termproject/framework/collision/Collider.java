package kr.ac.kpu.sgp02.termproject.framework.collision;

import android.graphics.Point;
import android.graphics.PointF;

public abstract class Collider {
    public PointF center;

    Collider(PointF center) {
        this.center = center;
    }

    Collider(float x, float y) {
        center.x = x;
        center.y = y;
    }

    public abstract boolean contains(Point point);
    public abstract boolean contains(PointF point);
    public abstract boolean intersects(BoxCollider box);
    public abstract boolean intersects(CircleCollider circle);
}


