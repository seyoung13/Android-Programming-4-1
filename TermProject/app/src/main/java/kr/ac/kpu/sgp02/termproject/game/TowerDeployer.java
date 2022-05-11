package kr.ac.kpu.sgp02.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.Recyclable;
import kr.ac.kpu.sgp02.termproject.framework.Sprite;
import kr.ac.kpu.sgp02.termproject.game.tower.CannonTower;
import kr.ac.kpu.sgp02.termproject.game.tower.LaserTower;
import kr.ac.kpu.sgp02.termproject.game.tower.PlasmaTower;
import kr.ac.kpu.sgp02.termproject.game.tower.SiegeTower;
import kr.ac.kpu.sgp02.termproject.game.tower.Tower;

public class TowerDeployer implements GameObject {
    public enum TowerType {
        cannon,
        laser,
        siege,
        plasma,
    }

    boolean isActivated = false;

    //타워 배치하고 나면 V, X로 확인하기
    boolean isSelectedDeployed = false;
    HashMap<TowerType, TowerPreview> previewImages = new HashMap<>(4);
    TowerPreview selectedPreview;

    public TowerDeployer() {
        previewImages.put(TowerType.cannon,
                new TowerPreview(0,0, R.mipmap.tower_sample, Metrics.size(R.dimen.cannon_range)));
        previewImages.put(TowerType.laser,
                new TowerPreview(0,0, R.mipmap.tower_sample, Metrics.size(R.dimen.laser_range)));
        previewImages.put(TowerType.siege,
                new TowerPreview(0,0, R.mipmap.tower_sample, Metrics.size(R.dimen.siege_range)));
        previewImages.put(TowerType.plasma,
                new TowerPreview(0,0, R.mipmap.tower_sample, Metrics.size(R.dimen.plasma_range)));
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                onActionMove(x, y);
                return true;
            case MotionEvent.ACTION_UP:
                //onActionUp(x, y);
                return true;
            default:
                break;
        }

        return false;
    }

    private void onActionDown(float x, float y){

    }

    private void onActionMove(float x, float y) {
        if(!isActivated)
            return;

        selectedPreview.setPosition(x, y);
    }

    private void onActionUp(float x, float y) {
        //deploy(x, y);
        selectedPreview.setLocationColor(Color.RED);
    }

    private void deploy(float x, float y) {
        DefenseGame.getInstance().add(CannonTower.get(3,3), DefenseGame.Layer.tower);
        isActivated = false;
    }

    public void activateDeployer(TowerType type) {
        isActivated = true;
        selectedPreview = previewImages.get(type);
    }

    @Override
    public void update(float deltaSecond) {

    }

    @Override
    public void draw(Canvas canvas) {
        if(!isActivated)
            return;

        selectedPreview.draw(canvas);
    }
}
