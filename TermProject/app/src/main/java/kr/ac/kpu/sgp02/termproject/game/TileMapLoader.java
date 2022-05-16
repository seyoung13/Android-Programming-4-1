package kr.ac.kpu.sgp02.termproject.game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TileMapLoader{
    
    public TileMapLoader(){}

    public void loadWaveInfo(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray maps = jsonObject.getJSONArray("Maps");

            for(int mapIndex=0; mapIndex<maps.length(); ++mapIndex) {
                JSONObject map = maps.getJSONObject(mapIndex);

                int column = map.getInt("Column");
                int row = map.getInt("Row");

                JSONArray startPoints = map.getJSONArray("Starts");
                String startTile = "";
                for(int startPointsIndex=0; startPointsIndex<startPoints.length(); ++startPointsIndex){
                    JSONObject start = startPoints.getJSONObject(startPointsIndex);
                    startTile = start.toString();
                }

                String tile = map.getString("Tile");

                JSONArray waves = map.getJSONArray("Waves");
                for(int waveIndex=0; waveIndex<waves.length(); ++waveIndex){
                    JSONObject wave = waves.getJSONObject(waveIndex);

                    JSONArray monsters = wave.getJSONArray(startTile);
                    for(int i=0; i<monsters.length(); ++i){
                        JSONObject waveInfo = monsters.getJSONObject(i);

                        String type = waveInfo.getString("Type");
                        int number = waveInfo.getInt("Number");
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
