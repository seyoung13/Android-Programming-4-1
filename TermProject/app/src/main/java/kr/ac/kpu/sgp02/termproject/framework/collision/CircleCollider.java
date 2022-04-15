package kr.ac.kpu.sgp02.termproject.framework.collision;

import android.graphics.Point;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.framework.MathHelper;

public class CircleCollider extends Collider {
    public float radius;
    public float radiusSquared;

    CircleCollider(PointF center, float radius) {
        super(center);
        this.radius = radius;
        computeRadiusSquared();
    }

    CircleCollider(float x, float y, float radius) {
        super(x, y);
        this.radius = radius;
        computeRadiusSquared();
    }

    @Override
    public boolean contains(Point point) {
        return MathHelper.getDistanceSquared(point, center) <= radiusSquared;
    }

    @Override
    public boolean contains(PointF point) {
        return MathHelper.getDistanceSquared(point, center) <= radiusSquared;
    }

    @Override
    public boolean intersects(BoxCollider box) {
        return box.intersects(this);
    }

    @Override
    public boolean intersects(CircleCollider circle) {
        return MathHelper.getDistanceSquared(center, circle.center) <= (radiusSquared + circle.radiusSquared);
    }

    private void computeRadiusSquared() {
        radiusSquared = radius * radius;
    }
}
