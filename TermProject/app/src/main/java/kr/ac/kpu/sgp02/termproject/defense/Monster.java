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

public class Monster implements GameObject {
    protected int hp;
    protected float speed;
    private Bitmap bitmap;
    private static Rect srcRect = new Rect();
    private RectF dstRect = new RectF();

    Monster() {
        Resources res = GameView.view.getResources();
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.monster_sample);
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
