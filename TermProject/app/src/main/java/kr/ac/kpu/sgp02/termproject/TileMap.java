package kr.ac.kpu.sgp02.termproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class TileMap implements GameObject{
    private ArrayList<ArrayList<Rect>> grid = new ArrayList<ArrayList<Rect>>();
    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    
    private Paint gridPaint = new Paint();

    private int cellSize = 250;

    public TileMap(int[][] blueprint){
        setGridPaint();

        buildGridByArray(blueprint);

        buildTilesByArray(blueprint);
    }

    private void setGridPaint() {
        gridPaint.setColor(Color.BLACK);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(8);
    }

    private void buildGridByArray(int[][] blueprint) {
        for(int i = 0; i < blueprint.length; ++i) {
            ArrayList<Rect> row = new ArrayList<Rect>();
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
            ArrayList<Tile> row = new ArrayList<Tile>();
            for(int j = 0; j < blueprint[i].length; ++j) {
                TileType type;
                switch (blueprint[i][j]){
                    case 0:
                        type = TileType.DEPLOYABLE;
                        break;
                    case 1:
                        type = TileType.PATH;
                        break;
                    case 2:
                        type = TileType.START;
                        break;
                    case 3:
                        type = TileType.END;
                        break;
                    default:
                        type = TileType.ERROR;
                        break;
                }

                Tile tile = new Tile(j, i, cellSize, type);
                row.add(tile);
            }
            tiles.add(row);
        }
    }

    @Override
    public void update() {
        for(ArrayList<Tile> row : tiles) {
            for(Tile tile : row) {
                tile.update();
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
}
