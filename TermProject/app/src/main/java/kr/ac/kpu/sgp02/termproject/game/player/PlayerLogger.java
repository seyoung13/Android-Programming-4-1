package kr.ac.kpu.sgp02.termproject.game.player;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;

public class PlayerLogger implements GameObject {
    public enum PlayerLog {
        kills,
        loses,
        usedMinerals,
    }

    private int killScore;
    private int loseScore;
    private int usedMinerals;

    public void addKillScore(){
        killScore++;
    }

    public void addLoseScore(){
        loseScore++;
    }

    public void addUsedMinerals(int used) {
        usedMinerals += used;
    }

    public ArrayList<Integer> getLog() {
        ArrayList<Integer> log = new ArrayList<>();
        log.add(PlayerLog.kills.ordinal(), killScore);
        log.add(PlayerLog.loses.ordinal(), loseScore);
        log.add(PlayerLog.usedMinerals.ordinal(), usedMinerals);

        return log;
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
