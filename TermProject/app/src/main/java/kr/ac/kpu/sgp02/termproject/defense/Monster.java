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
import kr.ac.kpu.sgp02.termproject.framework.collision.BoxCollider;

public class Monster implements GameObject {
    protected int hp;
    protected float speed;
    private Bitmap bitmap;
    private RectF dstRect = new RectF();
    public BoxCollider collider;

    public Monster(float x, float y) {
        Resources res = GameView.view.getResources();
        dstRect.set(x-150, y-150, x+150, y+150);
        bitmap = BitmapPool.getBitmap(R.mipmap.monster_sample);
        collider = new BoxCollider(x, y, 160, 160);
    }

    @Override
    public void update(float deltaSecond) {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
        collider.draw(canvas);
    }

}
