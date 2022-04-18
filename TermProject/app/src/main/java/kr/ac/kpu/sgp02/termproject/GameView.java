package kr.ac.kpu.sgp02.termproject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kr.ac.kpu.sgp02.termproject.defense.Monster;
import kr.ac.kpu.sgp02.termproject.defense.Projectile;
import kr.ac.kpu.sgp02.termproject.defense.TileMap;
import kr.ac.kpu.sgp02.termproject.defense.Tower;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.collision.CollisionChecker;

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

    // 테스트
    Monster monster;
    Tower tower;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    private void initView() {
        view = this;

        tileMap = new TileMap(tileBlueprint);
        objects.add(tileMap);

        monster = new Monster(2000, 400);
        objects.add(monster);

        tower = new Tower(0, 1);
        objects.add(tower);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currTimeNanoSecond) {
        int elapsedTimeNanoSecond = (int)(currTimeNanoSecond - prevTimeNanoSecond);

        if(elapsedTimeNanoSecond != 0) {
            framePerSecond = 1_000_000_000 / elapsedTimeNanoSecond;
            prevTimeNanoSecond = currTimeNanoSecond;

            float deltaSecond = elapsedTimeNanoSecond * 1e-9f;
            update(deltaSecond);
            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    public void update(float deltaTime) {
        for(GameObject object : objects) {
            object.update(deltaTime);
        }

        checkCollision();
    }

    private void checkCollision() {
        for(GameObject o1 : objects) {
            if (!(o1 instanceof Monster))
                continue;

            Monster monster = (Monster) o1;

            for (GameObject o2 : objects) {
                if (!(o2 instanceof Projectile))
                    continue;

                Projectile projectile = (Projectile) o2;

                if (CollisionChecker.collides(monster.collider, projectile.collider)) {
                    remove(monster);
                    remove(projectile);
                    break;
                }
            }
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

    public void add(GameObject object) {
        post(new Runnable() {
            @Override
            public void run() {
                objects.add(object);
            }
        });
    }

    public void remove(GameObject object) {
        post(new Runnable() {
            @Override
            public void run() {
                objects.remove(object);
            }
        });
    }
}
