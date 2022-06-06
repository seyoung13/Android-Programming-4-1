package kr.ac.kpu.sgp02.termproject.framework.helper;

import android.graphics.Point;
import android.graphics.PointF;

public class MathHelper {
    public static float getDistanceSquared(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        return dx * dx + dy * dy;
    }

    public static float getDistanceSquared(Point p1, Point p2) {
        return getDistanceSquared(p1.x, p1.y, p2.x, p2.y);
    }

    public static float getDistanceSquared(PointF p1, PointF p2) {
        return getDistanceSquared(p1.x, p1.y, p2.x, p2.y);
    }

    public static float getDistanceSquared(Point p1, PointF p2) {
        return getDistanceSquared(p1.x, p1.y, p2.x, p2.y);
    }

    public static float getDistance(Point p, float x, float y) {
        float dist = getDistanceSquared(p.x, p.y, x, y);
        return (float)Math.sqrt(dist);
    }

    public static float getDistance(float x1, float y1, float x2, float y2) {
        float dist = getDistanceSquared(x1, y1, x2, y2);
        return (float)Math.sqrt(dist);
    }

    public static float getDistance(Point p1, Point p2) {
        float dist = getDistanceSquared(p1, p2);
        return (float)Math.sqrt(dist);
    }

    public static float getDistance(PointF p1, PointF p2) {
        float dist = getDistanceSquared(p1, p2);
        return (float)Math.sqrt(dist);
    }

    public static float getDistance(Point p1, PointF p2) {
        float dist = getDistanceSquared(p1, p2);
        return (float)Math.sqrt(dist);
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static float add(float a, float b) {
        return a + b;
    }

    public static Point add(Point p1, Point p2) {
        return new Point(add(p1.x, p2.x), add(p1.y, p2.y));
    }

    public static PointF add(PointF p1, PointF p2) {
        return new PointF(add(p1.x, p2.x), add(p1.y, p2.y));
    }

    public static Point subtract(Point p1, Point p2) {
        return new Point(add(p1.x, -p2.x), add(p1.y, -p2.y));
    }

    public static PointF subtract(PointF p1, PointF p2) {
        PointF p = new PointF(add(p1.x, -p2.x), add(p1.y, -p2.y));
        return p;
    }

    public static double getRadian(PointF target, PointF origin) {
        PointF deltaPosition = subtract(target, origin);
        return Math.atan2(deltaPosition.y, deltaPosition.x);
    }

    public static double getDegree(PointF target, PointF origin){
        PointF deltaPosition = subtract(target, origin);
        double radian = Math.atan2(deltaPosition.y, deltaPosition.x);

        return Math.toDegrees(radian);
    }

}
