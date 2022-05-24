package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Point;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import kr.ac.kpu.sgp02.termproject.game.monster.MonsterType;

public class Wave {
    public HashMap<String, Queue<SubWave>> subWaves;

    public Wave() {
        subWaves = new HashMap<>();
    }

    static class SubWave {
        public Point startTileIndex;
        public MonsterType type;
        public int number;

        SubWave(String start, String type, int number){
            // 4자리 문자열에서 타일의 x,  y 인덱스를 추출한다.
            int xTens = Character.getNumericValue(start.charAt(0));
            int xUnits = Character.getNumericValue(start.charAt(1));
            int yTens = Character.getNumericValue(start.charAt(2));
            int yUnits = Character.getNumericValue(start.charAt(3));

            startTileIndex = new Point(xTens * 10 + xUnits, yTens * 10 + yUnits);

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
                default:
                    break;
            }

            this.number = number;
        }
    }

}
