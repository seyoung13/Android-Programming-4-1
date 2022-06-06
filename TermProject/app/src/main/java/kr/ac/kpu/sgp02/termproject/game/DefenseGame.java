package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.sgp02.termproject.framework.collision.CollisionChecker;
import kr.ac.kpu.sgp02.termproject.framework.view.GameView;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.pool.ObjectPool;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.Recyclable;
import kr.ac.kpu.sgp02.termproject.game.player.Life;
import kr.ac.kpu.sgp02.termproject.game.player.Mineral;
import kr.ac.kpu.sgp02.termproject.game.system.LevelLoader;
import kr.ac.kpu.sgp02.termproject.game.system.MonsterGenerator;
import kr.ac.kpu.sgp02.termproject.game.player.TowerDeployer;
import kr.ac.kpu.sgp02.termproject.game.tile.Tile;
import kr.ac.kpu.sgp02.termproject.game.tile.TileMap;

public class DefenseGame {

    public enum Layer {
        background,
        tower,
        monster,
        damageCauser,
        image,
        system,
        ui,
        COUNT,
    }

    private static DefenseGame singleton;
    private ArrayList<ArrayList<GameObject>> layeredObjects;
    private TileMap tileMap;
    private TowerDeployer towerDeployer;
    private LevelLoader levelLoader;
    private Mineral mineral;
    private Life life;

    // --------------- 생성자 ---------------
    private DefenseGame() {
        // 생성자를 private 으로 설정하여 getInstance()를
        // 통해서만 싱글톤이 생성되게 한다.
    }

    public static DefenseGame getInstance() {
        if(singleton == null)
            singleton = new DefenseGame();

        return singleton;
    }

    // --------------- 메소드 ---------------

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

        tileMap = new TileMap(levelLoader.getTileBlueprint(), levelLoader.getStartPoints());
        add(tileMap, Layer.background);

        towerDeployer = new TowerDeployer();
        add(towerDeployer, Layer.system);

        mineral = new Mineral(200);
        add(mineral, Layer.ui);

        life = new Life();
        add(life, Layer.ui);

        add(new MonsterGenerator(levelLoader.getWaveQueue(), tileMap.getPaths()), Layer.system);

        add(new CollisionChecker(), Layer.system);

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
    }

    public ArrayList<GameObject> getObjectsAt(Layer layer) {
        return layeredObjects.get(layer.ordinal());
    }

    public void storeMineral(int reward) {
        mineral.addAmount(reward);
    }

    public boolean useMineral(int reward) {
        return mineral.subAmount(reward);
    }

    public Tile getTileAt(int tileX, int tileY) {
        return tileMap.getTileAt(tileX, tileY);
    }

    public void restoreLife(int number) {
        life.restore(number);
    }

    public void gotHurtLife(int damage) {
        if(life.decrease(damage))
            onGameOver();
    }

    private void onGameOver() {

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
