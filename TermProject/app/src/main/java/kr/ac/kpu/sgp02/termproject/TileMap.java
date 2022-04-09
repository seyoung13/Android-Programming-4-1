package kr.ac.kpu.sgp02.termproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class TileMap implements GameObject {
    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();
    private ArrayList<ArrayList<Rect>> grid = new ArrayList<ArrayList<Rect>>();
    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    private int cellSize = 250;
    private Paint blackStrokePaint = new Paint();

    public TileMap(int[][] blueprint){
        Resources resources = GameView.view.getResources();
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.grid);
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());

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

        blackStrokePaint.setColor(Color.BLACK);
        blackStrokePaint.setStyle(Paint.Style.STROKE);
        blackStrokePaint.setStrokeWidth(8);
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
                canvas.drawRect(cell, blackStrokePaint);
            }
        }
    }
}
