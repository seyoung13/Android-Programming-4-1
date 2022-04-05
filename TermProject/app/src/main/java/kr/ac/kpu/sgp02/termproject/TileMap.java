package kr.ac.kpu.sgp02.termproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class TileMap {
    private static Bitmap bitmap;
    private static Rect srcRect;
    private Rect dstRect;
    private int tileSize = 50;

    public TileMap(int x, int y){
        dstRect.set(0,0,x * tileSize,y * tileSize);

        Resources resources = GameView.view.getResources();
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.grid);
    }
}
