package kr.ac.kpu.sgp02.termproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View implements Choreographer.FrameCallback {
    public static GameView view;
    private static final String DEBUG_TAG = GameView.class.getSimpleName();

    ArrayList<GameObject> objects = new ArrayList<GameObject>();

    private long prevTimeNanoSecond;
    private long framePerSecond;

    private final int[][] tileBlueprint =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 1, 1, 1, 3},
                    {0, 1, 0, 0, 0, 1, 0, 0},
                    {0, 1, 1, 1, 1, 1, 0, 0},
                    {2, 1, 0, 0, 0, 0, 0, 0},
            };

    TileMap tileMap;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    private void initView() {
        view = this;

        tileMap = new TileMap(tileBlueprint);

        objects.add(tileMap);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currTimeNanoSecond) {
        long elapsedTimeNanoSecond = currTimeNanoSecond - prevTimeNanoSecond;

        if(elapsedTimeNanoSecond != 0)
            framePerSecond = 1_000_000_000 / elapsedTimeNanoSecond;
        prevTimeNanoSecond = currTimeNanoSecond;

        update();
        invalidate();
        Choreographer.getInstance().postFrameCallback(this);
    }

    public void update() {
        for(GameObject object : objects) {
            object.update();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(GameObject object : objects) {
            object.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        return false;
    }
}
