package kr.ac.kpu.sgp02.termproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

enum TileType{
    PATH,
    DEPLOYABLE,
    START,
    END,
}

public class Tile implements GameObject {
    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    //타일맵 배열 내의 인덱스
    private int x, y;
    private int size = 50;

    Tile(int x,int y, TileType type) {
        this.x = x;
        this.y = y;

        buildBitmapResource(type);
        setDstRectSize();
    }

    public void setSize(int size) {
        this.size = Math.max(size, 10);
        setDstRectSize();
    }

    private void buildBitmapResource(TileType type) {
        if(bitmap != null)
            return;

        Resources resources = GameView.view.getResources();
        int id;
        switch (type){
            case PATH:
                id = R.mipmap.path_tile;
                break;
            case DEPLOYABLE:
                id = R.mipmap.deployable_tile;
                break;
            case START:
                id = R.mipmap.start_tile;
                break;
            case END:
                id = R.mipmap.end_tile;
                break;
            default:
                id = R.mipmap.error_tile;
        }

        bitmap = BitmapFactory.decodeResource(resources, id);

        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    private void setDstRectSize() {
        dstRect.set( x * size - size / 2, y * size - size / 2,
                x * size + size / 2, y * size + size / 2);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
