package kr.ac.kpu.sgp02.termproject.game;

import android.content.res.AssetManager;
import android.graphics.Point;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import kr.ac.kpu.sgp02.termproject.framework.GameView;
import kr.ac.kpu.sgp02.termproject.game.monster.MonsterType;

public class TileMapLoader{
    class Wave {
        HashMap<String, Queue<SubWave>> subWaves = new HashMap<>();
    }

    class SubWave {
        String start;
        Point startTileIndex;
        MonsterType type;
        int number;

        SubWave(String xy, String type, int number){
            // 4자리 문자열에서 각각 타일의 x 인덱스와 y 인덱스를 추출한다.
            start = xy;
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

        String getKey() {
            return start;
        }
    }

    int[][] tileBlueprint;

    Queue<Wave> waves = new LinkedList<>();

    public TileMapLoader(){}

    public void loadLevelFromJson(String fileName, int mapLevel) {
        AssetManager assets = GameView.view.getContext().getAssets();

        String json = "";
        try {
            InputStream inputStream = assets.open(fileName);
            int fileSize = inputStream.available();

            byte[] buffer = new byte[fileSize];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");

            loadLevelInfo(json, mapLevel);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadLevelInfo(String json, int mapLevel) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray maps = jsonObject.getJSONArray("Maps");

            // n번째 레벨의 맵
            JSONObject map = maps.getJSONObject(mapLevel);

            loadTileMapInfo(map);
            loadWavesInfo(map);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadTileMapInfo(JSONObject map) {
        try {
            // 타일맵 크기에 맞춰 이차원 배열을 할당한다.
            int col = map.getInt("Width");
            int row = map.getInt("Height");
            tileBlueprint = new int[row][col];

            // 타일맵 구조 문자열에서 줄바꿈 문자를 제외한 문자를 정수로 변환해 이차원 배열에 집어 넣는다.
            String tile = map.getString("Tile");
            if(tile.length() != row * col + row-1)
                throw new JSONException("Tiles Do Not Match Rows and Columns");

            int tileIndex = 0;
            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    char ch = tile.charAt(tileIndex++);
                    if (ch == '\n')
                        ch = tile.charAt(tileIndex++);
                    tileBlueprint[i][j] = Character.getNumericValue(ch);
                }
            }

        } catch (JSONException e) {
            Log.e("Json Load Error", "TileMap Info.");
            e.printStackTrace();
        }
    }

    private void loadWavesInfo(JSONObject map)  {
        try {
            // 웨이브가 시작될 시작점들을 저장한다.
            JSONArray startsInfo = map.getJSONArray("Starts");
            ArrayList<String> starts = new ArrayList<>(startsInfo.length());
            for (int i = 0; i < startsInfo.length(); ++i) {
                starts.add(startsInfo.getString(i));
            }

            // 웨이브별 시작점들의 서브웨이브 정보를 읽어온다.
            JSONArray waves = map.getJSONArray("Waves");
            Queue<Wave> waveQueue = new LinkedList<>();
            for (int waveIndex = 0; waveIndex < waves.length(); ++waveIndex) {
                JSONObject waveInfo = waves.getJSONObject(waveIndex);

                Wave wave = new Wave();
                for(String start : starts) {
                    wave.subWaves.put(start, new LinkedList<>());
                }

                for(String start : starts) {
                    JSONArray subWavesInfo = waveInfo.getJSONArray(start);

                    for (int subWaveIndex = 0; subWaveIndex < subWavesInfo.length(); ++subWaveIndex) {
                        JSONObject subWave = subWavesInfo.getJSONObject(subWaveIndex);

                        String type = subWave.getString("Type");
                        int number = subWave.getInt("Number");

                        wave.subWaves.get(start).offer(
                                new SubWave(start, type, number));
                    }
                }

                waveQueue.add(wave);
            }
            waveQueue.size();

        } catch (JSONException e){
            Log.e("Json Load Error", "Waves Info.");
            e.printStackTrace();
        }
    }
}
