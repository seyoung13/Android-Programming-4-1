package kr.ac.kpu.sgp02.termproject.framework.collision;

import android.graphics.Point;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.framework.MathHelper;

public class BoxCollider extends Collider {
    public PointF extents;
    public float left, top, right, bottom;

    BoxCollider(PointF center, PointF extents) {
        super(center);
        this.extents = extents;
        computeVertices();
    }

    BoxCollider(PointF center, float ex, float ey) {
        super(center);
        extents.x = ex;
        extents.y = ey;
        computeVertices();
    }

    BoxCollider(float x, float y, PointF extents) {
        super(x, y);
        this.extents = extents;
        computeVertices();
    }

    BoxCollider(float x, float y, float ex, float ey) {
        super(x, y);
        extents.x = ex;
        extents.y = ey;
        computeVertices();
    }

    @Override
    public boolean contains(Point point) {
        if (point.x < left)
            return false;
        if (right < point.x)
            return false;
        if (point.y < top)
            return false;
        if (bottom < point.y)
            return false;

        return true;
    }

    @Override
    public boolean contains(PointF point) {
        if (point.x < left)
            return false;
        if (right < point.x)
            return false;
        if (point.y < top)
            return false;
        if (bottom < point.y)
            return false;

        return true;
    }

    @Override
    public boolean intersects(BoxCollider box) {
        if (box.right < left)
            return false;
        if (right < box.left)
            return false;
        if (box.bottom < top)
            return false;
        if (bottom < box.top)
            return false;

        return true;
    }

    @Override
    public boolean intersects(CircleCollider circle) {
        float x = Math.max(left, Math.min(circle.center.x, right));
        float y = Math.max(top, Math.min(circle.center.y, bottom));

        float distance = MathHelper.getDistanceSquared(x, y, circle.center.x, circle.center.y);

        return distance <= circle.radiusSquared;
    }

    private void computeVertices() {
        left = center.x - extents.x;
        top = center.y - extents.y;
        right = center.x + extents.x;
        bottom = center.y + extents.y;
    }
}
