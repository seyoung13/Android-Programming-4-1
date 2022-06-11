package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Paint;
import android.graphics.Point;
import android.util.Pair;

import java.util.HashMap;
import java.util.Queue;

import kr.ac.kpu.sgp02.termproject.game.monster.MonsterType;

public class Wave {
    public static class SubWave {
        protected final MonsterType type;
        protected int number;

        public SubWave(String type, int number){
            switch (type){
                case "Walker":
                    this.type = MonsterType.walker;
                    break;
                case "Sprinter":
                    this.type = MonsterType.sprinter;
                    break;
                case "Armor":
                    this.type = MonsterType.armor;
                    break;
                case "None":
                default:
                    this.type = MonsterType.none;
                    break;
            }

            this.number = number;
        }

        public MonsterType getType() {
            return type;
        }

        public boolean isMonsterLeft() {
            return number > 0;
        }

        public void decreaseNumber() {
            number--;
        }
    }

    public HashMap<Point, Queue<SubWave>> subWaveQueues;

    public Wave() {
        subWaveQueues = new HashMap<>();
    }
}
