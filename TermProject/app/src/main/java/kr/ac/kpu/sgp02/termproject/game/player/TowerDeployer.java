package kr.ac.kpu.sgp02.termproject.game.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import java.util.HashMap;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.app.DefenseActivity;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.view.GameView;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.tile.Tile;
import kr.ac.kpu.sgp02.termproject.game.tower.CannonTower;
import kr.ac.kpu.sgp02.termproject.game.tower.LaserTower;
import kr.ac.kpu.sgp02.termproject.game.tower.PlasmaTower;
import kr.ac.kpu.sgp02.termproject.game.tower.MissileTower;

public class TowerDeployer implements GameObject {

    public enum TowerType {
        cannon,
        laser,
        missile,
        plasma,
    }

    protected boolean isActivated = false;
    protected HashMap<TowerType, TowerPreview> previewImages = new HashMap<>(4);
    protected TowerPreview selectedPreview;
    protected TowerType selectedType = TowerType.cannon;
    protected DefenseGame.Layer towerLayer = DefenseGame.Layer.tower;

    protected Point selectedTileIndex = new Point();
    protected PointF tilePosition = new PointF();

    public TowerDeployer() {
        previewImages.put(TowerType.cannon,
                new TowerPreview(0,0, R.mipmap.cannon_head, Metrics.size(R.dimen.cannon_range)));
        previewImages.put(TowerType.laser,
                new TowerPreview(0,0, R.mipmap.laser_head, Metrics.size(R.dimen.laser_range)));
        previewImages.put(TowerType.missile,
                new TowerPreview(0,0, R.mipmap.missile_head, Metrics.size(R.dimen.missile_range)));
        previewImages.put(TowerType.plasma,
                new TowerPreview(0,0, R.mipmap.plasma_head, Metrics.size(R.dimen.plasma_range)));
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(!isActivated)
            return false;

        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        Point tileIndex = Metrics.positionToTileIndex(x, y);
        Tile tile = DefenseGame.getInstance().getTileAt(tileIndex.x,tileIndex.y);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                onActionMove(tile);
                return true;
            case MotionEvent.ACTION_UP:
                onActionUp(tile);
                return true;
            default:
                break;
        }

        return false;
    }

    private void onActionMove(Tile tile) {
        Point tileIndex = tile.getIndex();
        tilePosition = Metrics.tileIndexToPosition(tileIndex.x, tileIndex.y);
        selectedPreview.setPosition(tilePosition.x, tilePosition.y);

        if(tile.isDeployable())
            selectedPreview.setLocationColor(Color.GREEN);
        else
            selectedPreview.setLocationColor(Color.RED);

    }

    private void onActionUp(Tile tile) {
        deploy(tile);
    }

    private void deploy(Tile tile) {
        Point index = tile.getIndex();

        if(!tile.isDeployable()) {
            isActivated = false;
            GameView.getDefenseActivity().resetButtonsSelected();
            return;
        }

        switch (selectedType) {
            case cannon:
                if(DefenseGame.getInstance().useMineral(Metrics.intValue(R.dimen.cannon_cost))) {
                    DefenseGame.getInstance().add(CannonTower.get(index.x, index.y), towerLayer);
                    tile.onTowerDeployed();
                }
                break;
            case laser:
                if(DefenseGame.getInstance().useMineral(Metrics.intValue(R.dimen.laser_cost))) {
                    DefenseGame.getInstance().add(LaserTower.get(index.x, index.y), towerLayer);
                    tile.onTowerDeployed();
                }
                break;
            case missile:
                if(DefenseGame.getInstance().useMineral(Metrics.intValue(R.dimen.missile_cost))) {
                    DefenseGame.getInstance().add(MissileTower.get(index.x, index.y), towerLayer);
                    tile.onTowerDeployed();
                }
                break;
            case plasma:
                if(DefenseGame.getInstance().useMineral(Metrics.intValue(R.dimen.plasma_cost))) {
                    DefenseGame.getInstance().add(PlasmaTower.get(index.x, index.y), towerLayer);
                    tile.onTowerDeployed();
                }
                break;
            default:
                break;
        }

        isActivated = false;
        GameView.getDefenseActivity().resetButtonsSelected();
    }

    public void activateDeployer(TowerType type) {
        isActivated = true;
        selectedPreview = previewImages.get(type);
        selectedPreview.setPosition(-9999,-9999);
        selectedType = type;
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
