package kr.ac.kpu.sgp02.termproject.defense;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.BitmapPool;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;

public class Tower implements GameObject {

    protected float range = 200;
    protected float maxDelay = Metrics.floatValue(R.dimen.fire_delay);
    protected float currDelay = 0.0f;
    private Bitmap bitmap;
    protected Monster target;
    private RectF dstRect = new RectF();

    //타일맵 배열내 인덱스
    private int x, y;

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;

        bitmap = BitmapPool.getBitmap(R.mipmap.tower_sample);
    }

    protected void fire(Monster target) {
        //Projectile p = new Projectile();
        //p.fire(target);
    }

    protected void setTarget(Monster target) {
        this.target = target;
    }

    @Override
    public void update(float deltaSecond) {

        float cellSize = Metrics.size(R.dimen.cell_size);

        currDelay -= deltaSecond;
        if(currDelay <= 0) {
            Projectile p = new Projectile(x*cellSize, y*cellSize);
            GameView.view.add(p);
            currDelay += maxDelay;
        }

        dstRect.set(x*cellSize -100, y*cellSize-100, x*cellSize+100, y*cellSize+100);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

}
