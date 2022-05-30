package kr.ac.kpu.sgp02.termproject.game.tile;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.objects.Sprite;


public class Tile implements GameObject {
    //만들어진 비트맵 중 타일타입에 맞는 비트맵의 복사본
    private Sprite sprite;

    //타일맵 배열 내의 인덱스
    private Point index;
    private PointF position;
    private int size = 50;

    protected boolean isDeployable = false;

    Tile(int x, int y, int size, TileType type) {
        index = new Point(x, y);
        position = Metrics.tileIndexToPosition(x, y);

        this.size = size;

        selectBitmapByType(type);
    }

    private void selectBitmapByType(TileType type) {
        switch (type){
            case path:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.tile_path);
                break;
            case deployable:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.tile_deployable);
                isDeployable = true;
                break;
            case start:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.tile_start);
                break;
            case end:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.tile_end);
                break;
            case error:
            default:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.tile_error);
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

    public boolean isDeployable() {
        return isDeployable;
    }
}
