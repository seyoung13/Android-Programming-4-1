package kr.ac.kpu.sgp02.termproject.game.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.Wave;
import kr.ac.kpu.sgp02.termproject.game.monster.Armor;
import kr.ac.kpu.sgp02.termproject.game.monster.MonsterType;
import kr.ac.kpu.sgp02.termproject.game.monster.Sprinter;
import kr.ac.kpu.sgp02.termproject.game.monster.Walker;

public class MonsterGenerator implements GameObject {
    private float spawnRemainingTime;
    private float spawnInterval;
    private float waveInterval;

    private Queue<Wave> waveQueue;
    private HashMap<Point, Path> paths;

    private Wave currWave;
    private HashMap<Point, Wave.SubWave> currSubWaves = new HashMap<>();


    private boolean isAllWavesCleared = false;
    private boolean isCurrWaveCleared = false;

    private Paint textPaint;
    private String wavesText = "Wave: 1 / 5";
    private int currWaveCount = 0;
    private int totalWaveCount = 0;

    public MonsterGenerator(Queue<Wave> waveQueue, HashMap<Point, Path> paths) {
        this.waveQueue = waveQueue;
        this.paths = paths;
        init();

        spawnInterval = Metrics.floatValue(R.dimen.monster_spawn_interval);
        spawnRemainingTime = spawnInterval;

        waveInterval = Metrics.floatValue(R.dimen.wave_interval);
    }
    private void init() {
        setTextPaint();

        totalWaveCount = waveQueue.size();
        setWaveText();

        goNextWave();
    }

    private void increaseWaveText() {
        currWaveCount++;
        setWaveText();
    }

    private void setWaveText() {
        wavesText = "Wave: " + currWaveCount + " / " + totalWaveCount;
    }

    private void setTextPaint() {
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(Metrics.size(R.dimen.wave_text_size));
    }

    private void goNextWave() {
        if(waveQueue.isEmpty()) {
            isAllWavesCleared = true;
            return;
        }
        else {
            increaseWaveText();
            isCurrWaveCleared = false;
        }

        currWave = waveQueue.poll();

        for(Point start : currWave.subWaveQueues.keySet()) {
            Queue<Wave.SubWave> subWaveQueue = currWave.subWaveQueues.get(start);

            currSubWaves.put(start, subWaveQueue.poll());
        }
    }

    /**
     * 서브웨이브 큐에서 다음 몬스터를 생성하는 함수.
     * @return 몬스터를 생성했다면 true, 큐가 비어있다면 false
     */
    private boolean generateMonsterFromSubWave(Point start) {
        Wave.SubWave subWave = currSubWaves.get(start);

        // 서브웨이브가 비었다면 생성하지 않는다.
        if(subWave == null)
            return false;

        // 서브웨이브 정보로 몬스터를 생성한다.
        if(subWave.isMonsterLeft()) {
            generateMonster(subWave.getType(), start);
            subWave.decreaseNumber();
        }
        // 서브웨이브 큐에서 다음 서브웨이브를 찾는다.
        else {
            currSubWaves.put(start, currWave.subWaveQueues.get(start).poll());
        }

        return true;
    }


    private void generateMonster(MonsterType type, Point tileIndex) {
        switch (type) {
            case walker:
                DefenseGame.getInstance().add(Walker.get(tileIndex.x, tileIndex.y, paths.get(tileIndex)), DefenseGame.Layer.monster);
                break;
            case sprinter:
                DefenseGame.getInstance().add(Sprinter.get(tileIndex.x, tileIndex.y, paths.get(tileIndex)), DefenseGame.Layer.monster);
                break;
            case armor:
                DefenseGame.getInstance().add(Armor.get(tileIndex.x, tileIndex.y, paths.get(tileIndex)), DefenseGame.Layer.monster);
                break;
            case none:
                break;
        }
    }

    @Override
    public void update(float deltaSecond) {
        if(isAllWavesCleared)
            return;

        spawnRemainingTime -= deltaSecond;

        if (spawnRemainingTime <= 0) {
            // 모든 시작점의 서브웨이브가 끝났다면 다음 웨이브로 넘어간다.
            if(isCurrWaveCleared) {
                goNextWave();
                spawnRemainingTime += waveInterval;
                return;
            }

            // 각 시작점의 서브웨이브에서 몬스터를 생성한다.
            isCurrWaveCleared = true;
            for(Point start : currWave.subWaveQueues.keySet()) {
                if(generateMonsterFromSubWave(start))
                    isCurrWaveCleared = false;
            }

            spawnRemainingTime += spawnInterval;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(wavesText, Metrics.width/2, 100, textPaint);
    }
}
