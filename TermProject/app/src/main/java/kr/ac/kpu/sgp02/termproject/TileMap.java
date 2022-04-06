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
    private ArrayList<ArrayList<Rect>> dstRects = new ArrayList<ArrayList<Rect>>();
    private int tileSize = 250;
    private Paint blackStrokePaint = new Paint();

    public TileMap(int[][] blueprint){
        Resources resources = GameView.view.getResources();
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.grid);
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());

        for(int i = 0; i < blueprint.length; ++i) {
            ArrayList<Rect> row = new ArrayList<Rect>();
            for(int j = 0; j < blueprint[i].length; ++j) {
                Rect grid = new Rect();
                grid.set(j * tileSize, i * tileSize,
                        j * tileSize + tileSize, i * tileSize + tileSize);
                row.add(grid);
            }
            dstRects.add(row);
        }

        blackStrokePaint.setColor(Color.BLACK);
        blackStrokePaint.setStyle(Paint.Style.STROKE);
        blackStrokePaint.setStrokeWidth(10);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        for(ArrayList<Rect> row : dstRects) {
            for(Rect grid : row) {
                canvas.drawRect(grid, blackStrokePaint);
            }
        }
    }
}
