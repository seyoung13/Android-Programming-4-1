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
import kr.ac.kpu.sgp02.termproject.defense.tower.CannonTower;
import kr.ac.kpu.sgp02.termproject.defense.tower.Projectile;
import kr.ac.kpu.sgp02.termproject.defense.TileMap;
import kr.ac.kpu.sgp02.termproject.defense.tower.Tower;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.MonsterGenerator;
import kr.ac.kpu.sgp02.termproject.framework.collision.CollisionChecker;

public class GameView extends View implements Choreographer.FrameCallback {
    public static GameView view;
    private static final String DEBUG_TAG = GameView.class.getSimpleName();

    ArrayList<GameObject> objects = new ArrayList<>();

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

        MonsterGenerator generator = new MonsterGenerator();
        objects.add(generator);

        objects.add(new CannonTower(5, 1));

        objects.add(new CannonTower(4, 5));

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
                if (o2 instanceof Projectile) {

                    Projectile projectile = (Projectile) o2;

                    // 해쉬셋을 이용해 계속 충돌중이었는지 확인하는 작업
                    // 일반화할 필요가 있음
                    if (CollisionChecker.collides(monster.collider, projectile.collider)) {
                        if (monster.collider.overlappedColliders.contains(projectile.collider)) {
                            monster.onStayOverlap(projectile);
                        } else {
                            monster.collider.overlappedColliders.add(projectile.collider);
                            monster.onBeginOverlap(projectile);
                        }

                        if (projectile.collider.overlappedColliders.contains(monster.collider)) {
                            projectile.onStayOverlap(monster);
                        } else {
                            projectile.collider.overlappedColliders.add(monster.collider);
                            projectile.onBeginOverlap(monster);
                        }
                    } else {
                        if (monster.collider.overlappedColliders.contains(projectile.collider)) {
                            monster.collider.overlappedColliders.remove(projectile.collider);
                            monster.onEndOverlap(projectile);
                        }

                        if (projectile.collider.overlappedColliders.contains(monster.collider)) {
                            projectile.collider.overlappedColliders.remove(monster.collider);
                            projectile.onEndOverlap(monster);
                        }
                    }
                }

                if (o2 instanceof Tower) {
                    Tower tower = (Tower) o2;

                    if (CollisionChecker.collides(monster.collider, tower.range)) {
                        if (monster.collider.overlappedColliders.contains(tower.range)) {
                            monster.onStayOverlap(tower);
                        } else {
                            monster.collider.overlappedColliders.add(tower.range);
                            monster.onBeginOverlap(tower);
                        }

                        if (tower.range.overlappedColliders.contains(monster.collider)) {
                            tower.onStayOverlap(monster);
                        } else {
                            tower.range.overlappedColliders.add(monster.collider);
                            tower.onBeginOverlap(monster);
                        }
                    } else {
                        if (monster.collider.overlappedColliders.contains(tower.range)) {
                            monster.collider.overlappedColliders.remove(tower.range);
                            monster.onEndOverlap(tower);
                        }

                        if (tower.range.overlappedColliders.contains(monster.collider)) {
                            tower.range.overlappedColliders.remove(monster.collider);
                            tower.onEndOverlap(monster);
                        }
                    }
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
