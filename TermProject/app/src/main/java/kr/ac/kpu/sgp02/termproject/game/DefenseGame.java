package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.sgp02.termproject.framework.GameView;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.MonsterGenerator;
import kr.ac.kpu.sgp02.termproject.framework.collision.CollisionChecker;
import kr.ac.kpu.sgp02.termproject.game.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.game.tower.CannonTower;
import kr.ac.kpu.sgp02.termproject.game.tower.LaserTower;
import kr.ac.kpu.sgp02.termproject.game.tower.PlasmaTower;
import kr.ac.kpu.sgp02.termproject.game.tower.Tower;

public class DefenseGame {

    public enum Layer {
        tower,
        projectile,
        monster,
        collider,
        ui,
        COUNT,
    }

    private final int[][] tileBlueprint =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
                    {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                    {0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            };

    private static DefenseGame singleton;
    private ArrayList<ArrayList<GameObject>> layers;
    private ArrayList<GameObject> objects;

    // --------------- 생성자 ---------------
    private DefenseGame() {
        // 생성자를 private 으로 설정하여 getInstance()를
        // 통해서만 싱글톤이 생성되게 한다.
    }

    // --------------- 메소드 ---------------

    public static DefenseGame getInstance() {
        if(singleton == null)
            singleton = new DefenseGame();

        return singleton;
    }

    public static void clear() {
        singleton = null;
    }

    public void initialize() {
        initializeLayers();
        objects = new ArrayList<>();

        add(new TileMap(tileBlueprint));

        add(new MonsterGenerator());

        add(new CannonTower(8, 2));

        add(new LaserTower(8, 6));

        add(new PlasmaTower(3, 3));

    }

    private void initializeLayers() {
        layers = new ArrayList<>();

        for(int i = 0; i < Layer.COUNT.ordinal(); ++i) {
            layers.add(new ArrayList<GameObject>());
        }
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

    public void onDraw(Canvas canvas) {
        for(GameObject object : objects) {
            object.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        return false;
    }

    public void add(GameObject object) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.add(object);
            }
        });
    }

    public void remove(GameObject object) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.remove(object);
            }
        });
    }
}
