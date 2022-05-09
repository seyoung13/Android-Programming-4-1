package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Canvas;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;

enum TileType{
    path,
    deployable,
    start,
    end,
    deployed,
    error,
}

public class Tile implements GameObject {
    //만들어진 비트맵 중 타일타입에 맞는 비트맵의 복사본
    private Sprite sprite;

    //타일맵 배열 내의 인덱스
    private int x, y;
    private PointF position = new PointF();
    private int size = 50;

    Tile(int x,int y, int size, TileType type) {
        this.x = x;
        this.y = y;

        this.size = size;

        position.x = x * size + size / 2;
        position.y = y * size + size / 2;

        selectBitmapByType(type);
    }

    private void selectBitmapByType(TileType type) {
        switch (type){
            case path:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.path_tile);
                break;
            case deployable:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.deployable_tile);
                break;
            case start:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.start_tile);
                break;
            case end:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.end_tile);
                break;
            case error:
            default:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.error_tile);
                break;
        }
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

}
