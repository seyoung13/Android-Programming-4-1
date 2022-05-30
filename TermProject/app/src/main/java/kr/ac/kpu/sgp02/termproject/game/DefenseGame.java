package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.sgp02.termproject.framework.view.GameView;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.collision.CollisionChecker;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.game.projectile.SiegeSplash;
import kr.ac.kpu.sgp02.termproject.game.system.LevelLoader;
import kr.ac.kpu.sgp02.termproject.game.system.MonsterGenerator;
import kr.ac.kpu.sgp02.termproject.game.system.TowerDeployer;
import kr.ac.kpu.sgp02.termproject.game.tile.TileMap;
import kr.ac.kpu.sgp02.termproject.game.tower.CannonTower;
import kr.ac.kpu.sgp02.termproject.game.tower.LaserTower;
import kr.ac.kpu.sgp02.termproject.game.tower.Tower;

public class DefenseGame {

    public enum Layer {
        background,
        tower,
        monster,
        damageCauser,
        image,
        controller,
        ui,
        COUNT,
    }

    private static DefenseGame singleton;
    private ArrayList<ArrayList<GameObject>> layeredObjects;
    private TowerDeployer towerDeployer;
    private LevelLoader levelLoader;

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

    public void deployTower(TowerDeployer.TowerType type) {
        towerDeployer.activateDeployer(type);
    }

    public void initialize() {
        initializeLayers();

        levelLoader = new LevelLoader();
        levelLoader.loadLevelFromJson("level_info.json", 1);

        add(new TileMap(levelLoader.getTileBlueprint()), Layer.background);

        towerDeployer = new TowerDeployer();
        add(towerDeployer, Layer.controller);

        add(new MonsterGenerator(levelLoader.getWaveQueue()), Layer.controller);

        add(CannonTower.get(4, 6), Layer.tower);

        add(LaserTower.get(6, 6), Layer.tower);
    }

    private void initializeLayers() {
        layeredObjects = new ArrayList<>();

        for(int i = 0; i < Layer.COUNT.ordinal(); ++i) {
            layeredObjects.add(new ArrayList<>());
        }
    }

    public void update(float deltaSecond) {
        for(ArrayList<GameObject> objects : layeredObjects){
            for(GameObject object : objects) {
                object.update(deltaSecond);
            }
        }

        checkCollision();
    }

    public ArrayList<GameObject> getObjectsAt(Layer layer) {
        return layeredObjects.get(layer.ordinal());
    }

    private void checkCollision() {
        for (GameObject o1 : getObjectsAt(Layer.monster)) {
            if(!(o1 instanceof Monster))
                continue;

            Monster monster = (Monster) o1;

            for(GameObject o2 : getObjectsAt(Layer.damageCauser)) {

                if(o2 instanceof Projectile){
                    Projectile projectile = (Projectile) o2;

                    // 해쉬셋을 이용해 계속 충돌중이었는지 확인하는 작업
                    // 일반화할 필요가 있음
                    if (CollisionChecker.collides(monster.collider, projectile.collider)) {
                        if (monster.collider.overlappedColliders.contains(projectile.collider)) {
                            monster.onStayOverlap(projectile);
                        }
                        else {
                            monster.collider.overlappedColliders.add(projectile.collider);
                            monster.onBeginOverlap(projectile);
                        }

                        if (projectile.collider.overlappedColliders.contains(monster.collider)) {
                            projectile.onStayOverlap(monster);
                        }
                        else {
                            projectile.collider.overlappedColliders.add(monster.collider);
                            projectile.onBeginOverlap(monster);
                        }
                    }
                    else {
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

                if(o2 instanceof SiegeSplash) {
                    SiegeSplash splash = (SiegeSplash) o2;

                    if (CollisionChecker.collides(monster.collider, splash.collider)) {
                        if (monster.collider.overlappedColliders.contains(splash.collider)) {
                            monster.onStayOverlap(splash);
                        }
                        else {
                            monster.collider.overlappedColliders.add(splash.collider);
                            monster.onBeginOverlap(splash);
                        }

                        if (splash.collider.overlappedColliders.contains(monster.collider)) {
                            splash.onStayOverlap(monster);
                        }
                        else {
                            splash.collider.overlappedColliders.add(monster.collider);
                            splash.onBeginOverlap(monster);
                        }
                    }
                    else {
                        if (monster.collider.overlappedColliders.contains(splash.collider)) {
                            monster.collider.overlappedColliders.remove(splash.collider);
                            monster.onEndOverlap(splash);
                        }

                        if (splash.collider.overlappedColliders.contains(monster.collider)) {
                            splash.collider.overlappedColliders.remove(monster.collider);
                            splash.onEndOverlap(monster);
                        }
                    }
                }
            }

            // 타워 사거리 내 몬스터가 있는지 확인
            for(GameObject o2 : getObjectsAt(Layer.tower)) {
                if(!(o2 instanceof Tower))
                    continue;

                Tower tower = (Tower) o2;

                if (CollisionChecker.collides(monster.collider, tower.range)) {
                    if (monster.collider.overlappedColliders.contains(tower.range)) {
                        monster.onStayOverlap(tower);
                    }
                    else {
                        monster.collider.overlappedColliders.add(tower.range);
                        monster.onBeginOverlap(tower);
                    }

                    if (tower.range.overlappedColliders.contains(monster.collider)) {
                        tower.onStayOverlap(monster);
                    }
                    else {
                        tower.range.overlappedColliders.add(monster.collider);
                        tower.onBeginOverlap(monster);
                    }
                }
                else {
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


    public void onDraw(Canvas canvas) {
        for(ArrayList<GameObject> objects : layeredObjects) {
            for(GameObject object : objects) {
                object.draw(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean eventResult;

        eventResult = towerDeployer.onTouchEvent(event);

        return eventResult;
    }

    public void add(GameObject object, Layer layer) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = getObjectsAt(layer);
                objects.add(object);
            }
        });
    }

    public void remove(GameObject object) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                for(ArrayList<GameObject> objects : layeredObjects) {
                    if(!objects.remove(object))
                        continue;

                    if(object instanceof Recyclable) {
                        ObjectPool.add((Recyclable)object);
                    }
                    break;
                }
            }
        });
    }


}
