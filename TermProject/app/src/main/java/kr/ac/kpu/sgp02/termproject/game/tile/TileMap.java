package kr.ac.kpu.sgp02.termproject.game.tile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;

public class TileMap implements GameObject {
    private ArrayList<ArrayList<Rect>> grid = new ArrayList<>();
    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();

    private HashMap<Point, Path> paths;

    private HashMap<Point, ArrayList<Tile>> tilePathMap = new HashMap<>();

    private Paint gridPaint = new Paint();

    private int cellSize = (int)Metrics.size(R.dimen.cell_size);
    private boolean[][] visited;

    public TileMap(int[][] blueprint, ArrayList<Point> startPoints){
        setGridPaint();

        buildGridByArray(blueprint);

        buildTilesByArray(blueprint);

        for(Point point : startPoints){
            maze(getTileAt(point.x, point.y), point);

            for(ArrayList<Tile> rows : tiles)
                for(Tile tile : rows) {
                    tile.isVisited = false;
                }
        }

        Tile t = getTileAt(0,0);
    }

    private void setGridPaint() {
        gridPaint.setColor(Color.BLACK);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(8);
    }

    private void buildGridByArray(int[][] blueprint) {
        for(int i = 0; i < blueprint.length; ++i) {
            ArrayList<Rect> row = new ArrayList<>();
            for(int j = 0; j < blueprint[i].length; ++j) {
                Rect cell = new Rect();
                cell.set(j * cellSize, i * cellSize,
                        j * cellSize + cellSize, i * cellSize + cellSize);
                row.add(cell);
            }
            grid.add(row);
        }
    }

    private void buildTilesByArray(int[][] blueprint) {
        for(int i = 0; i < blueprint.length; ++i) {
            ArrayList<Tile> row = new ArrayList<>();
            for(int j = 0; j < blueprint[i].length; ++j) {
                TileType type;
                switch (blueprint[i][j]){
                    case 0:
                        type = TileType.deployable;
                        break;
                    case 1:
                        type = TileType.path;
                        break;
                    case 2:
                        type = TileType.start;
                        break;
                    case 3:
                        type = TileType.end;
                        break;
                    default:
                        type = TileType.error;
                        break;
                }

                Tile tile = new Tile(j, i, type);
                row.add(tile);
            }
            tiles.add(row);
        }
    }

    private boolean maze(Tile tile, Point start) {
        //타일 확인해서 들어왔을테니까 path 임.
        tile.isVisited = true;

        // 인접 정점들을 확인한다.
        ArrayList<Tile> adjacentTiles = new ArrayList<>(4);

        adjacentTiles.add(getTileAt(tile.getIndex().x+1, tile.getIndex().y));
        adjacentTiles.add(getTileAt(tile.getIndex().x, tile.getIndex().y+1));
        adjacentTiles.add(getTileAt(tile.getIndex().x, tile.getIndex().y-1));
        adjacentTiles.add(getTileAt(tile.getIndex().x-1, tile.getIndex().y));

        for(Tile adjacentTile : adjacentTiles) {
            if(searchTile(adjacentTile, start)) {
                tilePathMap.get(start).add(tile);
                return true;
            }
        }

        return false;
    }

    private boolean searchTile(Tile tile, Point start) {
        if (tile.isVisited)
            return false;

        switch (tile.getType()) {
            case path:
                return maze(tile, start);
            case end:
                tilePathMap.put(start, new ArrayList<>());
                tilePathMap.get(start).add(tile);
                return true;
            case deployable:
            case start:
            case error:
                return false;
        }

        return false;
    }

    @Override
    public void update(float deltaSecond) {
        for(ArrayList<Tile> row : tiles) {
            for(Tile tile : row) {
                tile.update(deltaSecond);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for(ArrayList<Tile> row : tiles) {
            for(Tile tile : row) {
                tile.draw(canvas);
            }
        }

        for(ArrayList<Rect> row : grid) {
            for(Rect cell : row) {
                canvas.drawRect(cell, gridPaint);
            }
        }
    }

    public Tile getTileAt(int tileX, int tileY) {
        if(tileX < 0 || tileX >= tiles.get(0).size() || tileY < 0 || tileY >= tiles.size())
            return new Tile(-99, -99, TileType.error);

        return tiles.get(tileY).get(tileX);
    }
}
