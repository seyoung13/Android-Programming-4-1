package kr.ac.kpu.sgp02.termproject.game.tile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;

public class TileMap implements GameObject {
    private ArrayList<ArrayList<Rect>> grid = new ArrayList<>();
    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();

    private HashMap<Point, ArrayList<Tile>> pathTileLists = new HashMap<>();
    private HashMap<Point, Path> paths = new HashMap<>();

    private Paint gridPaint = new Paint();

    private int cellSize = (int)Metrics.size(R.dimen.cell_size);

    public TileMap(int[][] blueprint, ArrayList<Point> startPoints){
        setGridPaint();

        buildGridByArray(blueprint);

        buildTilesByArray(blueprint);

        buildPaths(startPoints);
    }

    public Tile getTileAt(int tileX, int tileY) {
        if(tileX < 0 || tileX >= tiles.get(0).size() || tileY < 0 || tileY >= tiles.size())
            return new Tile(-99, -99, TileType.error);

        return tiles.get(tileY).get(tileX);
    }

    public HashMap<Point, Path> getPaths() {
        return paths;
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

    private void buildPaths(ArrayList<Point> startPoints) {

        for(Point start : startPoints){
            // 경로 탐색을 위해 타일 방문 변수를 초기화한다.
            for(ArrayList<Tile> rows : tiles) {
                for (Tile tile : rows) {
                    tile.isVisited = false;
                }
            }

            // 타일맵을 탐색해 경로 리스트를 만든다.
            searchAdjacentTiles(getTileAt(start.x, start.y), start);

            // 경로 리스트를 이용해 Path 객체를 생성한다.
            buildPathByPathTileList(start);
        }
    }

    private void buildPathByPathTileList(Point start) {
        Path path = new Path();
        ArrayList<Tile> pathTiles = pathTileLists.get(start);

        if(pathTiles.size() < 2)
            return;

        Point pathTileIndex = pathTiles.get(pathTiles.size()-1).getIndex();
        PointF pathTilePosition = Metrics.tileIndexToPosition(pathTileIndex.x, pathTileIndex.y);
        path.moveTo(pathTilePosition.x, pathTilePosition.y);

        for(int i = pathTileLists.get(start).size()-2; i>=0; --i){
            pathTileIndex = pathTiles.get(i).getIndex();
            pathTilePosition = Metrics.tileIndexToPosition(pathTileIndex.x, pathTileIndex.y);
            path.lineTo(pathTilePosition.x, pathTilePosition.y);
        }

        paths.put(start, path);
    }

    private boolean searchAdjacentTiles(Tile tile, Point tileIndex) {
        // 방문한 노드라고 표시한다.
        tile.isVisited = true;

        ArrayList<Tile> adjacentTiles = new ArrayList<>(4);

        adjacentTiles.add(getTileAt(tile.getIndex().x+1, tile.getIndex().y));
        adjacentTiles.add(getTileAt(tile.getIndex().x, tile.getIndex().y+1));
        adjacentTiles.add(getTileAt(tile.getIndex().x, tile.getIndex().y-1));
        adjacentTiles.add(getTileAt(tile.getIndex().x-1, tile.getIndex().y));

        // 인접한 정점들을 확인한다.
        for(Tile adjacentTile : adjacentTiles) {
            // 종료지점을 발견했으면 경로 타일 리스트에 추가한다.
            if(searchTile(adjacentTile, tileIndex)) {
                pathTileLists.get(tileIndex).add(tile);
                return true;
            }
        }

        return false;
    }

    private boolean searchTile(Tile tile, Point tileIndex) {
        // 이미 방문한 노드는 무시한다.
        if (tile.isVisited)
            return false;

        switch (tile.getType()) {
            case path:
                // 노드가 경로의 일부면 계속 탐색한다.
                return searchAdjacentTiles(tile, tileIndex);
            case end:
                // 탐색이 종료지점에 도달했으면 끝에서부터 역으로 잇는다.
                pathTileLists.put(tileIndex, new ArrayList<>());
                pathTileLists.get(tileIndex).add(tile);
                return true;
            case deployable:
            case start:
            case error:
                // 노드의 끝이 종료지점이 아니면 분기로 돌아간다.
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

        for(Point start : paths.keySet()) {
            canvas.drawPath(paths.get(start), gridPaint);
        }

//        ArrayList<Point> points = new ArrayList<>();
//        for(Point start : paths.keySet())
//            points.add(start);
//
//        canvas.drawPath(paths.get(points.get(0)), gridPaint);
    }
}
