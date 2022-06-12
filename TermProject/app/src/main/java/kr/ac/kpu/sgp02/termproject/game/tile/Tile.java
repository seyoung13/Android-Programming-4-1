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
    private static float size;

    private TileType type;

    protected boolean isDeployable = false;

    protected boolean isVisited = false;

    static {
        size = Metrics.size(R.dimen.cell_size);
    }

    Tile(int x, int y, TileType type) {
        index = new Point(x, y);
        position = Metrics.tileIndexToPosition(x, y);
        this.type = type;

        selectBitmapByType();
    }

    private void selectBitmapByType() {
        switch (type){
            case path:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.tile_path);
                sprite.setOpacity(50);
                break;
            case deployable:
                sprite = new Sprite(position.x, position.y, size, R.mipmap.tile_deployable);
                sprite.setOpacity(50);
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

    public TileType getType() {
        return type;
    }

    public boolean isDeployable() {
        return isDeployable;
    }

    public void onTowerDeployed() {isDeployable = false;}

    public void onTowerUninstalled() {isDeployable = true;}

    public Point getIndex() {
        return index;
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
}
