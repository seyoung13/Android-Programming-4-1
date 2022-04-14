package kr.ac.kpu.sgp02.termproject.defense;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.kpu.sgp02.termproject.GameView;
import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;

enum TileType{
    PATH,
    DEPLOYABLE,
    START,
    END,
    DEPLOYED,
    ERROR,
}

public class Tile implements GameObject {
    private static Bitmap pathBitmap, deployableBitmap, startBitmap, endBitmap, errorBitmap;
    private static Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    //만들어진 비트맵 중 타일타입에 맞는 비트맵의 복사본
    private Bitmap bitmap;

    //타일맵 배열 내의 인덱스
    private int x, y;
    private int size = 50;

    Tile(int x,int y, int size, TileType type) {
        this.x = x;
        this.y = y;
        setSize(size);

        loadBitmapResources();
        selectBitmapByType(type);
        setDstRectSize();
    }

    public void setSize(int size) {
        this.size = Math.max(size, 50);
        setDstRectSize();
    }

    private void loadBitmapResources() {
        if(pathBitmap != null)
            return;

        Resources resources = GameView.view.getResources();

        pathBitmap = BitmapFactory.decodeResource(resources, R.mipmap.path_tile);
        deployableBitmap = BitmapFactory.decodeResource(resources, R.mipmap.deployable_tile);
        startBitmap = BitmapFactory.decodeResource(resources, R.mipmap.start_tile);
        endBitmap = BitmapFactory.decodeResource(resources, R.mipmap.end_tile);
        errorBitmap = BitmapFactory.decodeResource(resources, R.mipmap.error_tile);
    }

    private void selectBitmapByType(TileType type) {
        switch (type){
            case PATH:
                bitmap = pathBitmap;
                break;
            case DEPLOYABLE:
                bitmap = deployableBitmap;
                break;
            case START:
                bitmap = startBitmap;
                break;
            case END:
                bitmap = endBitmap;
                break;
            case ERROR:
            default:
                bitmap = errorBitmap;
                break;
        }

        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    private void setDstRectSize() {
        dstRect.set( x * size, y * size,
                x * size + size, y * size + size);
     }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
