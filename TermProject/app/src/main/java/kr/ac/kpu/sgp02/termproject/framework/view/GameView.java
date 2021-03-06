package kr.ac.kpu.sgp02.termproject.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.kpu.sgp02.termproject.app.DefenseActivity;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;

public class GameView extends View implements Choreographer.FrameCallback {
    public static GameView view;

    private long prevTimeNanos;
    private long framePerSecond;

    private boolean isInitialized = false;
    private boolean isRunning = false;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.width = w;
        Metrics.height = h;

        if(!isInitialized) {
            initialize();

            isInitialized = true;
            isRunning = true;
        }
    }

    private void initialize() {
        view = this;

        DefenseGame.getInstance().initialize();

        Choreographer.getInstance().postFrameCallback(this);
    }

    public void resumeGame() {
        if(isInitialized && !isRunning) {
            isRunning = true;
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    public void pauseGame() {
        isRunning = false;
    }

    @Override
    public void doFrame(long currTimeNanos) {
        if(!isRunning)
            return;

        int elapsedTimeNanos = (int)(currTimeNanos - prevTimeNanos);

        if(elapsedTimeNanos != 0) {
            framePerSecond = 1_000_000_000 / elapsedTimeNanos;
            prevTimeNanos = currTimeNanos;

            float deltaSecond = elapsedTimeNanos * 1e-9f;
            DefenseGame.getInstance().update(deltaSecond);
            invalidate();
        }

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        DefenseGame.getInstance().onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return DefenseGame.getInstance().onTouchEvent(event);
    }

    public DefenseGame get()
    {
        return DefenseGame.getInstance();
    }

    public static DefenseActivity getDefenseActivity(){
        return (DefenseActivity) view.getContext();
    }
}
