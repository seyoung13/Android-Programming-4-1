package kr.ac.kpu.sgp02.termproject.defense;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.ObjectPool;

public class Tower implements GameObject {

    protected float range;
    protected float maxDelay = 20.0f;
    protected float currDelay = 0.0f;
    protected Projectile projectile;
    private Bitmap bitmap;
    protected Monster target;
    private static Rect srcRect = new Rect();
    private RectF dstRect = new RectF();

    //타일맵 배열내 인덱스
    private int x, y;

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;

        Resources res = GameView.view.getResources();
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.tower_sample);
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    protected void fire(Monster target) {
        Projectile p = new Projectile();
        p.fire(target);
    }

    protected void setTarget(Monster target) {
        this.target = target;
    }

    @Override
    public void update(float deltaSecond) {


        currDelay -= deltaSecond;
        if(currDelay <= 0) {
            fire(monster);
            currDelay += maxDelay;
        }

        float cellSize = Metrics.size(R.dimen.cell_size);
        dstRect.set(x*cellSize -50, y*cellSize-50, x*cellSize+50, y*cellSize+50);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
