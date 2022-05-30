package kr.ac.kpu.sgp02.termproject.framework.collision;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.framework.helper.MathHelper;

public class BoxCollider extends Collider {
    public PointF extents;
    public float left, top, right, bottom;

    // --------------- 생성자 ---------------

    public BoxCollider(Point center, PointF extents) {
        super(center);
        initialize(extents.x, extents.y);
    }

    public BoxCollider(Point center, float extentsX, float extentsY) {
        super(center);
        initialize(extentsX, extentsY);
    }

    public BoxCollider(PointF center, PointF extents) {
        super(center);
        initialize(extents.x, extents.y);
    }

    public BoxCollider(PointF center, float extentsX, float extentsY) {
        super(center);
       initialize(extentsX, extentsY);
    }

    public BoxCollider(float x, float y, PointF extents) {
        super(x, y);
        initialize(extents.x, extents.y);
    }

    public BoxCollider(float x, float y, float extentsX, float extentsY) {
        super(x, y);
        initialize(extentsX, extentsY);
    }

    // --------------- 메소드 ---------------

    protected void initialize(float extentsX, float extentsY) {
        extents = new PointF(extentsX, extentsY);

        setVertices();
    }

    protected void setVertices() {
        left = center.x - extents.x;
        top = center.y - extents.y;
        right = center.x + extents.x;
        bottom = center.y + extents.y;
    }

    @Override
    public void offset(float dx, float dy) {
        super.offset(dx, dy);
        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }

    @Override
    public void set(float x, float y) {
        super.set(x, y);
        setVertices();
    }

    // --------------- 인터페이스 ---------------

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
    protected boolean intersects(BoxCollider box) {
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
    protected boolean intersects(CircleCollider circle) {
        float x = Math.max(left, Math.min(circle.center.x, right));
        float y = Math.max(top, Math.min(circle.center.y, bottom));

        float distance = MathHelper.getDistanceSquared(x, y, circle.center.x, circle.center.y);

        return distance <= circle.radiusSquared;
    }

    @Override
    public boolean intersects(Collider collider) {
        return collider.intersects(this);
    }

    @Override
    public void update(float deltaSecond) {
    }

    @Override
    public void draw(Canvas canvas) {
        if(isVisible)
            canvas.drawRect(left, top, right, bottom, paint);
    }

}
