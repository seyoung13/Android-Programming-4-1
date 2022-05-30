package kr.ac.kpu.sgp02.termproject.framework.collision;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.framework.helper.MathHelper;

public class CircleCollider extends Collider {
    public float radius;
    public float radiusSquared;

    // --------------- 생성자 ---------------

    public CircleCollider(Point center, float radius) {
        super(center);
        initialize(radius);
    }

    public CircleCollider(PointF center, float radius) {
        super(center);
        initialize(radius);
    }

    public CircleCollider(float x, float y, float radius) {
        super(x, y);
        initialize(radius);
    }

    // --------------- 메소드 ---------------

    protected void initialize(float radius) {
        this.radius = radius;
        radiusSquared = radius * radius;
    }

    @Override
    public void offset(float dx, float dy) {
        super.offset(dx, dy);
    }

    // --------------- 인터페이스 ---------------

    @Override
    public boolean contains(Point point) {
        return MathHelper.getDistanceSquared(point, center) <= radiusSquared;
    }

    @Override
    public boolean contains(PointF point) {
        return MathHelper.getDistanceSquared(point, center) <= radiusSquared;
    }

    @Override
    protected boolean intersects(BoxCollider box) {
        return box.intersects(this);
    }

    @Override
    protected boolean intersects(CircleCollider circle) {
        return MathHelper.getDistanceSquared(center, circle.center) <= (radiusSquared + circle.radiusSquared);
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
            canvas.drawCircle(center.x, center.y, radius, paint);
    }

}
