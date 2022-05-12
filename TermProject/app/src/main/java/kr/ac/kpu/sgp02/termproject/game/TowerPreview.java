package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.framework.collision.CircleCollider;
import kr.ac.kpu.sgp02.termproject.game.tower.Tower;

public class TowerPreview implements GameObject {
    protected Sprite sprite;
    protected RectF location;
    protected CircleCollider range;
    protected float size = Metrics.size(R.dimen.cell_size);
    protected static Paint paint;

    public TowerPreview(float x, float y, int bitmapResId, float radius) {
        sprite = new Sprite(x, y, size-100, bitmapResId);
        location = new RectF(x - size/2, y - size/2, x + size/2, y + size/2);
        range = new CircleCollider(x, y, radius);

        setPosition(x, y);

        setPaint();
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
        location.set(x - size / 2, y - size / 2, x + size / 2, y + size / 2);
        range.set(x, y);
    }

    private void setPaint() {
        if(paint != null)
            return;

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        // 0 ~ 255
        paint.setAlpha(150);
    }

    public void setLocationColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(location, paint);
        range.draw(canvas);
        sprite.draw(canvas);
    }
}
