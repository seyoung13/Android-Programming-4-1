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
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;

public class TileMap implements GameObject {
    private Sprite background;

    private ArrayList<ArrayList<Rect>> grid = new ArrayList<>();
    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();

    private HashMap<Point, ArrayList<Tile>> pathTileLists = new HashMap<>();
    private HashMap<Point, Path> paths = new HashMap<>();

    private Paint gridPaint = new Paint();

    private int cellSize = (int)Metrics.size(R.dimen.cell_size);

    public TileMap(int[][] blueprint, ArrayList<Point> startPoints){
        setGridPaint();

        background = new Sprite(Metrics.width/2, Metrics.height/2,
                Metrics.width * 2, Metrics.height * 2, R.mipmap.background);

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
            // ?????? ????????? ?????? ?????? ?????? ????????? ???????????????.
            for(ArrayList<Tile> rows : tiles) {
                for (Tile tile : rows) {
                    tile.isVisited = false;
                }
            }

            // ???????????? ????????? ?????? ???????????? ?????????.
            searchAdjacentTiles(getTileAt(start.x, start.y), start);

            // ?????? ???????????? ????????? Path ????????? ????????????.
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
        // ????????? ???????????? ????????????.
        tile.isVisited = true;

        ArrayList<Tile> adjacentTiles = new ArrayList<>(4);

        adjacentTiles.add(getTileAt(tile.getIndex().x+1, tile.getIndex().y));
        adjacentTiles.add(getTileAt(tile.getIndex().x, tile.getIndex().y+1));
        adjacentTiles.add(getTileAt(tile.getIndex().x, tile.getIndex().y-1));
        adjacentTiles.add(getTileAt(tile.getIndex().x-1, tile.getIndex().y));

        // ????????? ???????????? ????????????.
        for(Tile adjacentTile : adjacentTiles) {
            // ??????????????? ??????????????? ?????? ?????? ???????????? ????????????.
            if(searchTile(adjacentTile, tileIndex)) {
                pathTileLists.get(tileIndex).add(tile);
                return true;
            }
        }

        return false;
    }

    private boolean searchTile(Tile tile, Point tileIndex) {
        // ?????? ????????? ????????? ????????????.
        if (tile.isVisited)
            return false;

        switch (tile.getType()) {
            case path:
                // ????????? ????????? ????????? ?????? ????????????.
                return searchAdjacentTiles(tile, tileIndex);
            case end:
                // ????????? ??????????????? ??????????????? ??????????????? ????????? ?????????.
                pathTileLists.put(tileIndex, new ArrayList<>());
                pathTileLists.get(tileIndex).add(tile);
                return true;
            case deployable:
            case start:
            case error:
                // ????????? ?????? ??????????????? ????????? ????????? ????????????.
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
        background.draw(canvas);

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

//        for(Point start : paths.keySet()) {
//            canvas.drawPath(paths.get(start), gridPaint);
//        }

//        ArrayList<Point> points = new ArrayList<>();
//        for(Point start : paths.keySet())
//            points.add(start);
//
//        canvas.drawPath(paths.get(points.get(0)), gridPaint);
    }
}
