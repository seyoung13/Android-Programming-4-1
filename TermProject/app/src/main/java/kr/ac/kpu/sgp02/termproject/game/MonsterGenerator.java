package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Queue;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.game.monster.Armor;
import kr.ac.kpu.sgp02.termproject.game.monster.MonsterType;
import kr.ac.kpu.sgp02.termproject.game.monster.Sprinter;
import kr.ac.kpu.sgp02.termproject.game.monster.Walker;

public class MonsterGenerator implements GameObject {
    private float spawnRemainingTime;
    private float spawnInterval;

    private ArrayList<Point> startPoints;
    private Queue<Wave> waves;

    public MonsterGenerator(Queue<Wave> waves) {
        //this.startPoints = startPoints;
        this.waves = waves;

        spawnInterval = Metrics.floatValue(R.dimen.monster_spawn_interval);
        spawnRemainingTime = spawnInterval;
    }

    @Override
    public void update(float deltaSecond) {
        spawnRemainingTime -= deltaSecond;

        if(spawnRemainingTime <= 0) {
//            Wave wave = waves.poll();
//            for(String start : wave.subWaves.keySet()) {
//                Queue<Wave.SubWave> subWaves = wave.subWaves.get(start);
//
//                Wave.SubWave subWave = subWaves.poll();
//
//                //DefenseGame.getInstance().add(subWave.type, subWave.startTileIndex.x, subWave.startTileIndex.y);
//            }


            DefenseGame.getInstance().add(Sprinter.get(0, 4),
                    DefenseGame.Layer.monster);
            spawnRemainingTime += spawnInterval;
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    private void generateMonster(MonsterType type, Point tileIndex) {
        switch (type) {
            case walker:
                DefenseGame.getInstance().add(Walker.get(tileIndex.x, tileIndex.y), DefenseGame.Layer.monster);
                break;
            case sprinter:
                DefenseGame.getInstance().add(Sprinter.get(tileIndex.x, tileIndex.y), DefenseGame.Layer.monster);
                break;
            case armor:
                DefenseGame.getInstance().add(Armor.get(tileIndex.x, tileIndex.y), DefenseGame.Layer.monster);
                break;
        }
    }
}
