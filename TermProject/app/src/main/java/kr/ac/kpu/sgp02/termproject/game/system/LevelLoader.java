package kr.ac.kpu.sgp02.termproject.game.system;

import android.content.res.AssetManager;
import android.graphics.Point;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import kr.ac.kpu.sgp02.termproject.framework.view.GameView;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.game.Wave;

public class LevelLoader {
    private ArrayList<Point> startPoints;

    private int[][] tileBlueprint;

    private Queue<Wave> waveQueue;

    public LevelLoader(){}

    public ArrayList<Point> getStartPoints() { return startPoints;}

    public int[][] getTileBlueprint() {
        return tileBlueprint;
    }

    public Queue<Wave> getWaveQueue() {
        return waveQueue;
    }

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
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadTileMapInfo(JSONObject map) {
        try {
            // 타일맵 크기에 맞춰 이차원 배열을 할당한다.
            int col = map.getInt("Width");
            int row = map.getInt("Height");
            tileBlueprint = new int[row][col];

            // 타일맵 구조 문자열을 가져온다.
            String tile = map.getString("Tile");
            if(tile.length() != row * col + row-1)
                throw new JSONException("Tiles Do Not Match Rows and Columns");

            // 타일맵 구조 문자열에서 줄바꿈 문자를 제외한 문자를 정수로 변환해 이차원 배열에 집어 넣는다.
            int tileIndex = 0;
            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    char ch = tile.charAt(tileIndex++);
                    if (ch == '\n')
                        ch = tile.charAt(tileIndex++);
                    tileBlueprint[i][j] = Character.getNumericValue(ch);
                }
            }
        }
        catch (JSONException e) {
            Log.e("Json Load Error", "TileMap Info.");
            e.printStackTrace();
        }
    }

    private void loadWavesInfo(JSONObject map)  {
        try {
            // 웨이브가 시작될 시작점들을 저장한다.
            JSONArray startsInfo = map.getJSONArray("Starts");
            ArrayList<String> starts = new ArrayList<>(startsInfo.length());
            startPoints = new ArrayList<>(startsInfo.length());

            for (int i = 0; i < startsInfo.length(); ++i) {
                String start = startsInfo.getString(i);
                starts.add(start);
                startPoints.add(Metrics.stringToTileIndex(start));
            }

            JSONArray wavesInfo = map.getJSONArray("Waves");
            waveQueue = new LinkedList<>();

            for (int waveIndex = 0; waveIndex < wavesInfo.length(); ++waveIndex) {
                JSONObject waveInfo = wavesInfo.getJSONObject(waveIndex);

                // 이번 웨이브의 서브웨이브 큐 해시맵을 생성한다.
                Wave wave = new Wave();
                for(String start : starts) {
                    wave.subWaveQueues.put(Metrics.stringToTileIndex(start), new LinkedList<>());
                }

                // 각 시작점들의 서브웨이브 큐를 채운다.
                for(String start : starts) {
                    JSONArray subWavesInfo = waveInfo.getJSONArray(start);

                    for (int subWaveIndex = 0; subWaveIndex < subWavesInfo.length(); ++subWaveIndex) {
                        JSONObject subWave = subWavesInfo.getJSONObject(subWaveIndex);

                        String type = subWave.getString("Type");
                        int number = subWave.getInt("Number");

                        wave.subWaveQueues.get(Metrics.stringToTileIndex(start)).offer(
                                new Wave.SubWave(type, number));
                    }
                }

                // 이번 웨이브를 큐에 추가하고 다음으로 넘어간다.
                waveQueue.add(wave);
            }
        }
        catch (JSONException e){
            Log.e("Json Load Error", "Waves Info.");
            e.printStackTrace();
        }
    }
}
